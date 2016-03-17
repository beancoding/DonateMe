package com.dmcliver.donateme.controller.tests;

import static com.dmcliver.donateme.RequestLocaleFaultCodes.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.primefaces.model.DefaultTreeNode;
import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.builders.TreeNodeBuilder;
import com.dmcliver.donateme.controller.helpers.ModelContainer;
import com.dmcliver.donateme.controller.helpers.ModelValidationMessages;
import com.dmcliver.donateme.controllers.ProductCoordinator;
import com.dmcliver.donateme.controllers.ProductUploadControllerBean;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.domain.ProductCategoryAggregate;
import com.dmcliver.donateme.models.ProductModel;

@RunWith(MockitoJUnitRunner.class)
public class ProductUploadControllerBeanTest {

	@Mock private ModelContainer container;
	@Mock private ProductDAO prodDAO;
	@Mock private ProductCategoryDAO prodCatDAO;
	@Mock private TreeNodeBuilder treeBuilder;
	@Mock private ModelValidationMessages validMess;
	@Mock private ProductCoordinator coordinator;

	private int i;
	
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}

	private static <T> T anyInstance(Class<T> c) {
		return org.mockito.Matchers.any(c);
	}
	
	@Test
	public void save_WithInvalidCategoryValidation_ReturnsValidaionMessageAndReturnsToSamePage() {
		
		ProductModel model = new ProductModel();
		
		ProductUploadControllerBean controller = new ProductUploadControllerBean(container, prodDAO, prodCatDAO, treeBuilder, validMess, model, coordinator);
		String result = controller.save();
		
		assertThat(result, is("productUpload"));
		verify(validMess).add(CategoryRequired);
	}
	
	@Test
	public void save_WithCoordinatorException_ReturnsValidationMessageAndReturnsToSamePage() throws IOException, CommonCheckedException {
				
		ProductModel model = new ProductModel() {{
			setNewCategory("Yollo");
		}};
		
		doThrow(new CommonCheckedException(new Exception())).when(coordinator).saveNewProduct(Mockito.any(ProductCategory.class), eq(model), eq(null));
		
		ProductUploadControllerBean controller = new ProductUploadControllerBean(container, prodDAO, prodCatDAO, treeBuilder, validMess, model, coordinator);
		String result = controller.save();
		
		assertThat(result, is("productUpload"));
		verify(validMess).add(ProductSaveError);
	}
	
	@Test
	public void save_WithCoordinatorIOException_ReturnsValidationMessageAndReturnsToSamePage() throws IOException, CommonCheckedException {
		
		ProductModel model = new ProductModel() {{
			setNewCategory("Yollo");
		}};
		
		doThrow(new IOException(new Exception())).when(coordinator).saveNewProduct(Mockito.any(ProductCategory.class), eq(model), eq(null));
		
		ProductUploadControllerBean controller = new ProductUploadControllerBean(container, prodDAO, prodCatDAO, treeBuilder, validMess, model, coordinator);
		String result = controller.save();
		
		assertThat(result, is("productUpload"));
		verify(validMess).add(ProductImageSaveError);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void init_ConstructsTreeProperly() {
		
		when(treeBuilder.build()).thenReturn(new DefaultTreeNode("Rootz", null));
		when(prodCatDAO.getTopLevelInfo()).thenReturn(asList(buildProdCatAggregate(), buildProdCatAggregate()));
		
		ProductModel model = new ProductModel();
		
		ProductUploadControllerBean controller = new ProductUploadControllerBean(container, prodDAO, prodCatDAO, treeBuilder, validMess, model, coordinator);
		controller.init();
		
		assertThat(model.getRoot().getData(), is("Rootz"));
		verify(treeBuilder, times(2)).buildNode(anyList(), anyInstance(ProductCategoryAggregate.class));
	}

	private ProductCategoryAggregate buildProdCatAggregate() {
		return new ProductCategoryAggregate(UUID.randomUUID(), "catName", (long)0);
	}
}
