package com.dmcliver.donateme.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.ProductCategory;

@Component
@ManagedBean
@SessionScoped
public class HomeControllerBean {

	private ProductCategoryDAO prodCatDAO;

	@Autowired
	public HomeControllerBean(ProductCategoryDAO prodCatDAO){
		this.prodCatDAO = prodCatDAO;
	}
	
	public String getMessage() {

		ProductCategory productCategory = prodCatDAO.getTopLevelCategories().get(0);
		return "1st product category: " + productCategory.getProductCategoryName();
	}
}
