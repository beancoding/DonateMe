package com.dmcliver.donateme.controllers;

import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.ProductCategoryAggregate;
import com.dmcliver.donateme.models.TreeModel;

@Component
public class TreeNodeBuilderImpl implements TreeNodeBuilder {

	private ProductCategoryDAO prodCatDAO;

	@Autowired
	public TreeNodeBuilderImpl(ProductCategoryDAO prodCatDAO) {
		this.prodCatDAO = prodCatDAO;
	}
	
	@Override
	public TreeNode build() {
		return new DefaultTreeNode("Root", null);
	}
	
	@Override
	public void buildChildren(List<TreeNode> children, TreeModel model) {
		
		if(children.size() == 1 && new Integer(-1).equals(children.get(0).getData())) {
		
			children.clear();
		
			prodCatDAO.getChildCategories(model.getProductCategoryId())
			  	  	  .forEach(pc -> buildNode(children, pc));
		}
	}
	
	@Override
	public void buildNode(List<TreeNode> topLevel, ProductCategoryAggregate prodCat) {
		
		DefaultTreeNode node = new DefaultTreeNode(new TreeModel(prodCat.getProductCategoryName(), prodCat.getProductCategoryId()));
		
		if(prodCat.getChildCount() > 0)
			node.getChildren().add(new DefaultTreeNode(-1, node));
		
		topLevel.add(node);
	}
}
