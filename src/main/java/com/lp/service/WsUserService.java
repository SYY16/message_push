package com.lp.service;

import com.lp.entity.WsUser;

/**
 * @Description:
 * @Author: 师岩岩
 * @Date: 2019/5/9 11:18
 */
public interface WsUserService {

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    WsUser getUserInfo(Integer id);
}
