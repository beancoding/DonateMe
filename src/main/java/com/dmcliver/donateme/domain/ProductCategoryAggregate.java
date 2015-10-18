package com.dmcliver.donateme.domain;

import java.util.UUID;

public class ProductCategoryAggregate {

	private UUID productCategoryId;
	private String productCategoryName = "";
	private Long childCount = -1L;

	public ProductCategoryAggregate(UUID id, String categoryName, Long count) {
		
		productCategoryId = id;
		productCategoryName = categoryName;
		childCount = count;
	}
	
	public UUID getProductCategoryId() {
		return productCategoryId;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public Long getChildCount() {
		return childCount == null ? -1L : childCount;
	}
}
