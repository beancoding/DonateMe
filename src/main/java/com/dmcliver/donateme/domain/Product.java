package com.dmcliver.donateme.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {

	private long productId;
	private String model;
	private String description;
	private ProductCategory productCategory;
	private Brand brand;
	
	@Id
	@Column(name = "ProductId")
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	@Column(name = "Model", nullable = false)
	public String getModel() {
		return model;
	}
	public void setModel(String productName) {
		this.model = productName;
	}
	
	@ManyToOne
	@JoinColumn(name = "ProductCategoryId")
	public ProductCategory getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	
	@Column(name = "Description", nullable = false)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne
	@JoinColumn(name = "BrandId")
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
}
