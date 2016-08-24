package com.lsxy.framework.rpc.api.server;

import com.lsxy.framework.rpc.api.*;
import com.lsxy.framework.rpc.exceptions.SessionWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by tandy on 16/8/3.
 */
public abstract class AbstractServerRPCHandler implements RPCHandler {

    @Autowired
    private ServerSessionContext sessionContext;

    private static final Logger logger = LoggerFactory.getLogger(AbstractServerRPCHandler.class);
    
    //注册的监听
    protected Map<String,RequestListener> requestListeners = new HashMap<String,RequestListener>();


    @Autowired(required = false)
    private AbstractServiceHandler serviceHandler;

    @Autowired
    private RPCCaller rpcCaller;

    public ServerSessionContext getSessionContext() {
        return sessionContext;
    }

    private AtomicLong processRequestCount = new AtomicLong(0);


    /**
     * 查询处理次数
     * @return
     */
    public long getProcessRequestCount(){
        return processRequestCount.longValue();
    }

    /**
     * 注册监听器
     * @param listener
     */
    public void addRequestListener(RequestListener listener){
        if(requestListeners.get(listener.getSessionId())==null)
            requestListeners.put(listener.getSessionId(),listener);
    }

    /**
     * 移除监听器
     * @param listener
     */
    public void removeRequestListener(RequestListener listener){
        requestListeners.remove(listener.getSessionId());
    }


    /**
     * 消息统一处理入口
     * @param ctxObject  环境对象,根据环境不一样 类型也不一样  iosession in mina   channelcontext in netty
     * @param message
     */
    protected void process(Object ctxObject, RPCMessage message) throws SessionWriteException {
        if(message instanceof RPCRequest){
            RPCRequest request = (RPCRequest) message;
            RPCResponse response = null;
            if(logger.isDebugEnabled()){
                logger.debug("消息统一处理入口:{}",message);
            }
            if(request.getName().equals(ServiceConstants.CH_MN_CONNECT)){
                response = process_CH_MN_CONNECT(ctxObject,request);
            } else if (request.getName().equals(ServiceConstants.CH_MN_HEARTBEAT_ECHO)){    //心跳
                response = RPCResponse.buildResponse(request);
                response.setMessage(RPCResponse.STATE_OK);
            } else {
                if(serviceHandler != null){
                    if(logger.isDebugEnabled()){
                        logger.debug("serviceHandler is :{}" , serviceHandler.getClass().getName());
                    }
                    response = serviceHandler.handleService(request, getSessionInTheContextObject(ctxObject));
                }else{
                    if(logger.isDebugEnabled()){
                        logger.debug("未找到服务器端ServiceHandler Bean对象,丢弃请求:{}",request);
                    }
                }
            }
            if(response != null){
                Session session = getSessionInTheContextObject(ctxObject);
                session.write(response);
            }
            processRequestCount.incrementAndGet();

        }else if(message instanceof  RPCResponse){
            RPCResponse response = (RPCResponse) message;
            rpcCaller.receivedResponse(response);
        }else{

        }
    }




    /**
     * 处理连接命令 客户端连接到服务端,第一件事,发送CH_MN_CONNECT命令到服务器进行注册
     * 如果出现注册失败,就直接结束掉,如果注册成功,则返回成功的响应对象
     * @param ctx  根据环境不同而不同  mina 为 iosession   netty 为channelcontext
     * @param request   解析出来的请求对象
     * @return
     *      如果注册成功并且在IP白名单中,则允许连接  否则拒绝连接
     */
    protected abstract RPCResponse process_CH_MN_CONNECT(Object ctx, RPCRequest request);


}
