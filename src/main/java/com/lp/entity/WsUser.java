package com.lp.entity;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 * @Description: 用户实体
 * @Author: 师岩岩
 * @Date: 2019/5/9 18:43
 */
@Entity
public class WsUser implements Serializable {
    private Integer id;
    private String userName;
    private String passWord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "WsUser{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
