package com.express.dao;

import com.express.model.User;

/**
 * Created by wshibiao on 2017/4/5.
 */
public interface UserDao {

    public void updateUserInfo(User user);

    public void createUser(User user);

    public User getUser(String id);
}
