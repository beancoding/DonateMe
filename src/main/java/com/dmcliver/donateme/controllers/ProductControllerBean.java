package com.dmcliver.donateme.controllers;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.dmcliver.donateme.StringExt.isNullOrEmpty;
import static com.dmcliver.donateme.WebConstants.Strings.BLANK;

import java.io.IOException;
import java.util.List;

import com.dmcliver.donateme.builders.TreeNodeBuilder;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.models.TreeModel;
import com.dmcliver.donateme.services.ProductService;

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
	private ProductService productService;

	@Autowired
	public ProductControllerBean(ModelContainer modelContainer, ProductDAO productDAO, ProductCategoryDAO prodCatDAO, TreeNodeBuilder treeBuilder, ModelValidationMessages validatorMessages, ProductService productService) {
		this(modelContainer, productDAO, prodCatDAO, treeBuilder, validatorMessages, new ProductModel(), productService);
	}

	public ProductControllerBean(ModelContainer modelContainer, ProductDAO productDAO, ProductCategoryDAO prodCatDAO, TreeNodeBuilder treeBuilder, ModelValidationMessages validatorMessages, ProductModel model, ProductService productService) {
		
		this.modelContainer = modelContainer;
		this.productDAO = productDAO;
		this.prodCatDAO = prodCatDAO;
		this.treeBuilder = treeBuilder;
		this.validatorMessages = validatorMessages;
		this.model = model;
		this.productService = productService;
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

		if(!isNullOrEmpty(newCategory))
			productCategory = productService.createProductCategory(newCategory);
		else
			prodCatDAO.save(productCategory);
		
		try {
			
			if(!isNullOrEmpty(model.getBrand())) {
			
				Brand brand = productService.createBrand(model);
				productService.createProduct(brand, productCategory, model, model.getFiles());
			}
			else 
				productService.createProduct(productCategory, model, model.getFiles());
		}
		catch(IOException ex) {
			
			validatorMessages.add("ProductImageSaveError");
			return "uploadProduct";
		}
		
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
	
	public void handleFileUpload(FileUploadEvent evt) {
		model.addFile(evt.getFile());
	}
	
	public TreeNode getCategories() {
		return model.getRoot();
	}
}
