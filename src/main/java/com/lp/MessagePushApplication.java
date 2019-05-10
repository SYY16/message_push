package com.lp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description: 启动类
 * @Author: 师岩岩
 * @Date: 2019/5/8 18:04
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.lp.mapper")
public class MessagePushApplication {

  public static void main(String[] args) {
    SpringApplication.run(MessagePushApplication.class, args);
  }

}
