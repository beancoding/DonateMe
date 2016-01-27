package com.dmcliver.donateme.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dmcliver.donateme.DuplicateException;
import com.dmcliver.donateme.ErrorMessageLocator;
import com.dmcliver.donateme.LoggingFactory;

import static com.dmcliver.donateme.WebConstants.Messages.LoggingOutWhenNotLoggedIn;

import com.dmcliver.donateme.models.UserModel;
import com.dmcliver.donateme.services.UserService;

@Controller
public class AccountController {

	private UserService userService;
	private Logger logger;
	private ErrorMessageLocator errMessService;
	
	@Autowired
	public AccountController(UserService userService, LoggingFactory logFactory, ErrorMessageLocator errorMessageService) {
		
		this.userService = userService;
		this.errMessService = errorMessageService;
		this.logger = logFactory.create(getClass());
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

		model.addAttribute("loginfail", true);
		return "login";
	}
	
	@RequestMapping(value = "/register", method = GET)
	public String viewRegisterPage(Model model) {
		
		model.addAttribute("User", new UserModel());
		return "register";
	}
	
	@RequestMapping(value = "/register", method = POST)
	public String register(@Valid @ModelAttribute("User") UserModel model, BindingResult result, Locale locale) {
		
		if(result.hasErrors())
			return "register";
		
		if(!model.getConfirmPassword().equals(model.getPassword())) {
			
			result.reject("BadPassword", "*The passwords must match");
			return "register";
		}

		try {
			userService.save(model);
		} 
		catch (DuplicateException ex) {
			
			result.reject("DuplicateUser", errMessService.get("DuplicateUser", locale));
			return "register";
		}
		catch(Exception ex) {
			
			result.reject("BadUser", "Could not save user");
			return "register";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/logout", method = GET)
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		if(session != null)
			session.invalidate();
		else
			logger.info(LoggingOutWhenNotLoggedIn);
		
		return "redirect:/";
	}
}
