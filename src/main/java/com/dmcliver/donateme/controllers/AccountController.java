package com.dmcliver.donateme.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dmcliver.donateme.models.UserModel;
import com.dmcliver.donateme.services.UserService;

@Controller
public class AccountController {

	private UserService userService;
	
	@Autowired
	public AccountController(UserService userService){
		this.userService = userService;
	}
	
	@RequestMapping(value = "/admin", method = GET)
	public String admin() {
		
		return "admin";
	}
	
	@RequestMapping(value = "/login", method = GET)
	public String viewLoginPage() {
		
		return "login";
	}
	
	@RequestMapping(value = "/badlogin", method = GET)
	public String badLogin(Model model) {

		model.addAttribute("error", true);
		return "login";
	}
	
	@RequestMapping(value = "/register", method = GET)
	public String viewRegisterPage(Model model) {
		
		model.addAttribute("User", new UserModel());
		return "register";
	}
	
	@RequestMapping(value = "/register", method = POST)
	public String register(@Valid @ModelAttribute("User") UserModel model, BindingResult result){
		
		if(result.hasErrors())
			return "register";
		
		if(!model.getConfirmPassword().equals(model.getPassword())){
			
			result.reject("BadPassword", "*The passwords must match");
			return "register";
		}

		userService.save(model);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/logout", method = GET)
	public String logout(HttpServletRequest request){
		
		request.getSession(false).invalidate();
		return "redirect:/";
	}
}
