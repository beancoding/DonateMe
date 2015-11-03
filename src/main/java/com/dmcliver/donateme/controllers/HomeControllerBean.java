package com.dmcliver.donateme.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.ProductCategoryAggregate;
import com.dmcliver.donateme.models.TreeModel;

@Component
@ManagedBean
@ViewScoped
public class HomeControllerBean {

	private TreeNode root;
	
	private ProductCategoryDAO prodCatDAO;
	private TreeNodeBuilder builder;

	private String notice;

	@Autowired
	public HomeControllerBean(ProductCategoryDAO prodCatDAO, TreeNodeBuilder builder) {
		
		this.prodCatDAO = prodCatDAO;
		this.builder = builder;
	}
	
	@PostConstruct
	public void initializeTree() {
		
		root = builder.build();
		
		List<ProductCategoryAggregate> topLevelCategories = prodCatDAO.getTopLevelInfo();
		List<TreeNode> rootNodeChildren = root.getChildren();
		
		topLevelCategories.forEach(pc -> builder.buildNode(rootNodeChildren, pc));
	}
	
	public void onTreeExpand(NodeExpandEvent event) {
		
		TreeNode treeNode = event.getTreeNode();
		List<TreeNode> children = treeNode.getChildren();
		TreeModel prodCat = (TreeModel)treeNode.getData();
		
		builder.buildChildren(children, prodCat);
	}
	
	public void onSelect(NodeSelectEvent event) {
		notice = "Selected " + event.getTreeNode().getData();
	}
	
	public String getNotice() {
		return notice;
	}
	
	public TreeNode getCategories() {
		return root;
	}
	
	public String updateMessage(ActionEvent evt) {
		
		notice = "Button fired";
		return "template";
	}
}
