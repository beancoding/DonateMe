package com.dmcliver.donateme.models;

import java.io.Serializable;
import java.util.UUID;

public class TreeModel implements Serializable {

	static final long serialVersionUID = -1241785008151879333L;
	
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
