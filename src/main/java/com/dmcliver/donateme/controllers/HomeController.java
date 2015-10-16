package com.dmcliver.donateme.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private ProductCategoryDAO productCategoryDAO;

	@Autowired
	public HomeController(ProductCategoryDAO productCategoryDAO) {
		this.productCategoryDAO = productCategoryDAO;
	}
	
	@RequestMapping(value = "/", method = GET)
	public String home(Locale locale, Model model) {

		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		productCategoryDAO.getTopLevelInfo();
		List<String> names = Arrays.asList("");
		
		model.addAttribute("names", names);
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
}
