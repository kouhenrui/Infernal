package com.example.infernal.service.Impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @auther khr
 * @date 2022/8/29 14:39
 */
@Slf4j
//@Component
@ServerEndpoint(value="/websocket")
public class WebSocketServer {
    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
//        this.session = session;
//        webSocketSet.add(this);     //加入set中
        System.out.println("有新连接加入！当前在线人数为" + webSocketSet.size());
        try {
            sendMessage("连接成功，当前时间：" + new java.sql.Timestamp(System.currentTimeMillis()));
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 从set中删除
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("来自客户端的消息:" + message);

        // 群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


//    /**
//     * 发送消息
//     *
//     * @param message
//     * @throws IOException
//     */
    public void sendMessage(String message) throws IOException {
        this.session.getCookie().setComment(message);
        log.info("请求已发送");
//        this.session.getBasicRemote().sendText(message);
//         this.session.getAsyncRemote().sendText(message);
    }
//
//
//    /**
//     * 群发自定义消息
//     */
//    public static void sendInfo(String message) throws IOException {
//        for (MyWebSocket item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }



}
