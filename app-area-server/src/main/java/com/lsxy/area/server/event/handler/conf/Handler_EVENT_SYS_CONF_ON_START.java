package com.lsxy.area.server.event.handler.conf;

import com.lsxy.area.api.BusinessState;
import com.lsxy.area.api.BusinessStateService;
import com.lsxy.area.api.ConfService;
import com.lsxy.area.server.event.EventHandler;
import com.lsxy.area.server.service.callcenter.CallCenterUtil;
import com.lsxy.area.server.service.callcenter.ConversationService;
import com.lsxy.area.server.util.NotifyCallbackUtil;
import com.lsxy.area.server.util.RecordFileUtil;
import com.lsxy.framework.core.exceptions.api.YunhuniApiException;
import com.lsxy.framework.core.utils.JSONUtil2;
import com.lsxy.framework.core.utils.MapBuilder;
import com.lsxy.framework.rpc.api.RPCCaller;
import com.lsxy.framework.rpc.api.RPCRequest;
import com.lsxy.framework.rpc.api.RPCResponse;
import com.lsxy.framework.rpc.api.ServiceConstants;
import com.lsxy.framework.rpc.api.event.Constants;
import com.lsxy.framework.rpc.api.session.Session;
import com.lsxy.framework.rpc.api.session.SessionContext;
import com.lsxy.framework.rpc.exceptions.InvalidParamException;
import com.lsxy.framework.rpc.exceptions.RightSessionNotFoundExcepiton;
import com.lsxy.framework.rpc.exceptions.SessionWriteException;
import com.lsxy.yunhuni.api.app.model.App;
import com.lsxy.yunhuni.api.app.service.AppService;
import com.lsxy.yunhuni.api.session.model.Meeting;
import com.lsxy.yunhuni.api.session.service.MeetingService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Created by liuws on 2016/8/29.
 */
@Component
public class Handler_EVENT_SYS_CONF_ON_START extends EventHandler{

    private static final Logger logger = LoggerFactory.getLogger(Handler_EVENT_SYS_CONF_ON_START.class);

    @Autowired
    private BusinessStateService businessStateService;

    @Autowired
    private AppService appService;

    @Autowired
    private ConfService confService;

    @Autowired
    private NotifyCallbackUtil notifyCallbackUtil;

    @Autowired
    private RPCCaller rpcCaller;

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private ConversationService conversationService;

    @Override
    public String getEventName() {
        return Constants.EVENT_SYS_CONF_ON_START;
    }

    /**
     * 调用会议创建 成功事件
     * @param request
     * @param session
     * @return
     */
    @Override
    public RPCResponse handle(RPCRequest request, Session session) {
        RPCResponse res = null;
        Map<String,Object> params = request.getParamMap();
        if(MapUtils.isEmpty(params)){
            throw new InvalidParamException("request params is null");
        }
        String conf_id = (String)params.get("user_data");
        String res_id = (String)params.get("res_id");
        if(StringUtils.isBlank(conf_id)){
            throw new InvalidParamException("conf_id is null");
        }
        BusinessState state = businessStateService.get(conf_id);
        if(state == null){
            throw new InvalidParamException("businessstate is null,call_id={}",conf_id);
        }
        if(res_id!=null){
            businessStateService.updateResId(conf_id,res_id);
            if(state.getBusinessData().get(BusinessState.REF_RES_ID) == null){
                businessStateService.updateInnerField(conf_id,BusinessState.REF_RES_ID,res_id);
            }
        }
        if(logger.isDebugEnabled()){
            logger.info("confi_id={},state={}",conf_id,state);
        }
        if(BusinessState.TYPE_CC_CONVERSATION.equals(state.getType())){
            conversation(state,res_id,conf_id);
        }else{
            conf(state,conf_id,res_id);
        }
        return res;
    }

    public void conversation(BusinessState state,String res_id,String conversationId){
        if(conversationService.isPlayWait(state)){
            if(state.getBusinessData().get(CallCenterUtil.CONVERSATION_STARTED_FIELD) == null) {
                //会议尚未正式开始播放排队音
                Map<String, Object> _params = new MapBuilder<String,Object>()
                        .putIfNotEmpty("res_id",res_id)
                        .putIfNotEmpty("content", JSONUtil2.objectToJson(new Object[][]{new Object[]{
                                state.getBusinessData().get(CallCenterUtil.PLAYWAIT_FIELD),0,""}}))
                        .putIfNotEmpty("user_data",conversationId)
                        .putIfNotEmpty("is_loop",true)
                        .put("areaId",state.getAreaId())
                        .build();
                RPCRequest rpcrequest = RPCRequest.newRequest(ServiceConstants.MN_CH_SYS_CONF_PLAY, _params);
                try {
                    rpcCaller.invoke(sessionContext, rpcrequest,true);
                } catch (Throwable t) {
                    logger.error(String.format("调用会议放音失败,appId=%s,res_id=%s,conversation_id=%s",
                            state.getAppId(),state.getResId(),state.getId()),t);
                }
            }
        }
        String initiator = conversationService.getInitiator(conversationId);
        if(initiator != null){
            try {
                conversationService.join(conversationId,initiator,null,null,null);
            } catch (YunhuniApiException e) {
                logger.info("加入交谈失败",e);
                conversationService.logicExit(conversationId,initiator);
            }
        }
    }
    public void conf(BusinessState state,String conf_id,String res_id){
        String user_data = state.getUserdata();
        Map<String,String> businessData = state.getBusinessData();

        //开始通知开发者
        if(logger.isDebugEnabled()){
            logger.debug("开始发送会议创建成功通知给开发者");
        }
        if(StringUtils.isNotBlank(state.getCallBackUrl())){
            Map<String,Object> notify_data = new MapBuilder<String,Object>()
                    .putIfNotEmpty("event","conf.create.succ")
                    .putIfNotEmpty("id",conf_id)
                    .putIfNotEmpty("subaccount_id",state.getSubaccountId())
                    .putIfNotEmpty("begin_time",System.currentTimeMillis())
                    .putIfNotEmpty("user_data",user_data)
                    .build();
            notifyCallbackUtil.postNotify(state.getCallBackUrl(),notify_data,3);
        }

        if(logger.isDebugEnabled()){
            logger.debug("会议创建成功通知发送成功");
        }
        if(logger.isDebugEnabled()){
            logger.debug("处理{}事件完成",getEventName());
        }
        Meeting meeting = meetingService.findById(conf_id);
        if(meeting != null){
            meeting.setResId(res_id);
            meeting.setStartTime(new Date());
            meetingService.save(meeting);
        }
    }
}
