package com.example.rpcDemo.client;

import com.example.rpcDemo.codec.RpcDecoder;
import com.example.rpcDemo.codec.RpcEncoder;
import com.example.rpcDemo.msg.Request;
import com.example.rpcDemo.msg.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @description:
 * @author：wwei
 * @date: 2022/5/15
 */
public class ClientSocket implements Runnable {

    private ChannelFuture future;

    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try {
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.AUTO_READ, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(
                                    new RpcDecoder(Response.class),
                                    new RpcEncoder(Request.class),
                                    new MyRpcClientHandler()
                            );
                        }
                    });
            ChannelFuture f = null;
            f = b.bind(7397).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public ChannelFuture getFuture(){
        return future;
    }
}
