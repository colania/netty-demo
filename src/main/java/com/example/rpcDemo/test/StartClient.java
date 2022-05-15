package com.example.rpcDemo.test;

import com.alibaba.fastjson.JSON;
import com.example.rpcDemo.client.ClientSocket;
import com.example.rpcDemo.future.SyncWrite;
import com.example.rpcDemo.msg.Request;
import com.example.rpcDemo.msg.Response;
import io.netty.channel.ChannelFuture;

/**
 * @description:
 * @author：wwei
 * @date: 2022/5/15
 */
public class StartClient {

    private static ChannelFuture future;

    public static void main(String[] args) {
        ClientSocket client = new ClientSocket();
        new Thread(client).start();

        while (true) {
            try {
                if (null == future) {
                    future = client.getFuture();
                    Thread.sleep(500);
                    continue;
                }
                Request request = new Request();
                request.setResult("查询｛bugstack虫洞栈｝用户信息");
                SyncWrite s = new SyncWrite();
                Response response = s.writeAndSync(future.channel(), request, 1000);
                System.out.println("调用结果：" + JSON.toJSON(response));
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
