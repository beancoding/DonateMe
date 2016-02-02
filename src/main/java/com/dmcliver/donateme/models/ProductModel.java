package com.dmcliver.donateme.models;

import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

import static com.dmcliver.donateme.WebConstants.Strings.BLANK;

import java.util.LinkedList;
import java.util.List;

import com.dmcliver.donateme.domain.ProductCategory;

public class ProductModel {

	private String brand;
	private String modelName;
	private String description;
	private TreeNode root;
	private String newCategory = BLANK;
	private ProductCategory productCategory;
	private List<UploadedFile> files = new LinkedList<UploadedFile>();

	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
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
	
	public void addFile(UploadedFile file) {
		files.add(file);
	}
	
	public List<UploadedFile> getFiles() {
		return files;
	}
}
