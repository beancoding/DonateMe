package com.dmcliver.donateme.builders;

import static java.util.UUID.randomUUID;

import org.springframework.stereotype.Component;

import com.dmcliver.donateme.domain.ProductCategory;

@Component
public class ProductCategoryBuilderImpl implements ProductCategoryBuilder {

	@Override
	public ProductCategory build(String newCategory) {
		return new ProductCategory(randomUUID(), newCategory); 
	}
}
