package com.dmcliver.donateme.datalayer;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.domain.ProductListing;

public class ProductListingDAOImpl implements ProductListingDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void save(ProductListing listing) {
		sessionFactory.getCurrentSession().save(listing);
	}
}