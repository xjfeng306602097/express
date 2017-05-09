package com.express.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.express.dao.ExpressDao;
import com.express.dao.OverDueExpressDao;
import com.express.model.Express;
import com.express.model.OverDueExpress;
import com.express.service.ExpressService;
import com.express.service.SmsService;
import com.express.util.PropertyUtil;
import com.github.pagehelper.PageInfo;

public class TestCase extends BaseTestCase {

	@Autowired
	private SmsService smsService;
	
	@Autowired
	private ExpressService expressService;
	
	@Resource
	private ExpressDao expressDao;
	
	@Resource
	private OverDueExpressDao overDueExpressDao;

	private final String salt = "avadfa%^%#!&#%^fdafafa~@$%^$&&^%&erere}{}*(*&*^";
	
	private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 继承BaseTestCase即可
	 * @throws IOException 
	 */
	@Test
	public void test() throws IOException {
		OverDueExpress params = new OverDueExpress();
		params.setStatus("0");
		List<OverDueExpress> overDueExpressList = overDueExpressDao.queryShelfListByParams(params);
		for (OverDueExpress overDueExpress : overDueExpressList) {
			Express express = overDueExpress.getExpress();
			String contact = express.getContact();// 获取手机号
			String verificationCode = DigestUtils.md5DigestAsHex((express.getVerificationCode() + PropertyUtil.getProperty("Salt")).getBytes())
					.substring(0, 6); // 获取验证码
			smsService.sendMessage(contact, "Your vertificationConde is " + verificationCode);
		}
	}

	@Test
	public void testInsertExpressInfo() throws ParseException {
		Express express = new Express();
		express.setExpressNo("ADFABACVFAD2313");
		express.setFromDate(FORMAT.parse("2017-04-09 12:00:00"));
		express.setArriveDate(FORMAT.parse("2017-04-09 12:00:00"));
		express.setReceiveDate(new Date());
		express.setCompany("顺丰");
		express.setConsignee("老王");
		express.setContact("18319032131");
		String verificationCode = express.getExpressNo() + express.getContact() + salt;
		express.setVerificationCode(DigestUtils.md5DigestAsHex(verificationCode.getBytes()));
		express.setAddressSource("江苏徐州");
		express.setAddressDest("海南海口");
		express.setStatus("0");
		expressDao.insertExpressInfo(express);
	}
	
	@Test
	public void testQuery(){
		Express express = new Express();
		express.setContact("18319040556");
		List<Express> list = expressService.queryExpressInfo(express);
		System.out.println(list);
		PageInfo<Express> pageInfo = expressService.queryExpressListByPage(express, null, null);
		System.out.println(pageInfo);
	}
}
