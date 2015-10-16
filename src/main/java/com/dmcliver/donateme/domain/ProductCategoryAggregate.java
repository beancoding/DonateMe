package com.dmcliver.donateme.domain;

import java.util.UUID;

public class ProductCategoryAggregate {

	private UUID productCategoryId;
	private String productCategoryName = "";
	private Long childCount = -1L;

	public ProductCategoryAggregate(Object[] data) {

		if(data != null && data.length == 3) {
			
			productCategoryId = (UUID) data[0];
			productCategoryName = (String) data[1];
			childCount = (Long) data[2];
		}
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
