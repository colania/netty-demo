package com.example.rpcDemo.client;

import com.example.rpcDemo.future.SyncWriteFuture;
import com.example.rpcDemo.future.SyncWriteMap;
import com.example.rpcDemo.future.WriteFuture;
import com.example.rpcDemo.msg.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description:
 * @author：wwei
 * @date: 2022/5/15
 */
public class MyRpcClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        Response msg = (Response) obj;
        String requestId = msg.getRequestId();
        SyncWriteFuture writeFuture = (SyncWriteFuture) SyncWriteMap.syncKey.get(requestId);
        if (writeFuture != null) {
            writeFuture.setResponse(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
