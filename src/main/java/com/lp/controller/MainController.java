package com.lp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器入口
 */
@Controller
@RequestMapping(value = "/")
public class MainController {

  private static final Logger logger = LoggerFactory.getLogger(MainController.class);

  @RequestMapping(value = "hello")
  public String hello(){
    return "/myMsg";
  }

  /*
  a. 后端每五分钟向前端浏览器推送一条通知，通知内容自定，做一个简单页面，显示出后端推送的消息
  b. 前端可针对某一条通知进行回复内容，后端日志要明确打印出内容，并且在收到通知后，自动向前端浏览器
     推送一条OK消息
  c. 再后端向前端推送消息时间满一小时之后，自动生成一份excel保存到本地
   */

  @RequestMapping(value = "sendMessage")
  @ResponseBody
  public String sendMessage(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String message = "当前时间：" + sdf.format(new Date());
    logger.info(message);
    return message;
  }
    @RequestMapping(value = "index")
    public String index() {
        return "/index";
    }

  @RequestMapping(value = "room")
  public String room(){
    return "/room";
  }
}
