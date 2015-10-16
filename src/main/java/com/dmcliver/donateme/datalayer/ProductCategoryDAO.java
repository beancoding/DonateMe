package com.dmcliver.donateme.datalayer;

import java.util.List;
import java.util.UUID;

import com.dmcliver.donateme.domain.ProductCategoryAggregate;

public interface ProductCategoryDAO {

	/**
	 * Retrieves the top level parent categories and child count
	 */
	List<ProductCategoryAggregate> getTopLevelInfo();

	/**
	 * Gets all the child categories by the parent id
	 */
	List<ProductCategoryAggregate> getChildCategories(UUID parentId);
}