package com.lp.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lp.utils.ExcelUtils;
import com.lp.utils.RedisUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Resource;
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
 * WebSocket服务类
 *
 * @Author: 师岩岩
 * @Date: 2019/5/8 18:04
 */
//@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 存储消息
     */
    List<String> messageList = new ArrayList<>();
    /**
     * 用于判断时间是否满一小时
     */
    Integer times = 0;

    @Resource
    private RedisUtils redisUtils;

    private static Vector<Session> room = new Vector<Session>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        room.addElement(session);
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        //查询数据库中消息记录
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String message = "服务器端(" + dateStr + ")：连接成功。。。";
        //sendMessage(message);
        logger.info(message);
        messageList.add(message);
        times ++;
        if (times == 12) {
            if (messageList != null && messageList.size() > 0) {
                ExcelUtils.exportExcel(messageList);
                //一小时清空一次
                messageList.clear();
            }
            //重新计时
            times = 0;
        }
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
        logger.info("连接关闭！");
        //导出Excel
        if (messageList != null && messageList.size() > 0) {
            ExcelUtils.exportExcel(messageList);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info(message);
        //把用户发来的消息解析为JSON对象
        JSONObject obj = JSON.parseObject(message);
        //向JSON对象中添加发送时间
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        obj.put("date", dateStr);
        //遍历聊天室中的所有会话
        for(Session se : room){
            //设置消息是否为自己的
            if (se.equals(session)){
                obj.put("uname", "我");
            }
            //发送消息给远程用户
            //se.getAsyncRemote().sendText(obj.toString());
            sendMessage(obj.toString());
        }
        sendMessage(obj.toString());
       // logger.info(msg);
        //messageList.add(msg);
        //将数据存入数据库
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("发生错误");
        //导出Excel
        if (messageList != null && messageList.size() > 0) {
            ExcelUtils.exportExcel(messageList);
        }
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
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }

    public static void setWebSocketSet(
            CopyOnWriteArraySet<WebSocketServer> webSocketSet) {
        WebSocketServer.webSocketSet = webSocketSet;
    }
}
