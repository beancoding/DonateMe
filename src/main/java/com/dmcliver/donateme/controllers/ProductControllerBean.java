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

import static com.dmcliver.donateme.WebConstants.Strings.BLANK;
import static java.util.UUID.randomUUID;

import java.util.List;

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
		
		ProductCategory productCategory = model.getProductCategory();
		String newCategory = model.getNewCategory();

		if(productCategory == null && BLANK.equals(newCategory)) {
		
			validatorMessages.add("CategoryRequired");
			return "uploadProduct";
		}
		
		modelContainer.add(model, "model");
		
		Product product = new Product();
		product.setBrand(productDAO.getProductBrand(model.getBrand()));
		product.setModel(model.getModelName());
		product.setDescription(model.getDescription());
		
		if(productCategory == null) {
			
			productCategory = new ProductCategory(randomUUID(), newCategory); 
			prodCatDAO.save(productCategory);
		}

		product.setProductCategory(productCategory);
		productDAO.save(product);
		
		return "confirm";
	}
	
	public List<String> brandSearch(String potentialBrand) {
		
		return productDAO.getProductBrands(potentialBrand);
	}
	
	public void onTreeExpand(NodeExpandEvent event) {
		
		TreeNode treeNode = event.getTreeNode();
		treeBuilder.buildChildren(treeNode.getChildren(), (TreeModel)treeNode.getData());
	}
	
	public void onTreeSelect(NodeSelectEvent event) {
		
		UUID prodCatId = ((TreeModel)event.getTreeNode().getData()).getProductCategoryId();
		model.setProductCategory(prodCatDAO.getById(prodCatId));
	}
}
