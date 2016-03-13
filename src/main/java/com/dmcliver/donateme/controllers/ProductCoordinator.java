package com.dmcliver.donateme.controllers;

import java.io.IOException;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;

public interface ProductCoordinator {

	void saveNewProduct(ProductCategory productCategory, ProductModel model, String user) throws IOException, CommonCheckedException;
}