package com.dmcliver.donateme.controller.tests;

import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;

public class ProductModelBuilder {
	
	private ProductCategory prodCat = null;
	private static String newProdCat =  "Food";
	
	public ProductModelBuilder with(ProductCategory prodCat) {
		
		this.prodCat = prodCat;
		return this;
	}
	
	public ProductModelBuilder with(String prodCat) {
		
		newProdCat = prodCat;
		return this;
	}
	
	public ProductModel build(final String brand, String category) {
		
		ProductModel model = new ProductModel();
		model.setBrand(brand);
		model.setNewCategory(newProdCat);
		model.setProductCategory(prodCat);
		return model;
	}
}