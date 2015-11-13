package com.dmcliver.donateme.controller.tests;

import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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

import org.mockito.runners.MockitoJUnitRunner;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerBeanTest {

	@Mock private ModelContainer container;
	@Mock private ProductDAO productDAO;
	@Mock private ProductCategoryDAO prodCatDAO;
	@Mock private TreeNodeBuilder treeBuilder;
	@Mock private ModelValidationMessages messages;
	@Mock private NodeSelectEvent event;
	
	@Test
	public void save_WithBrandNameAndProductCategorySelected_SavesWithProductNameOfBrand() {

		final String productBrandName = "SugarPlum";
		
		buildTreeModelForTreeNodeSelectEvent();
		ProductModel model = buildProductModel(productBrandName);		
		
		when(prodCatDAO.getById(any(UUID.class))).thenReturn(new ProductCategory(randomUUID(), "Cat1"));
		
		ProductControllerBean controller = new ProductControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages, model);
		controller.onTreeSelect(event);
		final String destination = controller.save();
		
		ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
		verify(productDAO).save(productCaptor.capture());
		
		Product product = productCaptor.getValue();
		assertThat(product.getProductName(), is(productBrandName));
		
		assertThat(destination, is("confirm"));
		
		verify(container).add(model, "model");
	}
	
	@Test
	public void save_WithNewCategory_SavesDownToDb() {
		
		final String brand = "Sugar";
		
		ProductModel model = buildProductModel(brand);
		
		ProductControllerBean controller = new ProductControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages, model);
		String pageView = controller.save();
		
		ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
		verify(productDAO).save(productCaptor.capture());
		
		Product product = productCaptor.getValue();
		assertThat(product.getProductName(), is(brand));
		assertThat(pageView, is("confirm"));
		verify(container).add(model, "model");
	}

	@Test
	public void save_WithNoProductCategorySelected_DoesntSaveToDbButReturnsErrorMessage() {
		
		ProductControllerBean controller = new ProductControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages);
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
		model.setNewCategory("Food");
		return model;
	}
}
