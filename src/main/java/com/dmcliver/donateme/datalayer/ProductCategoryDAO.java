package com.dmcliver.donateme.datalayer;

import java.util.List;
import java.util.UUID;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
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

	ProductCategory getById(UUID prodCatId);

	void save(ProductCategory productCategory) throws CommonCheckedException;

	/**
	 * Finds the category by the category name - is case insensitive
	 * @param newCategory - The category name
	 * @return The category or null if none found
	 */
	ProductCategory getCategory(String newCategory);

	List<Product> getProducts(UUID prodCatId);
}

