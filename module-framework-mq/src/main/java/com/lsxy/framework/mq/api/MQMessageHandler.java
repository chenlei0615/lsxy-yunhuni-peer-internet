package com.lsxy.framework.mq.api;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;

/**
 * Created by Tandy on 2016/7/23.
 */

public interface MQMessageHandler<T extends MQEvent> {
    public abstract void handleMessage(T message) throws JMSException;
}
