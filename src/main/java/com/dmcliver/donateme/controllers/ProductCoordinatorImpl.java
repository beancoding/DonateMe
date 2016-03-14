package com.dmcliver.donateme.controllers;

import static com.dmcliver.donateme.StringExt.isNullOrEmpty;
import static java.util.UUID.randomUUID;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.domain.ProductListing;
import com.dmcliver.donateme.domain.User;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.services.ProductListingService;
import com.dmcliver.donateme.services.ProductService;
import com.dmcliver.donateme.services.UserService;

@Component
public class ProductCoordinatorImpl implements ProductCoordinator {

	private ProductService productService;
	private UserService userService;
	private ProductListingService prodListService;

	@Autowired
	public ProductCoordinatorImpl(ProductService productService, UserService userService, ProductListingService prodListService) {
		
		this.productService = productService;
		this.userService = userService;
		this.prodListService = prodListService;
	}

	@Override
	@Transactional(rollbackFor = CommonCheckedException.class)
	public void saveNewProduct(ProductCategory productCategory, ProductModel model, String user) throws IOException, CommonCheckedException {

		productCategory = saveCategory(productCategory, model.getNewCategory());
		Product savedProduct = saveProduct(productCategory, model);
		saveProductListing(user, savedProduct);
	}

	private void saveProductListing(String user, Product savedProduct) {
		
		User foundUser = userService.getByUserName(user);
		ProductListing listing = new ProductListing(randomUUID(), foundUser, savedProduct);
		listing.setDateListed(LocalDate.now());
		prodListService.saveListing(listing);
	}

	private ProductCategory saveCategory(ProductCategory productCategory, String newCategory) throws CommonCheckedException {
		
		if(!isNullOrEmpty(newCategory))
			productCategory = productService.createProductCategory(newCategory, productCategory);
		
		return productCategory;
	}

	private Product saveProduct(ProductCategory productCategory, ProductModel model) throws IOException, CommonCheckedException {
		
		if(!isNullOrEmpty(model.getBrand())) {
		
			Brand brand = productService.createBrand(model);
			return productService.createProduct(brand, productCategory, model, model.getFiles());
		}
		else 
			return productService.createProduct(productCategory, model, model.getFiles());
	}
}
