package com.dmcliver.donateme.controller.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import com.dmcliver.donateme.controllers.ModelContainer;
import com.dmcliver.donateme.controllers.ProductControllerBean;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.models.ProductModel;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerBeanTest {

	@Mock private ModelContainer container;
	@Mock private ProductDAO productDAO;
	
	@Test
	public void save_WithBrandName_SavesWithProductNameOfBrand() {

		final String productBrandName = "SugarPlum";
		
		ProductControllerBean controller = new ProductControllerBean(container, productDAO);
		ProductModel model = controller.getModel();
		model.setBrand(productBrandName);
		controller.save();
		
		ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
		verify(productDAO).save(productCaptor.capture());
		verify(container).add(model, "model");
		
		Product product = productCaptor.getValue();
		assertThat(product.getProductName(), is(productBrandName));
	}
}

