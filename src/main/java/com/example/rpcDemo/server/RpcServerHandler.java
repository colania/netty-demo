package com.example.rpcDemo.server;

import com.example.rpcDemo.msg.Request;
import com.example.rpcDemo.msg.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @description:
 * @author：wwei
 * @date: 2022/5/15
 */
public class RpcServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj){
        Request msg = (Request) obj;
        //反馈
        Response request = new Response();
        request.setRequestId(msg.getRequestId());
        request.setParam(msg.getResult() + " 请求成功，反馈结果请接受处理｛公众号：bugstack虫洞栈 博客栈：https://bugstack.cn｝。");
        ctx.writeAndFlush(request);
        //释放
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

}
