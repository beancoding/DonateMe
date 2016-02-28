package com.dmcliver.donateme.integration.tests;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.dmcliver.donateme.CommonCheckedException;
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

	/**
	 * Unique model properties used for A.O.P test join-point cut to join on
	 * @see TestAspect
	 * @see ProductCoordinatorTest
	 */
	public static final String modelName = randomUUID().toString();
	public static final String description = randomUUID().toString();
	public static final String newCategory = randomUUID().toString();
	public static final String brand = randomUUID().toString();
	
	@Test
	public void save_InTransactionWithError_RollsbackAllData() throws IOException {

		boolean thrown = false;
		ProductModel model = buildProductModel();

		try {
			coordinator.saveNewProduct(null, model);
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
