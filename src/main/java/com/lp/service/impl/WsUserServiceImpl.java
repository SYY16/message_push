package com.lp.service.impl;

import com.lp.mapper.WsUserMapper;
import com.lp.entity.WsUser;
import com.lp.service.WsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: 师岩岩
 * @Date: 2019/5/9 11:18
 */
@Service
public class WsUserServiceImpl implements WsUserService {

    @Autowired
    private WsUserMapper wsUserMapper;

    @Override
    public WsUser getUserInfo(Integer id) {
        return wsUserMapper.getUserInfo(id);
    }
}
