package com.express.model;

import java.io.Serializable;

/**
 * Created by wshibiao on 2017/4/5.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 登录帐号
     */
    private String userId;
    /**
     * 用户
     */
    private String userName;
    private String password;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
