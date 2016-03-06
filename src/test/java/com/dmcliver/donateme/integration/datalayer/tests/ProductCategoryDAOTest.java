package com.dmcliver.donateme.integration.datalayer.tests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategoryAggregate;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration("classpath:/servlet-context.xml")
public class ProductCategoryDAOTest extends DAOTestBase {

	@Autowired
	private ProductCategoryDAO prodCatDAO;

	@Test
	@Transactional
	public void getTopLevelCount_ReturnsProperCount() {
		
		List<ProductCategoryAggregate> topLevelCount = prodCatDAO.getTopLevelInfo();
		
		ProductCategoryAggregate data = topLevelCount.get(0);
		
		assertThat(topLevelCount.size(), either(is(1)).or(is(2)));
		assertThat(data.getProductCategoryName(), is("GrandParent"));
		assertThat(data.getChildCount(), is(1L));
	}
	
	@Test
	@Transactional
	public void getProducts_WithValidCategoryId_ReturnsAllProductsAssociatedWithTheId() {
		
		List<Product> products = prodCatDAO.getProducts(this.cat.getProductCategoryId());
		
		assertThat(products.size(), is(3));
		assertTrue(products.stream().anyMatch(p -> p.getProductId() == 1L && "model".equals(p.getModel()) && "description".equals(p.getDescription())));
		assertTrue(products.stream().anyMatch(p -> p.getProductId() == 2L && "model2".equals(p.getModel()) && "desc2".equals(p.getDescription())));
		assertTrue(products.stream().anyMatch(p -> p.getProductId() == 3L && "model3".equals(p.getModel()) && "desc3".equals(p.getDescription())));
	}
}
