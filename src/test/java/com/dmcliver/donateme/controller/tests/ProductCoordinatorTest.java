package com.dmcliver.donateme.controller.tests;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.controllers.ProductCoordinatorImpl;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductListing;
import com.dmcliver.donateme.domain.User;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.services.ProductListingService;
import com.dmcliver.donateme.services.ProductService;
import com.dmcliver.donateme.services.UserService;

public class ProductCoordinatorTest {

	@Test
	public void saveNewProduct_WithNewCategory_CallsProductServiceToCreateCategory() throws IOException, CommonCheckedException {
		
		ProductModel model = new ProductModel() {{
			this.setNewCategory("NewCat");
		}};

		ProductService service = mock(ProductService.class);
		
		ProductCoordinatorImpl coordinator = new ProductCoordinatorImpl(service, mock(UserService.class), mock(ProductListingService.class));
		coordinator.saveNewProduct(null, model, null);
		
		verify(service).createProductCategory("NewCat", null);
	}
	
	@Test
	public void saveNewProduct_WithNoNewCategory_DoesntCallProductServiceToCreateCategory() throws IOException, CommonCheckedException {
		
		ProductModel model = new ProductModel() {{
			this.setNewCategory("");
		}};

		ProductService service = mock(ProductService.class);
		
		ProductCoordinatorImpl coordinator = new ProductCoordinatorImpl(service, mock(UserService.class), mock(ProductListingService.class));
		coordinator.saveNewProduct(null, model, null);
		
		verify(service, never()).createProductCategory("NewCat", null);
	}
	
	@Test
	public void saveNewProduct_withBrand_CallsProductServiceToCreateOrRetrieveExistingBrand() throws IOException, CommonCheckedException {
		
		ProductModel model = new ProductModel() {{
			this.setBrand("Brandy");
		}};

		ProductService service = mock(ProductService.class);
		
		ProductCoordinatorImpl coordinator = new ProductCoordinatorImpl(service, mock(UserService.class), mock(ProductListingService.class));
		coordinator.saveNewProduct(null, model, null);
		
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
		
		ProductCoordinatorImpl coordinator = new ProductCoordinatorImpl(service, mock(UserService.class), mock(ProductListingService.class));
		coordinator.saveNewProduct(null, model, null);
		
		verify(service, never()).createBrand(model);
		verify(service, never()).createProduct(null, null, model, model.getFiles());
		verify(service).createProduct(null, model, model.getFiles());
	}
	
	@Test
	public void saveNewProduct_WithUserName_CreatesProductListing() throws Exception {
		
		final String userName = "Sugar Ray Leonard";

		User user = new User("Sugar", "Ray", "Leonard", "sugar@email.com", "sugar");
		Product product = new Product("P", "Prod", null);
		ProductModel model = new ProductModel();

		UserService userService = mock(UserService.class);
		when(userService.getByUserName(userName)).thenReturn(user);

		ProductService prodService = mock(ProductService.class);
		when(prodService.createProduct(eq(null), eq(model), eq(model.getFiles()))).thenReturn(product);

		ProductListingService productListingService = mock(ProductListingService.class);
		
		ProductCoordinatorImpl coordinator = new ProductCoordinatorImpl(prodService, userService, productListingService);
		coordinator.saveNewProduct(null, model, userName);
		
		ArgumentCaptor<ProductListing> prodListType = ArgumentCaptor.forClass(ProductListing.class);
		verify(productListingService).saveListing(prodListType.capture());
		
		ProductListing prodListing = prodListType.getValue();
		
		assertThat(prodListing.getUser(), is(user));
		assertThat(prodListing.getProduct(), is(product));
		assertThat(prodListing.getDateListedExt().atStartOfDay(), is(LocalDate.now().atStartOfDay()));
		assertThat(prodListing.getDateSoldExt(), is((LocalDate)null));
	}
}
