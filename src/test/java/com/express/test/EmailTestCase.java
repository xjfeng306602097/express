package com.express.test;

import java.io.IOException;

import org.junit.Test;

import com.express.util.SendMailUtil;

public class EmailTestCase extends BaseTestCase {

	@Test
	public void test() throws IOException {
		SendMailUtil sm = new SendMailUtil();
		sm.doSendHtmlEmail("你好，同学", "这个是邮件测试，测试下我这个发送邮件的程序功能有没有成功，顺便想跟你说一下，我们这个代码的验证密码发送功能打算通过邮件实现，也就是我现在发给你的这个消息的实现，请你收到邮件后回复一下", "306602097@qq.com");
	}

}
