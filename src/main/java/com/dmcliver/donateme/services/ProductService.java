package com.dmcliver.donateme.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.primefaces.model.UploadedFile;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.domain.ProductListing;
import com.dmcliver.donateme.models.ProductModel;

public interface ProductService {

	ProductCategory createProductCategory(String newCategory, ProductCategory parent) throws CommonCheckedException;
	Brand createBrand(ProductModel model) throws CommonCheckedException;
	Product createProduct(ProductCategory productCategory, ProductModel model, List<UploadedFile> files) throws MalformedURLException, IOException, CommonCheckedException;
	Product createProduct(Brand brand, ProductCategory productCategory, ProductModel model, List<UploadedFile> files) throws MalformedURLException, IOException, CommonCheckedException;
	Product createProduct(ProductCategory productCategory, ProductModel model) throws CommonCheckedException;
	void saveListing(ProductListing listing);
}