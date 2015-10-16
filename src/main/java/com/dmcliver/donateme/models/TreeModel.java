package com.dmcliver.donateme.models;

import java.util.UUID;

public class TreeModel {

	private String productCategoryName;
	private UUID productCategoryId;

	public TreeModel(String productCategoryName, UUID productCategoryId) {
		this.productCategoryName = productCategoryName;
		this.productCategoryId = productCategoryId;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}
	public UUID getProductCategoryId() {
		return productCategoryId;
	}
	
	@Override
	public String toString(){
		return productCategoryName;
	}
}
