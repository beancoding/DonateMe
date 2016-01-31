package com.dmcliver.donateme.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.builders.BrandBuildResult;
import com.dmcliver.donateme.builders.BrandBuilder;
import com.dmcliver.donateme.builders.ImageBuilder;
import com.dmcliver.donateme.builders.ProductBuilder;
import com.dmcliver.donateme.builders.ProductCategoryBuilder;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductCategoryDAO prodCatDAO;
	private ProductDAO productDAO;
	private ProductBuilder productBuilder;
	private ProductCategoryBuilder productCategoryBuilder;
	private BrandBuilder brandBuilder;
	private ImageBuilder imageBuilder;

	@Autowired
	public ProductServiceImpl(ProductCategoryDAO prodCatDAO, ProductDAO productDAO, ProductBuilder productBuilder, ProductCategoryBuilder productCategoryBuilder, BrandBuilder brandBuilder, ImageBuilder imageBuilder) {
		
		this.prodCatDAO = prodCatDAO;
		this.productDAO = productDAO;
		this.productBuilder = productBuilder;
		this.productCategoryBuilder = productCategoryBuilder;
		this.brandBuilder = brandBuilder;
		this.imageBuilder = imageBuilder;
	}

	@Override
	public ProductCategory createProductCategory(String newCategory) {

		ProductCategory productCategory = productCategoryBuilder.build(newCategory);
		prodCatDAO.save(productCategory);
		return productCategory;
	}

	public Brand createBrand(ProductModel model) {
		
		BrandBuildResult brandResult = brandBuilder.build(model);
		
		Brand brand = brandResult.getBrand();
		
		if(!brandResult.isExisting())
			productDAO.saveProductBrand(brand);
		
		return brand;
	}

	@Override
	public Product createProduct(Brand brand, ProductCategory productCategory, ProductModel model, List<UploadedFile> files) throws MalformedURLException, IOException {
		
		productBuilder.with(brand);
		return createProduct(productCategory, model, files);
	}
	
	@Override
	@Transactional
	public Product createProduct(ProductCategory productCategory, ProductModel model, List<UploadedFile> files) throws MalformedURLException, IOException {
		
		Product createdProduct = createProduct(productCategory, model);
		
		if(files != null && !files.isEmpty())
			imageBuilder.buildAll(createdProduct, files).forEach(i -> productDAO.saveProductImage(i));
		
		return createdProduct;
	}
	
	@Override
	public Product createProduct(ProductCategory productCategory, ProductModel model){
		
		Product product = productBuilder.build(productCategory, model);
		productDAO.save(product);
		return product;
	}
}
