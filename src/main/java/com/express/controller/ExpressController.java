package com.express.controller;

import com.alibaba.fastjson.JSONObject;
import com.express.model.Express;
import com.express.model.ExpressHistory;
import com.express.model.ExpressShelf;
import com.express.model.OverDueExpress;
import com.express.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/express")
public class ExpressController {
	@Autowired
	ExpressService expressService;
	@Autowired
	ExpressShelfService expressShelfService;
	@Autowired
	OverDueExpressService overDueExpressService;
	@Autowired
	ExpressHistoryService expressHistoryService;
	@Autowired
	SendMailService sendMailService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toIndex(Model model) {
		return "express/express";
	}

	@RequestMapping(value = "/vegas", method = RequestMethod.GET)
	public String toVegasIndex(Model model) {
		return "express/index";
	}

	@RequestMapping(value = "/getExpressList", method = RequestMethod.POST)
	@ResponseBody
	public List<Express> getExpressList(@RequestBody Express express) {
		express.setStatus("E");
		List<Express> expressList = expressService.queryExpressInfo(express);
		express.setStatus("O");
		expressList.addAll(
				expressService.queryExpressInfo(express));
		return expressList;
	}

	@RequestMapping(value = "/getExpress", method = RequestMethod.POST)
	@ResponseBody
	public Object getExpress(@RequestBody Express express) throws IOException {
		JSONObject jsonObject = new JSONObject(); // 用于返回位置信息
		String postCode = express.getVerificationCode(); // 用户传送的验证码
		String status = express.getStatus();
		// 判断订单验证码是否正确
		express = expressService.queryExpressDetail(express);
		if(expressService.affirmCode(express, postCode)){ // 判断是否通过验证
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
}
