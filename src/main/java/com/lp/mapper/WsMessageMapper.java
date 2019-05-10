package com.lp.mapper;

import com.lp.entity.WsMessage;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description: 消息Dao
 * @Author: 师岩岩
 * @Date: 2019/5/9 18:43
 */
@Repository
public interface WsMessageMapper {

    /**
     * 批量插入推送消息
     * @param messageList
     * @return
     */
    int insertBatchData(@Param("list") List<WsMessage> messageList);

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    WsMessage getMessage(Integer id);
}
