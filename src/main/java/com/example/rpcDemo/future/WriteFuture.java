package com.example.rpcDemo.future;

import com.example.rpcDemo.msg.Response;

import java.util.concurrent.Future;

/**
 * @description:
 * @author：wwei
 * @date: 2022/5/15
 */
public interface WriteFuture<T> extends Future<T> {
    Throwable cause();

    void setCause(Throwable cause);

    boolean isWriteSuccess();

    void setWriteResult(boolean result);

    String requestId();

    T response();

    void setResponse(Response response);

    boolean isTimeout();

}
