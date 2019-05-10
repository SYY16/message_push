package com.lp.controller;

import com.lp.entity.WsMessage;
import com.lp.entity.WsUser;
import com.lp.service.WsMessageService;
import com.lp.service.WsUserService;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yanyan
 * @create 2019-05-08 20:55
 * @desc 用户登录Controller
 **/
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private WsUserService wsUserService;
    @Autowired
    private WsMessageService wsMessageService;

    @RequestMapping(value = "login")
    public String login(WsUser wsUser, HttpSession session) {
        session.setAttribute("userInfo", wsUser);
        //保存到redis

        logger.info("[===>user：{} 登录成功!]", wsUser.getUserName());
        return "/msgPage";
    }

    @RequestMapping(value = "/sel")
    @ResponseBody
    public WsUser login(Integer id) {
        logger.info("[===>user：{} 登录成功!]",id);
        return wsUserService.getUserInfo(id);
    }

    @RequestMapping(value = "/sel1")
    @ResponseBody
    public WsMessage login1(Integer id) {
        logger.info("[===>user：{} 登录成功!]",id);
        return wsMessageService.getMessage(id);
    }
}
