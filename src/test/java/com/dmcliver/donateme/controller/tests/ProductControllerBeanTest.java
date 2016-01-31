package com.dmcliver.donateme.controller.tests;

import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.dmcliver.donateme.controllers.ModelContainer;
import com.dmcliver.donateme.controllers.ModelValidationMessages;
import com.dmcliver.donateme.controllers.ProductControllerBean;
import com.dmcliver.donateme.controllers.TreeNodeBuilder;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.models.TreeModel;
import com.dmcliver.donateme.services.ProductService;

import org.mockito.runners.MockitoJUnitRunner;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerBeanTest {

	private static final String newCategory = "Food";;
	
	@Mock private ModelContainer container;
	@Mock private ProductDAO productDAO;
	@Mock private ProductCategoryDAO prodCatDAO;
	@Mock private TreeNodeBuilder treeBuilder;
	@Mock private ModelValidationMessages messages;
	@Mock private NodeSelectEvent event;
	@Mock private ProductService productService;
	
	@Test
	public void save_WithBrandNameAndProductCategorySelected_SavesWithProductNameOfBrand() {

		final String productBrandName = "SugarPlum";
		ProductCategory prodCat = new ProductCategory(randomUUID(), "Cat1");
		
		buildTreeModelForTreeNodeSelectEvent();
		ProductModel model = buildProductModel(productBrandName);		
		model.setNewCategory(null);
		
		when(prodCatDAO.getById(any(UUID.class))).thenReturn(prodCat);
		
		ProductControllerBean controller = new ProductControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages, model, productService);
		controller.onTreeSelect(event);
		final String destination = controller.save();
		
		verify(prodCatDAO).save(prodCat);
		verify(productService, never()).createProductCategory(anyString());
		verify(productService).createBrand(model);
		assertThat(destination, is("confirm"));
		verify(container).add(model, "model");
	}
	
	@Test
	public void save_WithNewCategoryAndNoBrandName_SavesNewProductCategoryAndDoesntCreateBrand() {
		
		ProductModel model = buildProductModel(null);
		
		ProductControllerBean controller = new ProductControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages, model, productService);
		String pageView = controller.save();
		
		verify(productService).createProductCategory("Food");
		verify(prodCatDAO, never()).save(any(ProductCategory.class));
		verify(productService, never()).createBrand(model);

		verify(container).add(model, "model");
		assertThat(pageView, is("confirm"));
		verify(container).add(model, "model");
	}

	@Test
	public void save_WithNoProductCategorySelected_DoesntSaveToDbButReturnsErrorMessage() {
		
		ProductControllerBean controller = new ProductControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages, productService);
		String pageView = controller.save();
		
		verify(productDAO, never()).save(any(Product.class));
		verify(messages).add("CategoryRequired");
		assertThat(pageView, is("uploadProduct"));
	}
	
	private void buildTreeModelForTreeNodeSelectEvent() {
		
		TreeNode node = mock(TreeNode.class);
		when(event.getTreeNode()).thenReturn(node);
		when(node.getData()).thenReturn(new TreeModel("Cat1", randomUUID()));
	}
	
	private static ProductModel buildProductModel(final String brand) {
		
		ProductModel model = new ProductModel();
		model.setBrand(brand);
		model.setNewCategory(newCategory);
		return model;
	}
}
