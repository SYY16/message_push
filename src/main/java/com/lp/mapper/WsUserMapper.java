package com.lp.mapper;

import com.lp.entity.WsUser;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: 师岩岩
 * @Date: 2019/5/9 11:19
 */
@Repository
public interface WsUserMapper {

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    WsUser getUserInfo(Integer id);
}
