package com.dmcliver.donateme.integration.datalayer.tests;

import static java.util.UUID.randomUUID;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.tests.ProductBuilder;

public abstract class DAOTestBase {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private HibernateDataTest data;
	
	protected ProductCategory cat;
	
	protected Product product1;
	protected Product product2;
	protected Product product3;
	
	@Before
	public void generateTestData() {
		
		createProductCategory();
		
		createProducts();
		
		//possible constraint violation due to duplicate data
		try {
			data.canGenerateTestProductCategoryDataOk();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		} 
	}

	protected void createProductCategory() {

		cat = new ProductCategory(randomUUID(), "Music");
		save(cat);
	}
	
	protected void createProducts() {
		
		product1 = new ProductBuilder().withCategory(cat).build();
		product2 = new ProductBuilder().withId(2).withDesc("desc2").withModel("model2").withCategory(cat).build();
		product3 = new ProductBuilder().withId(3).withDesc("desc3").withModel("model3").withCategory(cat).build();
		
		save(product1);
		save(product2);
		save(product3);
	}
	
	@Transactional
	public void save(Object entity) {
		sessionFactory.getCurrentSession().save(entity);
	}
}
