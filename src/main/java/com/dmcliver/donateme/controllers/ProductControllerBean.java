package com.dmcliver.donateme.controllers;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.models.TreeModel;

@Component
@ViewScoped
@ManagedBean
public class ProductControllerBean {
	
	private ProductModel model;
	private ProductCategory selectedCategory;
	
	private ModelContainer modelContainer;
	private ProductDAO productDAO;
	private ProductCategoryDAO prodCatDAO;
	private TreeNodeBuilder treeBuilder;
	private ModelValidationMessages validatorMessages;
	
	@Autowired
	public ProductControllerBean(ModelContainer modelContainer, ProductDAO productDAO, ProductCategoryDAO prodCatDAO, TreeNodeBuilder treeBuilder, ModelValidationMessages validatorMessages) {
		this(modelContainer, productDAO, prodCatDAO, treeBuilder, validatorMessages, new ProductModel());
	}

	public ProductControllerBean(ModelContainer modelContainer, ProductDAO productDAO, ProductCategoryDAO prodCatDAO, TreeNodeBuilder treeBuilder, ModelValidationMessages validatorMessages, ProductModel model) {
		
		this.modelContainer = modelContainer;
		this.productDAO = productDAO;
		this.prodCatDAO = prodCatDAO;
		this.treeBuilder = treeBuilder;
		this.validatorMessages = validatorMessages;
		this.model = model;
	}
	
	@PostConstruct
	public void init() {
		
		TreeNode root = treeBuilder.build();
		model.setRoot(root);
		prodCatDAO.getTopLevelInfo().forEach(tlpc -> treeBuilder.buildNode(root.getChildren(), tlpc));
	}
	
	public ProductModel getModel() {
		return model;
	}
	
	public String save() {
		
		if(selectedCategory == null) {
		
			validatorMessages.add("CategoryRequired");
			return "uploadProduct";
		}
		
		modelContainer.add(model, "model");
		
		Product product = new Product();
		product.setProductName(model.getBrand());
		product.setProductCategory(selectedCategory);
		productDAO.save(product);
		
		return "confirm";
	}
	
	public void catChanged() {
		model.setNotice("You've selected " + model.getSelected());
	}
	
	public void onTreeExpand(NodeExpandEvent event) {
		
		TreeNode treeNode = event.getTreeNode();
		treeBuilder.buildChildren(treeNode.getChildren(), (TreeModel)treeNode.getData());
	}
	
	public void onTreeSelect(NodeSelectEvent event) {
		
		UUID prodCatId = ((TreeModel)event.getTreeNode().getData()).getProductCategoryId();
		selectedCategory = prodCatDAO.getById(prodCatId);
	}
}
