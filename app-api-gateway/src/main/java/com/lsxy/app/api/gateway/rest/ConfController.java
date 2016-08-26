package com.lsxy.app.api.gateway.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lsxy.app.api.gateway.StasticsCounter;
import com.lsxy.app.api.gateway.dto.ConfCreateInputDTO;
import com.lsxy.app.api.gateway.dto.ConfInviteCallInputDTO;
import com.lsxy.app.api.gateway.dto.ConfJoinInputDTO;
import com.lsxy.area.api.ConfService;
import com.lsxy.framework.web.rest.RestResponse;
import com.lsxy.framework.web.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuws on 2016/8/24.
 */
@RestController
public class ConfController extends AbstractAPIController{
    private static final Logger logger = LoggerFactory.getLogger(ConfController.class);

    @Autowired(required = false)
    private StasticsCounter sc;

    @Reference(timeout=3000)
    private ConfService confService;

    @RequestMapping(value = "/{accountId}/conf/create",method = RequestMethod.POST)
    public RestResponse create(HttpServletRequest request, @PathVariable String accountId,
                                    @RequestHeader(value = "AppID",required = false) String appId,
                                    @RequestBody ConfCreateInputDTO dto) {
        if(logger.isDebugEnabled()){
            logger.debug("创建会议API参数,accountId={},appId={},dto={}",accountId,appId,JSON.toJSON(dto));
        }
        String ip = WebUtils.getRemoteAddress(request);
        String callId = null;
        try {
            callId = confService.create(ip,appId,dto.getMaxDuration(),dto.getMaxParts(),
                    dto.getRecording(),dto.getAutoHangup(),dto.getBgmFile(),dto.getCallbackUrl(),dto.getUserData());
        } catch (Exception e) {
            return RestResponse.failed("0000x",e.getMessage());
        }
        Map<String,String> result = new HashMap<>();
        result.put("callId",callId);
        return RestResponse.success(result);
    }


    @RequestMapping(value = "/{accountId}/conf/{id}/dismiss",method = RequestMethod.POST)
    public RestResponse dismiss(HttpServletRequest request, @PathVariable String accountId,@PathVariable String id,
                               @RequestHeader(value = "AppID",required = false) String appId) {
        if(logger.isDebugEnabled()){
            logger.debug("解散会议API参数,accountId={},appId={},confId={}",accountId,appId,id);
        }
        String ip = WebUtils.getRemoteAddress(request);
        boolean result = false;
        try {
            result = confService.dismiss(ip,appId,id);
        } catch (Exception e) {
            return RestResponse.failed("0000x",e.getMessage());
        }
        return RestResponse.success(result);
    }

    @RequestMapping(value = "/{accountId}/conf/{id}/invite_call",method = RequestMethod.POST)
    public RestResponse invite(HttpServletRequest request, @PathVariable String accountId,@PathVariable String id,
                                @RequestHeader(value = "AppID",required = false) String appId,
                                @RequestBody ConfInviteCallInputDTO dto) {
        if(logger.isDebugEnabled()){
            logger.debug("创建会议API参数,accountId={},appId={},confId={}",accountId,appId,id);
        }
        String ip = WebUtils.getRemoteAddress(request);
        String callId = null;
        try {
            callId = confService.invite(ip,appId,id,
                    dto.getFrom(),dto.getTo(),dto.getCustomFrom(),dto.getCustomTo(),
                    dto.getMaxDuration(),dto.getMaxDialDuration(),
                    dto.getDialVoiceStopCond(),dto.getPlayFile(),dto.getVoiceMode());
        } catch (Exception e) {
            return RestResponse.failed("0000x",e.getMessage());
        }
        Map<String,String> result = new HashMap<>();
        result.put("callId",callId);
        return RestResponse.success(result);
    }

    @RequestMapping(value = "/{accountId}/conf/{id}/join",method = RequestMethod.POST)
    public RestResponse join(HttpServletRequest request, @PathVariable String accountId,@PathVariable String id,
                                   @RequestHeader(value = "AppID",required = false) String appId,
                                   @RequestBody ConfJoinInputDTO dto) {
        if(logger.isDebugEnabled()){
            logger.debug("创建会议API参数,accountId={},appId={},confId={}",accountId,appId,id);
        }
        String ip = WebUtils.getRemoteAddress(request);
        boolean  result = false;
        try {
            result = confService.join(ip,appId,id,dto.getCallId(),dto.getMaxDuration(),dto.getPlayFile(),dto.getVoiceMode());
        } catch (Exception e) {
            return RestResponse.failed("0000x",e.getMessage());
        }
        return RestResponse.success(result);
    }
}
