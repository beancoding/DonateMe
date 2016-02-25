package com.dmcliver.donateme.datalayer;

import java.util.List;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Image;
import com.dmcliver.donateme.domain.Product;

public interface ProductDAO {

	void save(Product product) throws CommonCheckedException;
	List<String> getProductBrands(String potentialBrandName);
	Brand getProductBrand(String brand);
	void saveProductBrand(Brand brand) throws CommonCheckedException;
	void saveProductImage(Image i);
}