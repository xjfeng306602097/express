package com.express.timer.impl;

import com.express.model.Express;
import com.express.model.ExpressShelf;
import com.express.model.OverDueExpress;
import com.express.service.ExpressService;
import com.express.service.ExpressShelfService;
import com.express.service.OverDueExpressService;
import com.express.service.SendMailService;
import com.express.service.SmsService;
import com.express.timer.IExecuteTimer;
import com.express.util.PropertyUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by wshibiao on 2017/4/6.
 */
@Component
public class SendSMSTimer implements IExecuteTimer {
	
	private static final Logger logger = LoggerFactory.getLogger(SendSMSTimer.class);
	
	@Autowired
	private ExpressService expressService;
	@Autowired
	private ExpressShelfService expressShelfService;
	@Autowired
	private SendMailService sendMailService;
	@Autowired
	private SmsService smsService;
	@Resource
	private OverDueExpressService overDueExpressService;

	// 每天早上8点
	// @Scheduled(cron = "0 */1 * * * ?")
	@Scheduled(cron = "0 0 8 * * ?")
	@Override
	public void execute() throws IOException {
		// 获取隔日件的收件人联系方式
		List<Express> expresses = overDueExpressService.getExpressWithOverDue();
		for (Express express : expresses) {
			String verificationCode = DigestUtils
					.md5DigestAsHex((express.getVerificationCode() + PropertyUtil.getProperty("Salt")).getBytes())
					.substring(0, 6); // 获取验证码
			express.setVerificationCode(verificationCode);
			sendMailService.sendVertificationCodeByEmail(express);
		}
		logger.info("Send OverDueExpress email success");
	}

	// 每隔3小时
	@Scheduled(cron = "0 0 0/3 * * ?")
	public void sendMessage() throws IOException {
		ExpressShelf params = new ExpressShelf();
		params.setShelfStatus("N");
		List<ExpressShelf> expressShelfList = expressShelfService.queryShelfListByParams(params);
		List<Express> expressList = expressService.queryExpressInfo(null, null, "N");
		for (int i = 0; i < expressShelfList.size(); i++) {
			if (i < expressList.size()) {
				Express express = expressList.get(i);
				ExpressShelf expressShelf = expressShelfList.get(i);
				express.setStatus("E");
				expressShelf.setShelfStatus("E");
				expressShelf.setCreateDate(new Date());
				expressShelf.setExpress(express);
				expressService.updateExpress(express);
				expressShelfService.updateExpressShelf(expressShelf);
				String verificationCode = DigestUtils
						.md5DigestAsHex((express.getVerificationCode() + PropertyUtil.getProperty("Salt")).getBytes())
						.substring(0, 6);// 生成验证码
				express.setVerificationCode(verificationCode);// 先更新后再重新给verficationCode赋值，避免入库
				sendMailService.sendVertificationCodeByEmail(express);
			} else {
				break;
			}
		}
		logger.info("Move express to expressShelf and send vertificationcode email success");
	}
	
	/**
	 * 每天8点发送邮件
	 * @throws IOException
	 */
	@Scheduled(cron = "0 0 8 * * ?")
	public void sendOverDueMessage() throws IOException {
		OverDueExpress params = new OverDueExpress();
		params.setStatus("O");
		List<OverDueExpress> overDueExpressList = overDueExpressService.queryShelfListByParams(params);
		for (OverDueExpress overDueExpress : overDueExpressList) {
			Express express = overDueExpress.getExpress();
			String verificationCode = DigestUtils
					.md5DigestAsHex((express.getVerificationCode() + PropertyUtil.getProperty("Salt")).getBytes())
					.substring(0, 6); // 获取验证码
			express.setVerificationCode(verificationCode);
			sendMailService.sendVertificationCodeByEmail(express);
		}
		logger.info("Move express to expressShelf and send vertificationcode email success");
	}
}
