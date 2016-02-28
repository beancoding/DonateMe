package com.dmcliver.donateme.controller.tests;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.controllers.ProductCoordinatorImpl;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.services.ProductService;

public class ProductCoordinatorTest {

	@Test
	public void saveNewProduct_WithNewCategory_CallsProductServiceToCreateCategory() throws IOException, CommonCheckedException {
		
		ProductModel model = new ProductModel() {{
			this.setNewCategory("NewCat");
		}};

		ProductService service = mock(ProductService.class);
		
		ProductCoordinatorImpl coordinator = new ProductCoordinatorImpl(service);
		coordinator.saveNewProduct(null, model);
		
		verify(service).createProductCategory("NewCat", null);
	}
	
	@Test
	public void saveNewProduct_WithNoNewCategory_DoesntCallProductServiceToCreateCategory() throws IOException, CommonCheckedException {
		
		ProductModel model = new ProductModel() {{
			this.setNewCategory("");
		}};

		ProductService service = mock(ProductService.class);
		
		ProductCoordinatorImpl coordinator = new ProductCoordinatorImpl(service);
		coordinator.saveNewProduct(null, model);
		
		verify(service, never()).createProductCategory("NewCat", null);
	}
	
	@Test
	public void saveNewProduct_withBrand_CallsProductServiceToCreateOrRetrieveExistingBrand() throws IOException, CommonCheckedException {
		
		ProductModel model = new ProductModel() {{
			this.setBrand("Brandy");
		}};

		ProductService service = mock(ProductService.class);
		
		ProductCoordinatorImpl coordinator = new ProductCoordinatorImpl(service);
		coordinator.saveNewProduct(null, model);
		
		verify(service).createBrand(model);
		verify(service).createProduct(null, null, model, model.getFiles());
		verify(service, never()).createProduct(null, model, model.getFiles());
	}
	
	@Test
	public void saveNewProduct_withNoBrand_DoesntCallProductServiceToCreateOrRetrieveExistingBrand() throws IOException, CommonCheckedException {
		
		ProductModel model = new ProductModel() {{
			this.setBrand("");
		}};

		ProductService service = mock(ProductService.class);
		
		ProductCoordinatorImpl coordinator = new ProductCoordinatorImpl(service);
		coordinator.saveNewProduct(null, model);
		
		verify(service, never()).createBrand(model);
		verify(service, never()).createProduct(null, null, model, model.getFiles());
		verify(service).createProduct(null, model, model.getFiles());
	}
}
