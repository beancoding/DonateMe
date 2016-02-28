package com.dmcliver.donateme.controllers;

import static com.dmcliver.donateme.StringExt.isNullOrEmpty;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.services.ProductService;

@Component
public class ProductCoordinatorImpl implements ProductCoordinator {

	private ProductService productService;

	@Autowired
	public ProductCoordinatorImpl(ProductService productService) {
		this.productService = productService;
	}

	@Override
	@Transactional(rollbackFor = CommonCheckedException.class)
	public void saveNewProduct(ProductCategory productCategory, ProductModel model) throws IOException, CommonCheckedException {

		productCategory = saveCategory(productCategory, model.getNewCategory());
		saveProduct(productCategory, model);
	}

	private ProductCategory saveCategory(ProductCategory productCategory, String newCategory) throws CommonCheckedException {
		
		if(!isNullOrEmpty(newCategory))
			productCategory = productService.createProductCategory(newCategory, productCategory);
		
		return productCategory;
	}

	private void saveProduct(ProductCategory productCategory, ProductModel model) throws IOException, CommonCheckedException {
		
		if(!isNullOrEmpty(model.getBrand())) {
		
			Brand brand = productService.createBrand(model);
			productService.createProduct(brand, productCategory, model, model.getFiles());
		}
		else 
			productService.createProduct(productCategory, model, model.getFiles());
	}
}
