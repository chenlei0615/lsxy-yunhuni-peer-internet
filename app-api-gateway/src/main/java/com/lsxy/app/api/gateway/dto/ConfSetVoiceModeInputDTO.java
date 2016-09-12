package com.lsxy.app.api.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by liuws on 2016/8/24.
 */
public class ConfSetVoiceModeInputDTO extends CommonDTO{

    @JsonProperty("call_id")
    private String callId;

    @JsonProperty("voice_mode")
    private Integer voiceMode;

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public Integer getVoiceMode() {
        return voiceMode;
    }

    public void setVoiceMode(Integer voiceMode) {
        this.voiceMode = voiceMode;
    }
}