package com.lp.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description:聊天室WebSocket服务
 * @Author: 师岩岩
 * @Date: 2019/5/9 14:51
 */
@ServerEndpoint("/room")
@Component
public class
        ChatServer {

    private final static Logger logger = LoggerFactory.getLogger(ChatServer.class);

    private static Vector<Session> room = new Vector<Session>();

    /**
     * 用户接入
     *
     * @param session 可选
     */
    @OnOpen
    public void onOpen(Session session) {
        room.addElement(session);
    }

    /**
     * 接收到来自用户的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("[===> 接收消息]");
        //把用户发来的消息解析为JSON对象
        JSONObject obj = JSON.parseObject(message);
        String dateStr = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        obj.put("date", dateStr);
        //遍历聊天室中的所有会话
        room.forEach(session1 -> {
            //设置消息是否为自己的
            obj.put("isSelf", session1.equals(session));
            //发送消息给远程用户
            session1.getAsyncRemote().sendText(obj.toString());
        });
    }

    /**
     * 用户断开
     */
    @OnClose
    public void onClose(Session session) {
        room.remove(session);
    }

    /**
     * 用户连接异常
     */
    @OnError
    public void onError(Throwable t) {
    }
}
