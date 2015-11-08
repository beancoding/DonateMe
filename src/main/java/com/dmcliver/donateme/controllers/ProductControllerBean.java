package com.dmcliver.donateme.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.models.ProductModel;

@Component
@ViewScoped
@ManagedBean
public class ProductControllerBean {
	
	private ProductModel model;

	private ModelContainer modelContainer;
	private ProductDAO productDAO;
	
	@Autowired
	public ProductControllerBean(ModelContainer modelContainer, ProductDAO productDAO) {
		
		this.modelContainer = modelContainer;
		this.productDAO = productDAO;
		this.model = new ProductModel();
	}
	
	public ProductModel getModel() {
		return model;
	}
	
	public String save() {
		
		modelContainer.add(model, "model");
		
		Product product = new Product();
		product.setProductName(model.getBrand());
		productDAO.save(product);
		
		return "confirm";
	}
}
