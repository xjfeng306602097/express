package com.express.timer.impl;

import com.express.model.Express;
import com.express.model.OverDueExpress;
import com.express.service.OverDueExpressService;
import com.express.service.SmsService;
import com.express.timer.IExecuteTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * Created by wshibiao on 2017/4/6.
 */
@Component
public class SendSMSTimer implements IExecuteTimer {

	private static final String SALT = "avadfa%^%#!&#%^fdafafa~@$%^$&&^%&erere}{}*(*&*^";

	@Autowired
	private SmsService smsService;
	@Autowired
	private OverDueExpressService overDueExpressService;

	// 每天早上8点
	// @Scheduled(cron = "0 */1 * * * ?")
	@Scheduled(cron = "0 0 8 * * ?")
	@Override
	public void execute() {
		// 获取隔日件的收件人联系方式
		String smsContent = "";
		List contacts = overDueExpressService.getContactsWithOverDue();
		// 发送短信
		if (contacts.size() > 0) {
			smsService.sendSMS(smsContent, contacts);
		}

	}

	// 每隔3小时
	@Scheduled(cron = "0 0 0/3 * * ?")
	public void sendMessage() {
		System.out.println("This is send message timer running");
	}

	@Scheduled(cron = "0 0/1 * * * ?")
	public void sendOverDueMessage() {
		OverDueExpress params = new OverDueExpress();
		params.setStatus("0");
		List<OverDueExpress> overDueExpressList = overDueExpressService.queryShelfListByParams(params);
		for (OverDueExpress overDueExpress : overDueExpressList) {
			Express express = overDueExpress.getExpress();
			String contact = express.getContact();// 获取手机号
			String verificationCode = DigestUtils.md5DigestAsHex((express.getVerificationCode() + SALT).getBytes())
					.substring(0, 6); // 获取验证码
			smsService.sendMessage(contact, "Your vertificationConde is " + verificationCode);
		}
	}
}
