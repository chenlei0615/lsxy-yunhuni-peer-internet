package com.lsxy.framework.web.rest;

/**
 * Created by Tandy on 2016/6/6.
 * 提供统一的REST相应对象封装
 * data 用于提供返回对象的封装
 *
 */
public class RestResponse<T> {
    //是否请求成功
    private boolean success;

    //如果success = false ，必须提供错误代号
    private String errorCode;

    //如果success = false,指出错误消息
    private String errorMsg;

    //如果success = true,可提供返回对象，也可以不提供
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    /**
     * 提供成功的静态方法，方便使用
     * @param data 希望作为返回值输出的结果对象
     * @return
     *                  返回success为true的相应对象
     */
    public static RestResponse success(Object data){
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(true);
        restResponse.setData(data);
        return restResponse;
    }

    /**
     * 提供失败对象的静态方法，方便输出
     * @param errorCode  错误码
     * @param errorMsg   错误消息
     * @return
     */
    public static RestResponse failed(String errorCode,String errorMsg){
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(false);
        restResponse.setErrorCode(errorCode);
        restResponse.setErrorMsg(errorMsg);
        return restResponse;
    }

    public <T> RestResponse<T> cloneOne() {
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(this.isSuccess());
        restResponse.setErrorMsg(this.getErrorMsg());
        restResponse.setErrorCode(this.getErrorCode());
        return restResponse;
    }
}
