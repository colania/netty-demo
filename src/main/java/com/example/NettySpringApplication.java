package com.example;

import com.example.websocket.server.WebSocketServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.net.InetSocketAddress;

/**
 * @author wwei
 */
@SpringBootApplication
@ComponentScan(value = "com.example.websocket")
//@ComponentScans(value = {@ComponentScan(value = "com.example.websocket.web"), @ComponentScan(value = "com.example.websocket.server")})
public class NettySpringApplication implements CommandLineRunner {

    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private int port;
    @Autowired
    private WebSocketServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(NettySpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(host, port);
        ChannelFuture channelFuture = nettyServer.bing(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destroy()));
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}
