package com.dmcliver.donateme.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {

	@RequestMapping(value = "/admin", method = GET)
	public String admin() {
		
		return "admin";
	}
}
