package com.express.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/express")
public class ExpressController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toIndex(Model model) {
		return "express/index";
	}
}

