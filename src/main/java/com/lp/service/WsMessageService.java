package com.lp.service;

import com.lp.entity.WsMessage;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 消息Service
 * @Author: 师岩岩
 * @Date: 2019/5/9 18:43
 */
public interface WsMessageService {

    /**
     * 批量插入推送消息
     * @param messageList
     * @return
     */
    int insertBatchData(List<WsMessage> messageList);

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    WsMessage getMessage(Integer id);

}
