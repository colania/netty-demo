package com.example.rpcDemo.msg;

/**
 * @description:
 * @author：wwei
 * @date: 2022/5/15
 */
public class Response {
    private String requestId;
    private String param;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
