package com.dmcliver.donateme.integration.tests;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.dmcliver.donateme.CommonCheckedException;
import static com.dmcliver.donateme.WebConstants.*;
import com.dmcliver.donateme.controllers.ProductCoordinator;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@ContextConfiguration("classpath:/servlet-context.xml")
public class ProductCoordinatorTest {

	@Autowired private ProductCategoryDAO prodCatDAO;
	@Autowired private ProductCoordinator coordinator;
	
	@Test
	public void save_InTransactionWithError_RollsbackAllData() throws IOException {

		boolean thrown = false;
		ProductModel model = buildProductModel();

		try {
			coordinator.saveNewProduct(null, model, null);
		} 
		catch (CommonCheckedException e) {
			thrown = true;
		}
		
		ProductCategory category = prodCatDAO.getCategory(model.getNewCategory());
		
		assertNull(category);
		assertTrue(thrown);
	}

	private static ProductModel buildProductModel() {
		
		ProductModel model = new ProductModel() {{
		
			setBrand(brand);
			setDescription(description);
			setModelName(modelName);
			setNewCategory(newCategory);
		}};
		
		return model;
	}
}
