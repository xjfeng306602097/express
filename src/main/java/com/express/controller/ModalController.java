package com.express.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Xiaojunfeng
 * This is the controller for getting modal, you can add modal here
 *
 */
@Controller
@RequestMapping("/express")
public class ModalController {

	@RequestMapping(value = "/searchModal", method = RequestMethod.GET)
	public String getExpressSearchModal() {
		return "modal/searchModal";
	}
}
