package com.dmcliver.donateme.builders;

import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;

public interface ProductBuilder {

	Product build(ProductCategory category, ProductModel model);
	ProductBuilder with(Brand brand);
}