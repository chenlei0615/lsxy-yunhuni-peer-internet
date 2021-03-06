package com.lsxy.call.center.api.model;

import com.lsxy.framework.core.utils.JSONUtil;

import java.io.Serializable;

public class EnQueueResult implements Serializable {

    private AppExtension extension;
    private CallCenterAgent agent;
    private Integer fetchTime;
    private BaseEnQueue baseEnQueue;

    public CallCenterAgent getAgent() {
        return agent;
    }

    public void setAgent(CallCenterAgent agent) {
        this.agent = agent;
    }

    public AppExtension getExtension() {
        return extension;
    }

    public void setExtension(AppExtension extension) {
        this.extension = extension;
    }

    public BaseEnQueue getBaseEnQueue() {
        return baseEnQueue;
    }

    public void setBaseEnQueue(BaseEnQueue baseEnQueue) {
        this.baseEnQueue = baseEnQueue;
    }

    public Integer getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Integer fetchTime) {
        this.fetchTime = fetchTime;
    }

    @Override
    public String toString(){
        return JSONUtil.objectToJson(this);
    }
}

