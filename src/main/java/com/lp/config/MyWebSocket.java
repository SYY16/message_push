package com.lp.config;

import static com.lp.config.WebSocketServer.addOnlineCount;

import com.lp.entity.WsMessage;
import com.lp.service.WsMessageService;
import com.lp.utils.ExcelUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * WebSocket服务类
 * @Author: 师岩岩
 * @Date: 2019/5/8 18:04
 */
@ServerEndpoint(value = "/websocket")
@Component
public class MyWebSocket {

    private static final Logger logger = LoggerFactory.getLogger(MyWebSocket.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 存储消息
     */
    private static List<WsMessage> messageList = new ArrayList<>();
    @Autowired
    private WsMessageService wsMessageService;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        String message = "连接成功。。。";
        WsMessage wsMessage = new WsMessage();
        wsMessage.setContent(message);
        wsMessage.setSender("服务端");
        wsMessage.setSendTime(new Date());
        sendMessage(message);
        messageList.add(wsMessage);
        logger.info("[===>] {}", message);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        logger.info("[===> 连接关闭！]");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info(message);
        String dateStr = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String msg = "服务器端(" + dateStr + ")：OK";
        sendMessage(msg);
        logger.info("[===> {}]", msg);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("[===> 发生错误]");
        error.printStackTrace();
    }

    /**
     * 发送消息
     */
    public void sendMessage(String message) {
        //确认session是否已经打开
        if (session.isOpen()) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("[===> 服务器推送消息发生异常，error{}]", e.getStackTrace());
                throw new RuntimeException("服务器推送消息发生IO异常");
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public static CopyOnWriteArraySet<MyWebSocket> getWebSocketSet() {
        return webSocketSet;
    }

    public static void setWebSocketSet(
            CopyOnWriteArraySet<MyWebSocket> webSocketSet) {
        MyWebSocket.webSocketSet = webSocketSet;
    }

    public static List<WsMessage> getMessageList() {
        return messageList;
    }

    public static void setMessageList(List<WsMessage> messageList) {
        MyWebSocket.messageList = messageList;
    }
}
