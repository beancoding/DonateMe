package com.dmcliver.donateme.datalayer;

import static java.util.stream.Collectors.toList;
import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Projections.groupProperty;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.isNull;
import static org.hibernate.sql.JoinType.RIGHT_OUTER_JOIN;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.domain.ProductCategoryAggregate;

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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ProductCategoryAggregate> getTopLevelInfo(){
		
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> result = buildQuery(session).add(isNull("pc.parentProductCategory")).list();
		return map(result);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ProductCategoryAggregate> getChildCategories(UUID parentId){
		
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> result = buildQuery(session).createAlias("pc.parentProductCategory", "gpc")
												   .add(eq("gpc.productCategoryId", parentId))
												   .list();
		return map(result);
	}
	
	/**
	 * HQL:
	 * select pc.productCategoryId, pc.productCategoryName, count(productCategoryId)
	 * from ProductCategory
	 * right outer join ProductCategory.parentProductCagory pc 
	 * group by pc.productCategoryId, pc.productCategoryName
	 * 
	 * SQL:
	 * Is exactly the same except for the right outer join which is shown below 
	 * right outer join ProductCategory pc on pc.ProductCategoryId = ProductCategory.parentProductCategoryId
	 */
	private Criteria buildQuery(Session session){

	   return session.createCriteria(ProductCategory.class)
				     .createAlias("parentProductCategory", "pc", RIGHT_OUTER_JOIN)
				     .setProjection(
				    		 
			    		 projectionList().add(groupProperty("pc.productCategoryId"))
					  				   	 .add(groupProperty("pc.productCategoryName"))
					  				   	 .add(count("productCategoryId"))
		    		 );
	}
	
	private List<ProductCategoryAggregate> map(List<Object[]> list) {
		
		return list.stream()
				   .map(res -> new ProductCategoryAggregate(res))
				   .collect(toList());
	}
}
