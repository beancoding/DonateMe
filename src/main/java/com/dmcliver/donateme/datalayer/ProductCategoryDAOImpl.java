package com.dmcliver.donateme.datalayer;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.domain.ProductCategory;

@Repository
public class ProductCategoryDAOImpl implements ProductCategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ProductCategory> getAll(){
		
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(ProductCategory.class).list();
	}
}
