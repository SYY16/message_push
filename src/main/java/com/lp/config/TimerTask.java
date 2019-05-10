package com.lp.config;

import com.lp.entity.WsMessage;
import com.lp.service.WsMessageService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 定时器
 * @Author: 师岩岩
 * @Date: 2019/5/8 18:04
 */
@Component
public class TimerTask {

    private final static Logger logger = LoggerFactory.getLogger(TimerTask.class);

    @Autowired
    private WsMessageService wsMessageService;

    /**
     * 服务器定时发送消息
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void sendMessage() {
        CopyOnWriteArraySet<MyWebSocket> webSocketSet = MyWebSocket.getWebSocketSet();
        MyWebSocket.getWebSocketSet();
        webSocketSet.forEach(c -> {
            c.onOpen(c.getSession());
        });
    }

    /**
     * 定时存储服务器推送消息
     */
    @Scheduled(cron = "0/60 * * * * ?")
    public void saveMessage() {
        List<WsMessage> messageList = MyWebSocket.getMessageList();
        if (messageList != null && messageList.size() > 0) {
            //存入数据库
            wsMessageService.insertBatchData(messageList);
            //清空消息列表
            messageList.clear();
            MyWebSocket.setMessageList(messageList);
        }
    }
}
