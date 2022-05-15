package com.example.websocket.web;

import com.example.websocket.server.WebSocketServer;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description:
 * @author：wwei
 * @date: 2022/5/14
 */
@RestController("/netty")
public class WsController {

    @Resource
    private WebSocketServer wsServer;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", "xiaohao");
        return "index";
    }

    @GetMapping("/localAddress")
    public String localAddress() {
        return "nettyServer localAddress " + wsServer.getChannel().localAddress();
    }

}
