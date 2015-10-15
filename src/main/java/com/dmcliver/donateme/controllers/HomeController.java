package com.dmcliver.donateme.controllers;

import static java.util.stream.Collectors.toList;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dmcliver.donateme.datalayer.ProductCategoryDAO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private ProductCategoryDAO productCategoryDAO;

	@Autowired
	public HomeController(ProductCategoryDAO productCategory){
		this.productCategoryDAO = productCategory;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		List<String> names = productCategoryDAO.getTopLevelCategories()
											   .stream()
											   .map(pc -> pc.getProductCategoryName())
											   .collect(toList());
		
		model.addAttribute("names", names);
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
}
