package com.example.rpcDemo.future;

import com.example.rpcDemo.msg.Request;
import com.example.rpcDemo.msg.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * @author：wwei
 * @date: 2022/5/15
 */
public class SyncWrite {
    public Response writeAndSync(final Channel channel, final Request request, final long timeout) throws Exception {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        if (request == null) {
            throw new NullPointerException("request");
        }
        if (timeout <= 0) {
            throw new IllegalArgumentException("timeout <= 0");
        }

        String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);
        WriteFuture<Response> future = new SyncWriteFuture(request.getRequestId());
        SyncWriteMap.syncKey.put(requestId, future);
        Response response = doWriteAndSync(channel, request, timeout, future);
        SyncWriteMap.syncKey.remove(requestId);
        return response;
    }

    private Response doWriteAndSync(final Channel channel, final Request request, final long timeout, final WriteFuture<Response> writeFuture) throws Exception {
        channel.writeAndFlush(request).addListener((ChannelFutureListener) future -> {
            writeFuture.setWriteResult(future.isSuccess());
            writeFuture.setCause(future.cause());
            if (!writeFuture.isWriteSuccess()) {
                SyncWriteMap.syncKey.remove(writeFuture.requestId());
            }
        });
        Response response = writeFuture.get(timeout, TimeUnit.MILLISECONDS);
        if (response == null) {
            if (writeFuture.isTimeout()) {
                throw new TimeoutException();
            } else {
                throw new Exception(writeFuture.cause());
            }
        }
        return response;
    }
}
