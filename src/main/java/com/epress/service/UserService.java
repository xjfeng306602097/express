package com.epress.service;

import java.util.List;

import com.epress.model.User;

public interface UserService {
	
	User getUserById(String id);
	
	List<User> getAllUsers();
	
	void createUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(User user);
	
}
