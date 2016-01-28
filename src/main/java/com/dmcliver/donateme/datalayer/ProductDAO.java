package com.dmcliver.donateme.datalayer;

import java.util.List;

import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Product;

public interface ProductDAO {

	void save(Product product);
	List<String> getProductBrands(String potentialBrandName);
	Brand getProductBrand(String brand);
}