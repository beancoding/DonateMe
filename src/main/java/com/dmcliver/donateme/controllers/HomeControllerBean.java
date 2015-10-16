package com.dmcliver.donateme.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.ProductCategory;

@Component
@ManagedBean
@SessionScoped
public class HomeControllerBean {

	private ProductCategoryDAO prodCatDAO;
	private DefaultTreeNode root;

	@Autowired
	public HomeControllerBean(ProductCategoryDAO prodCatDAO){
		this.prodCatDAO = prodCatDAO;
	}
	
	@PostConstruct
	public void initializeTree(){
		
		root = new DefaultTreeNode("Root", null);
		List<ProductCategory> topLevelCategories = prodCatDAO.getTopLevelCategories();
		List<TreeNode> topLevel = root.getChildren();
		topLevelCategories.forEach(c -> buildNode(topLevel, c));
	}

	private boolean buildNode(List<TreeNode> topLevel, ProductCategory c) {
		
		TreeNode node = new DefaultTreeNode(c.getProductCategoryName(), root);
		node.getChildren().add(new DefaultTreeNode("", node));
		return topLevel.add(node);
	}
	
	public void onTreeExpand(NodeExpandEvent event){
		
		List<TreeNode> children = event.getTreeNode().getChildren();
		
		if(children.size() > 0)
			children.removeIf(t -> true);
		
		children.add(new DefaultTreeNode("OK"));
	}
	
	public String getMessage() {

		ProductCategory productCategory = prodCatDAO.getTopLevelCategories().get(0);
		return "1st product category: " + productCategory.getProductCategoryName();
	}
	
	public TreeNode getCategories(){
		return root;
	}
}
