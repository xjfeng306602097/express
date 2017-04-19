package com.express.controller;

import com.alibaba.fastjson.JSONObject;
import com.express.dao.ExpressShelfDao;
import com.express.model.Express;
import com.express.model.ExpressShelf;
import com.express.service.ExpressService;
import com.express.service.ExpressShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/express")
public class ExpressController {
	@Autowired
	ExpressService expressService;
	@Autowired
	ExpressShelfService expressShelfService;
	@Resource
	ExpressShelfDao expressShelfDao;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toIndex(Model model) {
		return "express/express";
	}

	@ResponseBody
	@RequestMapping(value = "/getParam", method = RequestMethod.GET)
	public List<Express> getQueryParam(HttpServletRequest request, HttpServletResponse response) {

		String contact = request.getParameter("contact");
		String expressNo = request.getParameter("expressNo");
		String arriveDate = request.getParameter("arriveDate");
		List<Express> expresses = expressService.queryExpressInfo(contact, expressNo);
		return expresses;
	}

	@ResponseBody
	@RequestMapping(value = "/getShelf", method = RequestMethod.GET)
	public Object getExpressShelf(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		Long id = Long.parseLong(request.getParameter("id"));
		Express express = expressService.getExpressInfoById(id);
		ExpressShelf shelf = new ExpressShelf();
		shelf.setExpress(express);
		shelf = expressShelfDao.queryShelfByParams(shelf);
		return shelf;
	}

	@RequestMapping(value = "/vegas", method = RequestMethod.GET)
	public String toVegasIndex(Model model) {
		return "express/index";
	}

	@RequestMapping(value = "/getExpressList", method = RequestMethod.POST)
	@ResponseBody
	public List<Express> getExpressList(@RequestBody Express express) {
		List<Express> expressList = expressService.queryExpressInfo(express.getContact(), express.getExpressNo());
		return expressList;
	}
}
