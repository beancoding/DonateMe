package com.dmcliver.donateme.datalayer.tests;

import static com.dmcliver.donateme.domain.Role.ADMIN;
import static java.util.UUID.randomUUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@ContextConfiguration("classpath:/servlet-context.xml")
public class HibernateDataTest {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private PasswordEncoder encoder;
	
	//@Test
	@Transactional
	public void canGenerateTestProductCategoryDataOk() {
		
		Session session = sessionFactory.getCurrentSession();
		
		ProductCategory grandParent = new ProductCategory(randomUUID(), "GrandParent");
		ProductCategory child = new ProductCategory(randomUUID(), "Child");
		child.setParentProductCategory(grandParent);
		ProductCategory grandChild = new ProductCategory(randomUUID(), "GrandChild");
		grandChild.setParentProductCategory(child);
		
		session.save(grandParent);
		session.save(child);
		session.save(grandChild);
	}
	
	//@Test
	@Transactional
	public void generateAdminUser() {
		
		Session session = sessionFactory.getCurrentSession();
		String password = "Password1";
		password = encoder.encode(password);
		User user = new User("dmcliver", "Dan", "Mcliver", "admin@admin.com", password);
		user.setRole(ADMIN.privilege());
		user.setEnabled(false);
		session.save(user);
	}
	
	/**
	 * To allow maven build to complete when tests are commented out.
	 */
	@Test
	public void test() {
		
	}
}
