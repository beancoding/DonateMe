package com.dmcliver.donateme.controller.tests;

import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.dmcliver.donateme.builders.TreeNodeBuilder;
import com.dmcliver.donateme.controller.helpers.ModelContainer;
import com.dmcliver.donateme.controller.helpers.ModelValidationMessages;
import com.dmcliver.donateme.controllers.ProductUploadControllerBean;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.models.TreeModel;
import com.dmcliver.donateme.services.ProductService;

import org.mockito.runners.MockitoJUnitRunner;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

@RunWith(MockitoJUnitRunner.class)
public class ProductUploadControllerBeanTest {

	private static final String newCategory = "Food";
	
	@Mock private ModelContainer container;
	@Mock private ProductDAO productDAO;
	@Mock private ProductCategoryDAO prodCatDAO;
	@Mock private TreeNodeBuilder treeBuilder;
	@Mock private ModelValidationMessages messages;
	@Mock private NodeSelectEvent event;
	@Mock private ProductService productService;
	
	@Test
	public void save_WithBrandNameAndProductCategorySelected_SavesWithProductNameOfBrand() throws MalformedURLException, IOException {

		Brand brand = new Brand();

		final String productBrandName = "SugarPlum";
		ProductCategory prodCat = new ProductCategory(randomUUID(), "Cat1");
		
		buildTreeModelForTreeNodeSelectEvent();
		ProductModel model = new ProductModelBuilder().build(productBrandName, null);		
		model.setNewCategory(null);
		
		when(prodCatDAO.getById(any(UUID.class))).thenReturn(prodCat);
		when(this.productService.createBrand(model)).thenReturn(brand);
		
		ProductUploadControllerBean controller = new ProductUploadControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages, model, productService);
		controller.onTreeSelect(event);
		final String destination = controller.save();
		
		verify(prodCatDAO).save(prodCat);
		verify(productService, never()).createProductCategory(anyString(), any(ProductCategory.class));
		verify(productService).createProduct(brand, prodCat, model, model.getFiles());
		verify(productService, never()).createProduct(prodCat, model, model.getFiles());
		verify(productService).createBrand(model);
		assertThat(destination, is("confirm"));
		verify(container).add(model, "model");
	}
	
	@Test
	public void save_WithNewCategoryAndExistingProductCategorySelectedButNoBrandName_SavesNewProductCategoryAndDoesntCreateBrand() throws MalformedURLException, IOException {
		
		ProductCategory parentProdCat = new ProductCategory(UUID.randomUUID(), "");
		ProductModel model = new ProductModelBuilder().with(parentProdCat).build(null, newCategory);
		
		ProductCategory prodCat = new ProductCategory(randomUUID(), "");
		when(this.productService.createProductCategory("Food", parentProdCat)).thenReturn(prodCat);
		
		ProductUploadControllerBean controller = new ProductUploadControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages, model, productService);
		String pageView = controller.save();
		
		verify(productService).createProductCategory("Food", parentProdCat);
		verify(prodCatDAO, never()).save(any(ProductCategory.class));
		verify(productService, never()).createBrand(model);
		verify(productService).createProduct(prodCat, model, model.getFiles());
		verify(productService, never()).createProduct(any(Brand.class), eq(prodCat), eq(model), eq(model.getFiles()));
		verify(container).add(model, "model");
		assertThat(pageView, is("confirm"));
		verify(container).add(model, "model");
	}
	
	@Test
	public void save_WithIOException_AddsErrorMessageAndReturnsToProductPage() throws MalformedURLException, IOException {
	
		ProductCategory prodCat = new ProductCategory(randomUUID(), "");
		when(this.productService.createProductCategory("Food", null)).thenReturn(prodCat);
		
		ProductModel model = new ProductModelBuilder().with((ProductCategory)null).build(null, newCategory);
		
		when(this.productService.createProduct(prodCat, model, model.getFiles())).thenThrow(new IOException());
		
		ProductUploadControllerBean controller = new ProductUploadControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages, model, productService);
		String page = controller.save();
		
		verify(this.messages).add("ProductImageSaveError");
		assertThat(page, is("uploadProduct"));
	}
	
	@Test
	public void save_WithNoProductCategorySelected_DoesntSaveToDbButReturnsErrorMessage() {
		
		ProductUploadControllerBean controller = new ProductUploadControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages, productService);
		String pageView = controller.save();
		
		verify(productDAO, never()).save(any(Product.class));
		verify(messages).add("CategoryRequired");
		assertThat(pageView, is("uploadProduct"));
	}
	
	@Test
	public void save_WithNewCategory_CallsProductServiceWithParentProductCategoryAndNewCategory() {
		
		ProductCategory prodCat = new ProductCategory(randomUUID(), "");
		
		ProductModel model = new ProductModelBuilder().with(prodCat).build(null, newCategory);
		
		ProductUploadControllerBean controller = new ProductUploadControllerBean(container, productDAO, prodCatDAO, treeBuilder, messages, model, productService);
		controller.save();
		
		verify(this.productService).createProductCategory(newCategory, prodCat);
	}
	
	private void buildTreeModelForTreeNodeSelectEvent() {
		
		TreeNode node = mock(TreeNode.class);
		when(event.getTreeNode()).thenReturn(node);
		when(node.getData()).thenReturn(new TreeModel("Cat1", randomUUID()));
	}
}

