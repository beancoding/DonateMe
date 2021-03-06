package com.dmcliver.donateme.builders;

import java.util.List;

import org.primefaces.model.TreeNode;

import com.dmcliver.donateme.domain.ProductCategoryAggregate;
import com.dmcliver.donateme.models.TreeModel;

public interface TreeNodeBuilder {

	TreeNode build();

	void buildNode(List<TreeNode> topLevel, ProductCategoryAggregate c);

	/**
	 * Builds the children for the current selected tree item if it hasn't been built yet using the treemodels selected item id to retrieve the data
	 */
	void buildChildren(List<TreeNode> children, TreeModel prodCat);
}
