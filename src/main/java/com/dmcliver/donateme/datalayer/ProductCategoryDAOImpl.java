package com.dmcliver.donateme.datalayer;

import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Projections.groupProperty;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Restrictions.isNull;
import static org.hibernate.sql.JoinType.RIGHT_OUTER_JOIN;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.domain.ProductCategory;;

@Repository
public class ProductCategoryDAOImpl implements ProductCategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	@SuppressWarnings("unchecked")
	public List<ProductCategory> getTopLevelCategories(){
		
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(ProductCategory.class)
					  .add(isNull("parentProductCategory"))
					  .list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> getTopLevelCount(){
		
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(ProductCategory.class)
					  .createAlias("parentProductCategory", "pc", RIGHT_OUTER_JOIN)
					  .setProjection(
						  projectionList().add(groupProperty("pc.productCategoryId"))
						  				  .add(groupProperty("pc.productCategoryName"))
				   				   		  .add(count("productCategoryId"))
					  )
					  .add(isNull("pc.parentProductCategory"))
					  .list();
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ProductCategory> getChildCategories(UUID id){
		
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(ProductCategory.class)
					  .add(Restrictions.eq("productCategoryId", id))
					  .list();
	}
}
