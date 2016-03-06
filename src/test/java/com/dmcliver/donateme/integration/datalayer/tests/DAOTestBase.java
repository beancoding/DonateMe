package com.dmcliver.donateme.integration.datalayer.tests;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DAOTestBase {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void save(Object entity) {
		sessionFactory.getCurrentSession().save(entity);
	}
}
