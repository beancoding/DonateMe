package com.dmcliver.donateme.controllers;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.builders.TreeNodeBuilder;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.ProductCategoryAggregate;
import com.dmcliver.donateme.models.TreeModel;

@Component
@ViewScoped
@ManagedBean
public class HomeControllerBean {

	private TreeNode root;
	
	private ProductCategoryDAO prodCatDAO;
	private TreeNodeBuilder builder;

	private String notice = "no notice";

	@Autowired
	public HomeControllerBean(ProductCategoryDAO prodCatDAO, TreeNodeBuilder builder) {
		
		this.prodCatDAO = prodCatDAO;
		this.builder = builder;
	}
	
	//Tree/Get
	public TreeNode getCategories() {
		
		if(root == null) {

			root = builder.build();
			
			List<ProductCategoryAggregate> topLevelCategories = prodCatDAO.getTopLevelInfo();
			List<TreeNode> rootNodeChildren = root.getChildren();
			
			topLevelCategories.forEach(pc -> builder.buildNode(rootNodeChildren, pc));
		}
		
		return root;
	}
	
	//Tree/Post
	public void onTreeExpand(NodeExpandEvent event) {
		
		TreeNode treeNode = event.getTreeNode();
		List<TreeNode> children = treeNode.getChildren();
		TreeModel prodCatTreeModel = (TreeModel)treeNode.getData();
		
		builder.buildChildren(children, prodCatTreeModel);
	}

	//Notice/Get
	public String getNotice() {
		return notice;
	}
	
	//Notice/Get/Id
	public void onSelect(NodeSelectEvent event) {
		notice = "Selected " + event.getTreeNode().getData();
	}
	
	//Notice/Post
	public String updateMessage() {
		
		notice = "Button fired";
		return "uploadProduct";
	}
}
