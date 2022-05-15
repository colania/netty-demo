package com.example.rpcDemo.test;

import com.example.rpcDemo.server.ServerSocket;

/**
 * @description:
 * @author：wwei
 * @date: 2022/5/15
 */
public class StartServer {

    public static void main(String[] args) {
        new Thread(new ServerSocket()).start();
    }
}
