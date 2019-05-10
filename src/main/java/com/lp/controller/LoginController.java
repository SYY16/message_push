package com.lp.controller;

import com.lp.entity.WsUser;
import com.lp.utils.RedisUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:用户登陆
 * @Author: 师岩岩
 * @Date: 2019/5/9 13:58
 */
@RestController
@RequestMapping(value = "/")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private RedisUtils redisUtils;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(WsUser wsUser, HttpSession session) {
        if (wsUser == null) {
            return "100";
        }
        //判断redis中是否已存在有效的信息
        String password = redisUtils.get(wsUser.getUserName());
        if (password == null || !password.equals(wsUser.getPassWord())) {
            logger.info("[===>user：{} 保存redis成功!]", wsUser.getUserName());
            //保存到redis
            redisUtils.set(wsUser.getUserName(), wsUser.getPassWord());
        }
        logger.info("[===>user：{} 登录成功!]", wsUser.getUserName());
        return "200";
    }
}
