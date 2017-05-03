package com.express.service.impl;

import com.express.dao.UserDao;
import com.express.model.User;
import com.express.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	
	@Override
	public User getUserById(String id) {
		return userDao.getUser(id);
	}

	@Override
	public List<User> getAllUsers() {
		return null;
	}

	@Override
	public void createUser(User user) {
		userDao.createUser(user);
	}

	@Override
	public void updateUser(User user) {

	}

	@Override
	public void deleteUser(User user) {

	}

}
