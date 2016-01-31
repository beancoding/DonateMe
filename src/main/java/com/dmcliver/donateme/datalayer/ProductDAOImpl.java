package com.dmcliver.donateme.datalayer;

import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ilike;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Image;
import com.dmcliver.donateme.domain.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void save(Product product) {
		
		Session session = sessionFactory.getCurrentSession();
		session.save(product);
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
							 .add(eq("brandName", brand))
							 .setMaxResults(1)
							 .uniqueResult();
	}

	@Transactional
	public void saveProductBrand(Brand brand) {
		sessionFactory.getCurrentSession().save(brand);
	}

	@Override
	@Transactional
	public void saveProductImage(Image image) {
		sessionFactory.getCurrentSession().save(image);
	}
}
