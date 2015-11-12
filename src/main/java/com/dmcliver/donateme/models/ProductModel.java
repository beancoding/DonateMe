package com.dmcliver.donateme.models;

import static java.util.Arrays.asList;

import java.util.List;

import org.primefaces.model.TreeNode;

public class ProductModel {

	private String brand;
	private String model;
	private String description;
	private TreeNode root;

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

	public List<String> getCategories() {
		return asList("Sugar", "Salt", "Pepper");
	}

	private String selected = "";

	public void setSelected(String selected) {
		this.selected = selected;
	}
	public String getSelected() {
		return selected;
	}

	private String notice = "";

	public void setNotice(String notice){
		this.notice = notice;
	}
	public String getNotice() {
		return notice;
	}

	public TreeNode getRoot() {
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}
}
