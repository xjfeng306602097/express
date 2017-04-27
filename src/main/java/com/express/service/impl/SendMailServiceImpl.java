package com.express.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.express.model.Express;
import com.express.service.SendMailService;
import com.express.util.PropertyUtil;
import com.express.util.SendMailUtil;

@Service
public class SendMailServiceImpl implements SendMailService {
	
	@Override
	public void sendVertificationCodeByEmail(Express express) throws IOException {
		SendMailUtil sm = new SendMailUtil();
		String title = PropertyUtil.getProperty("Esteem") + express.getConsignee() + PropertyUtil.getProperty("User");
		String content = "您的快件" + express.getExpressNo() + "已到达矿大物流,验证码为" + express.getVerificationCode(); 
		sm.doSendHtmlEmail(title, content, express.getEmailAddress());
	}

	@Override
	public void sendGetExpressSuccessEmail(Express express) throws IOException {
		SendMailUtil sm = new SendMailUtil();
		String title = PropertyUtil.getProperty("Esteem") + express.getConsignee() + PropertyUtil.getProperty("User");
		String content = "您的快件" + express.getExpressNo() + "已成功收件，欢迎您下次使用！";
		sm.doSendHtmlEmail(title, content, express.getEmailAddress());
	}
	
}
