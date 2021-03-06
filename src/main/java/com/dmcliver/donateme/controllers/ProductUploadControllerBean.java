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

import static com.dmcliver.donateme.RequestLocaleFaultCodes.CategoryRequired;
import static com.dmcliver.donateme.RequestLocaleFaultCodes.ProductImageSaveError;
import static com.dmcliver.donateme.RequestLocaleFaultCodes.ProductSaveError;
import static com.dmcliver.donateme.WebConstants.Strings.*;

import java.io.IOException;
import java.util.List;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.builders.TreeNodeBuilder;
import com.dmcliver.donateme.controller.helpers.ModelContainer;
import com.dmcliver.donateme.controller.helpers.ModelValidationMessages;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.models.TreeModel;

@Component
@ViewScoped
@ManagedBean
public class ProductUploadControllerBean extends ControllerBeanBase {
	
	private ProductModel model;
	private ModelContainer modelContainer;

	private ProductDAO productDAO;
	private ProductCategoryDAO prodCatDAO;
	
	private TreeNodeBuilder treeBuilder;
	private ModelValidationMessages validatorMessages;
	private ProductCoordinator productCoordinator;

	@Autowired
	public ProductUploadControllerBean(ModelContainer modelContainer, ProductDAO productDAO, ProductCategoryDAO prodCatDAO, TreeNodeBuilder treeBuilder, ModelValidationMessages validatorMessages, ProductCoordinator productCoordinator) {
		this(modelContainer, productDAO, prodCatDAO, treeBuilder, validatorMessages, new ProductModel(), productCoordinator);
	}

	public ProductUploadControllerBean(ModelContainer modelContainer, ProductDAO productDAO, ProductCategoryDAO prodCatDAO, TreeNodeBuilder treeBuilder, ModelValidationMessages validatorMessages, ProductModel model, ProductCoordinator productCoordinator) {
		
		this.modelContainer = modelContainer;
		this.productDAO = productDAO;
		this.prodCatDAO = prodCatDAO;
		this.treeBuilder = treeBuilder;
		this.validatorMessages = validatorMessages;
		this.model = model;
		this.productCoordinator = productCoordinator;
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
		
		final String nosuccessPage = "productUpload";

		ProductCategory productCategory = model.getProductCategory();
		String newCategory = model.getNewCategory();

		if(productCategory == null && BLANK.equals(newCategory)) {
		
			validatorMessages.add(CategoryRequired);
			return nosuccessPage;
		}
		
		try {
			
			String userName = super.getPrincipalUserName();
			productCoordinator.saveNewProduct(productCategory, model, userName);
		}
		catch (IOException ex) {
			
			validatorMessages.add(ProductImageSaveError);
			return nosuccessPage;
		}
		catch (CommonCheckedException ex) {

			validatorMessages.add(ProductSaveError);
			return nosuccessPage;
		}
		
		modelContainer.add(model, "model");
		
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
}
