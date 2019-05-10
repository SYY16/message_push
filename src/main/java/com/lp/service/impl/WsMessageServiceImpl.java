package com.lp.service.impl;

import com.lp.entity.WsMessage;
import com.lp.mapper.WsMessageMapper;
import com.lp.service.WsMessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: 师岩岩
 * @Date: 2019/5/9 18:43
 */
@Service
public class WsMessageServiceImpl implements WsMessageService {

    @Autowired
    private WsMessageMapper wsMessageMapper;

    @Override
    public int insertBatchData(List<WsMessage> messageList) {
        return wsMessageMapper.insertBatchData(messageList);
    }

    @Override
    public WsMessage getMessage(Integer id) {
        return wsMessageMapper.getMessage(id);
    }
}
