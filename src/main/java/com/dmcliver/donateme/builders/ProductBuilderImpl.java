package com.dmcliver.donateme.builders;

import org.springframework.stereotype.Component;

import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;

@Component
public class ProductBuilderImpl implements ProductBuilder {

	private Brand brand;
	public ProductBuilderImpl() {
		brand = null;
	}
	
	@Override
	public Product build(ProductCategory category, ProductModel model) {
		
		Product product = new Product();

		product.setModel(model.getModelName());
		product.setDescription(model.getDescription());
		product.setProductCategory(category);

		if(brand != null)
			product.setBrand(brand);
		
		return product;
	}

	@Override
	public ProductBuilder with(Brand brand) {
		
		this.brand  = brand;
		return this;
	}
}
