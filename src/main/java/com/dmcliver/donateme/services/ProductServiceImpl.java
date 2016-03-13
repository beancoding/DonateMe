package com.dmcliver.donateme.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmcliver.donateme.CommonCheckedException;
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
import com.dmcliver.donateme.domain.ProductListing;
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
	public ProductCategory createProductCategory(String newCategory, ProductCategory parent) throws CommonCheckedException {

		ProductCategory category = prodCatDAO.getCategory(newCategory);
		if(category != null)
			return category;
		
		ProductCategory productCategory = productCategoryBuilder.build(newCategory, parent);
		prodCatDAO.save(productCategory);
		return productCategory;
	}

	@Override
	public Brand createBrand(ProductModel model) throws CommonCheckedException {
		
		BrandBuildResult brandResult = brandBuilder.build(model);
		
		Brand brand = brandResult.getBrand();
		
		if(!brandResult.isExisting())
			productDAO.saveProductBrand(brand);
		
		return brand;
	}

	@Override
	public Product createProduct(Brand brand, ProductCategory productCategory, ProductModel model, List<UploadedFile> files) throws MalformedURLException, IOException, CommonCheckedException {
		
		productBuilder.with(brand);
		return createProduct(productCategory, model, files);
	}
	
	@Override
	public Product createProduct(ProductCategory productCategory, ProductModel model, List<UploadedFile> files) throws MalformedURLException, IOException, CommonCheckedException {
		
		Product createdProduct = createProduct(productCategory, model);
		
		if(files != null && !files.isEmpty())
			imageBuilder.buildAll(createdProduct, files).forEach(i -> productDAO.saveProductImage(i));
		
		return createdProduct;
	}
	
	@Override
	public Product createProduct(ProductCategory productCategory, ProductModel model) throws CommonCheckedException {
		
		Product product = productBuilder.build(productCategory, model);
		productDAO.save(product);
		return product;
	}

	@Override
	public void saveListing(ProductListing listing) {
		productDAO.save(listing);
	}
}
