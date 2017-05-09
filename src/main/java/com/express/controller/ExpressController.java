package com.express.controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.express.dao.ExpressShelfDao;
import com.express.model.Express;
import com.express.model.ExpressHistory;
import com.express.model.ExpressShelf;
import com.express.model.OverDueExpress;
import com.express.service.ExpressHistoryService;
import com.express.service.ExpressService;
import com.express.service.ExpressShelfService;
import com.express.service.OverDueExpressService;
import com.express.service.SendMailService;
import com.express.util.PropertyUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/express")
public class ExpressController {
	@Autowired
	ExpressService expressService;
	@Autowired
	ExpressShelfService expressShelfService;
	@Resource
	ExpressShelfDao expressShelfDao;
	@Autowired
	OverDueExpressService overDueExpressService;
	@Autowired
	ExpressHistoryService expressHistoryService;
	@Autowired
	SendMailService sendMailService;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ExpressController.class);

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toVegasIndex(Model model) {
		return "express/index";
	}

	@RequestMapping(value = "/getExpressListByPage/{pageNum}/pageNum/{pageSize}/pageSize", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<Express> getExpressListByPage(@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize, @RequestBody Express express) {
		PageInfo<Express> result = expressService.queryExpressInShelfListByPage(express, pageNum, pageSize);
		return result;
	}

	@RequestMapping(value = "/getExpress", method = RequestMethod.POST)
	@ResponseBody
	public Object getExpress(@RequestBody Express express) throws IOException {
		JSONObject jsonObject = new JSONObject(); // 用于返回位置信息
		String postCode = express.getVerificationCode(); // 用户传送的验证码
		String status = express.getStatus();
		// 判断订单验证码是否正确
		express = expressService.queryExpressDetail(express);
		if (expressService.affirmCode(express, postCode)) { // 判断是否通过验证
			switch (status) { // 根据
			case "O":
				// 过期快件处理
				// 获取OverDueExpress
				// 根据返回的OverDueExpress对象
				OverDueExpress overDueExpress = new OverDueExpress();
				overDueExpress.setExpress(express);
				overDueExpress = overDueExpressService.queryShelfByParams(overDueExpress);
				// 删除对应的overDueExpress记录并更新订单
				overDueExpress.setStatus("S");
				express.setStatus("S");
				express.setReciveDate(new Date());
				overDueExpressService.updateOverDueExpressShelf(overDueExpress);// 修改过期货柜表中的状态
				expressService.updateExpress(express);// 修改订单中的相应
				ExpressHistory expressHistory = new ExpressHistory();
				expressHistory.setShelfId("OverDueShelf");
				expressHistory.setExpress(express);
				expressHistory.setCreateDate(new Date());
				expressHistoryService.insertExpressHistory(expressHistory);// 插入历史记录
				// 发送已收件消息
				jsonObject.put("location", "OverDueShelf");
				break;
			case "E":
				// 当天快件处理
				ExpressShelf expressShelf = new ExpressShelf();
				expressShelf.setExpress(express);
				expressShelf = expressShelfService.queryShelfByParams(expressShelf);
				expressShelf.setShelfStatus("S");
				express.setStatus("S");
				express.setReciveDate(new Date());
				jsonObject.put("location", expressShelf.getShelfId());
				ExpressHistory expressHistoryE = new ExpressHistory();
				expressHistoryE.setShelfId(expressShelf.getShelfId().toString());
				expressHistoryE.setExpress(express);
				expressHistoryE.setCreateDate(new Date());
				expressShelf.setExpress(null);
				expressShelf.setShelfStatus("N");
				expressShelf.setCreateDate(null);
				expressShelfService.updateExpressShelf(expressShelf); // 修改货柜记录
				expressService.updateExpress(express);// 修改订单记录
				expressHistoryService.insertExpressHistory(expressHistoryE);// 插入历史记录
				// 发送已收件消息
				break;
			default:
				break;
			}
			sendMailService.sendGetExpressSuccessEmail(express);
		}
		return jsonObject;
	}

	@RequestMapping(value = "/resendEmail", method = RequestMethod.POST)
	@ResponseBody
	public Object resendEmail(@RequestBody Express express) throws IOException {
		JSONObject result = new JSONObject();
		express = expressService.queryExpressDetail(express);
		String verificationCode = DigestUtils
				.md5DigestAsHex((express.getVerificationCode() + PropertyUtil.getProperty("Salt")).getBytes())
				.substring(0, 6);// 生成验证码
		express.setVerificationCode(verificationCode);// 先更新后再重新给verficationCode赋值，避免入库
		try {
			sendMailService.sendVertificationCodeByEmail(express);
			result.put("message", "邮件已发送至您的邮箱，请接收！");
		} catch (IOException e) {
			logger.error("Send Mail Service connection error");
			result.put("message", "发送失败！");
		}
		return result;
	}
}
