package com.dmcliver.donateme.tests;

import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;

public class ProductBuilder {

	private long productId;
	private String model;
	private String description;
	private ProductCategory productCategory;
	private Brand brand;
	
	public ProductBuilder() {
		
		productId = 1;
		model = "model";
		description = "description";
	}
	
	public ProductBuilder withModel(String model) {
		
		this.model = model;
		return this;
	}
	
	public ProductBuilder withDesc(String desc) {
		
		description = desc;
		return this;
	}
	
	public ProductBuilder withCategory(ProductCategory cat) {
		
		productCategory = cat;
		return this;
	}
	
	public Product build() {
		
		Product product = new Product();
			
		product.setDescription(description);
		product.setModel(model);
		product.setProductId(productId);
		product.setProductCategory(productCategory);
		product.setBrand(brand);
		
		return product;
	}

	public ProductBuilder withId(int i) {

		productId = i;
		return this;
	}
}

