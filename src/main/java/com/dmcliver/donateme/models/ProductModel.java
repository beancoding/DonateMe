package com.dmcliver.donateme.models;

import org.primefaces.model.TreeNode;

import static com.dmcliver.donateme.WebConstants.Strings.BLANK;
import com.dmcliver.donateme.domain.ProductCategory;

public class ProductModel {

	private String brand;
	private String model;
	private String description;
	private TreeNode root;
	private String newCategory = BLANK;
	private ProductCategory productCategory;
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public TreeNode getRoot() {
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
	public String getNewCategory() {
		return newCategory;
	}
	public void setNewCategory(String newCategory) {
		this.newCategory = newCategory;
	}
	
	public ProductCategory getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
}
