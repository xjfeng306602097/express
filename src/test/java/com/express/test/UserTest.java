package com.express.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.express.model.User;
import com.express.service.UserService;
import com.express.util.PropertyUtil;

public class UserTest extends BaseTestCase {
	@Autowired
	private UserService userService;

	@Test
	public void test() throws IOException {
		User user = new User();
		user.setUserId("a306602097");
		user.setUserName("老肖");
		user.setPassword(DigestUtils
				.md5DigestAsHex(("a123456" + PropertyUtil.getProperty("Salt")).getBytes()));
		userService.createUser(user);
	}
	
	@Test
	public void testPassword() throws IOException {
		User params = new User();
		params.setUserId("a306602097");
		params.setPassword("a123456");
		String userId = params.getUserId();
		String password = DigestUtils
				.md5DigestAsHex((params.getPassword() + PropertyUtil.getProperty("Salt")).getBytes());
		User result = userService.getUserById(userId);
		System.out.println(password.equals(result.getPassword()));
	}

	@Test
	public void testString(){
		String text = "2017年11月11日";
		text = text.replace("年", "-");
		text = text.replace("月", "-");
		text = text.replace("日", "");
		System.out.println(text);
		System.out.println("搞事情，搞冲突");
	}
}
