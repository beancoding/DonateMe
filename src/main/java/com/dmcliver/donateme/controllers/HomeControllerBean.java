package com.dmcliver.donateme.controllers;

import static com.dmcliver.donateme.WebConstants.Strings.TREE_ROOT;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.builders.TreeNodeBuilder;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.Product;
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

	private List<Product> products;

	@Autowired
	public HomeControllerBean(ProductCategoryDAO prodCatDAO, TreeNodeBuilder builder) {
		
		this.prodCatDAO = prodCatDAO;
		this.builder = builder;
	}
	
	@PostConstruct
	public void init() {
		products = new ArrayList<Product>();
	}
	
	//Tree/Get
	public TreeNode getCategories() {
		
		if(root == null || isEmptyTree()) {

			root = builder.build();
			
			List<ProductCategoryAggregate> topLevelCategories = prodCatDAO.getTopLevelInfo();
			List<TreeNode> rootNodeChildren = root.getChildren();
			
			topLevelCategories.forEach(pc -> builder.buildNode(rootNodeChildren, pc));
		}
		
		return root;
	}

	private boolean isEmptyTree() {
		
		Object data = root.getData();
		return TREE_ROOT.equals(data == null ? "" : data.toString()) && root.getChildren().isEmpty();
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
		
		TreeModel data = (TreeModel)event.getTreeNode().getData();
		notice = "Selected " + data;
		UUID prodCatId = data.getProductCategoryId();
		products.clear();
		products.addAll(prodCatDAO.getProducts(prodCatId));
	}
	
	//Notice/Post
	public String updateMessage() {
		
		notice = "Button fired";
		return "uploadProduct";
	}
	
	public List<Product> getProducts() {
		return products;
	}
}
