package com.dmcliver.donateme.controllers;

import java.util.List;

import org.primefaces.model.TreeNode;

import com.dmcliver.donateme.domain.ProductCategoryAggregate;
import com.dmcliver.donateme.models.TreeModel;

public interface TreeNodeBuilder {

	TreeNode build();

	void buildNode(List<TreeNode> topLevel, ProductCategoryAggregate c);

	void buildChildren(List<TreeNode> children, TreeModel prodCat);
}
