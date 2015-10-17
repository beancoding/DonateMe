package com.dmcliver.donateme.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.ProductCategoryAggregate;
import com.dmcliver.donateme.models.TreeModel;

@Component
@ManagedBean
@SessionScoped
public class HomeControllerBean {

	private ProductCategoryDAO prodCatDAO;
	private TreeNode root;
	private TreeNodeBuilder builder;

	@Autowired
	public HomeControllerBean(ProductCategoryDAO prodCatDAO, TreeNodeBuilder builder) {
		
		this.prodCatDAO = prodCatDAO;
		this.builder = builder;
	}
	
	@PostConstruct
	public void initializeTree(){
		
		root = builder.build();
		List<ProductCategoryAggregate> topLevelCategories = prodCatDAO.getTopLevelInfo();
		List<TreeNode> rootNodeChildren = root.getChildren();
		topLevelCategories.forEach(pc -> builder.buildNode(rootNodeChildren, pc));
	}
	
	public void onTreeExpand(NodeExpandEvent event){
		
		TreeNode treeNode = event.getTreeNode();
		List<TreeNode> children = treeNode.getChildren();
		TreeModel prodCat = (TreeModel)treeNode.getData();
		
		builder.buildChildren(children, prodCat);
	}
	
	public TreeNode getCategories(){
		return root;
	}
}
