package com.express.service;

import com.express.model.User;

import java.util.List;

public interface UserService {
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	User getUserById(String id);
	
	/**
	 * 获取所有用户
	 * @return
	 */
	List<User> getAllUsers();
	
	/**
	 * 新增用户
	 * @param user
	 */
	void createUser(User user);
	
	/**
	 * 修改用户
	 * @param user
	 */
	void updateUser(User user);
	
	/**
	 * 删除用户
	 * @param user
	 */
	void deleteUser(User user);
	
}
