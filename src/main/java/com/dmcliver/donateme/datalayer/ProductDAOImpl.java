package com.dmcliver.donateme.datalayer;

import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ilike;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.LoggingFactory;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Image;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductListing;

@Repository
public class ProductDAOImpl implements ProductDAO {

	private SessionFactory sessionFactory;
	private Logger logger;

	@Autowired
	public ProductDAOImpl(SessionFactory sessionFactory, LoggingFactory loggingFactory) {
		
		this.sessionFactory = sessionFactory;
		logger = loggingFactory.create(ProductDAOImpl.class);
	}
	
	@Transactional(rollbackFor = CommonCheckedException.class)
	public void save(Product product) throws CommonCheckedException {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			session.save(product);
		}
		catch (Exception ex) {
			
			logger.error("Could not save product", ex);
			throw new CommonCheckedException(ex);
		}
	}
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getProductBrands(String potentialBrandName) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return (List<String>)session.createCriteria(Brand.class)
					  				.add(ilike("brandName", potentialBrandName))
					  				.setProjection(property("brandName"))
					  				.list();
	}

	@Override
	@Transactional
	public Brand getProductBrand(String brand) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return (Brand)session.createCriteria(Brand.class)
							 .add(eq("brandName", brand).ignoreCase())
							 .setMaxResults(1)
							 .uniqueResult();
	}

	@Transactional(rollbackFor = CommonCheckedException.class)
	public void saveProductBrand(Brand brand) throws CommonCheckedException {
		
		try {
			sessionFactory.getCurrentSession().save(brand);
		}
		catch (Exception ex) {
			
			logger.error("Couldn't save brand", ex);
			throw new CommonCheckedException(ex);
		}
	}

	@Override
	@Transactional
	public void saveProductImage(Image image) {
		sessionFactory.getCurrentSession().save(image);
	}

	@Override
	@Transactional
	public void save(ProductListing listing) {
		sessionFactory.getCurrentSession().save(listing);
	}
}
