package com.dmcliver.donateme.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ProductCategory")
public class ProductCategory {

	public ProductCategory(UUID id, String name){
		productCategoryId = id;
		productCategoryName = name;
	}

	protected ProductCategory(){}
	
	private UUID productCategoryId;
	private String productCategoryName;
	private ProductCategory parentProductCategory;
	
	@Id
	@Column(name = "ProductCategoryId")
	public UUID getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(UUID productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	
	@Column(name = "ProductCategoryName", nullable = false)
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	
	@ManyToOne
	@JoinColumn(name = "ParentProductCategoryId")
	public ProductCategory getParentProductCategory() {
		return parentProductCategory;
	}
	public void setParentProductCategory(ProductCategory productCategory) {
		this.parentProductCategory = productCategory;
	}
}
