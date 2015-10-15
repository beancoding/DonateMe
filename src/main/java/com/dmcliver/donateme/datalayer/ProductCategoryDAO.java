package com.dmcliver.donateme.datalayer;

import java.util.List;

import com.dmcliver.donateme.domain.ProductCategory;

public interface ProductCategoryDAO {

	List<ProductCategory> getTopLevelCategories();
}