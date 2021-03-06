package com.lsxy.area.server.event.handler.call;

import com.lsxy.area.api.BusinessState;
import com.lsxy.area.api.BusinessStateService;
import com.lsxy.area.server.event.EventHandler;
import com.lsxy.area.server.service.callcenter.CallCenterUtil;
import com.lsxy.area.server.service.callcenter.ConversationService;
import com.lsxy.area.server.util.NotifyCallbackUtil;
import com.lsxy.framework.core.utils.MapBuilder;
import com.lsxy.framework.rpc.api.RPCRequest;
import com.lsxy.framework.rpc.api.RPCResponse;
import com.lsxy.framework.rpc.api.event.Constants;
import com.lsxy.framework.rpc.api.session.Session;
import com.lsxy.framework.rpc.exceptions.InvalidParamException;
import com.lsxy.yunhuni.api.app.service.AppService;
import com.lsxy.yunhuni.api.session.service.CallSessionService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by liuws on 2016/8/29.
 */
@Component
public class Handler_EVENT_SYS_CALL_ON_FAIL extends EventHandler{

    private static final Logger logger = LoggerFactory.getLogger(Handler_EVENT_SYS_CALL_ON_FAIL.class);

    @Autowired
    private BusinessStateService businessStateService;

    @Autowired
    private AppService appService;

    @Autowired
    private NotifyCallbackUtil notifyCallbackUtil;

    @Autowired
    private CallSessionService callSessionService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private Handler_EVENT_SYS_CALL_ON_RELEASE handler_event_sys_call_on_release;

    @Override
    public String getEventName() {
        return Constants.EVENT_SYS_CALL_ON_FAIL;
    }

    /***
     * 呼叫失败处理使用呼叫释放处理器
     * @param request
     * @param session
     * @return
     */
    @Override
    public RPCResponse handle(RPCRequest request, Session session) {
        Map<String,Object> params = request.getParamMap();
        if(MapUtils.isEmpty(params)){
            throw new InvalidParamException("request params is null");
        }
        String call_id = (String)params.get("user_data");

        if(StringUtils.isBlank(call_id)){
            //throw new InvalidParamException("call_id is null");
            logger.info("call_id is null");
            return null;
        }

        BusinessState state = businessStateService.get(call_id);
        if(state == null){
            throw new InvalidParamException("businessstate is null,call_id="+call_id);
        }

        if(BusinessState.TYPE_CC_INVITE_AGENT_CALL.equals(state.getType())){
            //坐席呼叫失败退出会议
            String conversationId = state.getBusinessData().get(CallCenterUtil.CONVERSATION_FIELD);
            if(conversationId != null){
                conversationService.logicExit(conversationId,call_id);
            }
        }
        if(BusinessState.TYPE_SYS_CONF.equals(state.getType())){
            String conf_id = state.getBusinessData().get("conf_id");
            Map<String,Object> notify_data = new MapBuilder<String,Object>()
                    .putIfNotEmpty("event","conf.join.fail")
                    .putIfNotEmpty("id",conf_id)
                    .putIfNotEmpty("subaccount_id",state.getSubaccountId())
                    .putIfNotEmpty("time",System.currentTimeMillis())
                    .putIfNotEmpty("call_id",call_id)
                    .putIfNotEmpty("user_data",state.getUserdata())
                    .build();
            notifyCallbackUtil.postNotify(state.getCallBackUrl(),notify_data,3);
        }
        return handler_event_sys_call_on_release.handle(request, session);
    }
}
