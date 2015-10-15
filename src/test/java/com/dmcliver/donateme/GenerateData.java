package com.dmcliver.donateme;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.domain.ProductCategory;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration("classpath:/servlet-context.xml")
public class GenerateData {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	@Transactional
	public void generateProductCategory(){
		
		Session session = sessionFactory.getCurrentSession();
		
		ProductCategory grandParent = new ProductCategory(UUID.randomUUID(), "GrandParent");
		ProductCategory child = new ProductCategory(UUID.randomUUID(), "Child");
		child.setParentProductCategory(grandParent);
		ProductCategory grandChild = new ProductCategory(UUID.randomUUID(), "GrandChild");
		grandChild.setParentProductCategory(child);
		
		session.save(grandParent);
		session.save(child);
		session.save(grandChild);
	}
}
