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
	public List<ProductCategory> getTopLevelCategories() {
		
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
	public List<ProductCategoryAggregate> getTopLevelInfo() {
		
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> result = buildQuery(session).add(isNull("pc2.parentProductCategory")).list();
		return map(result);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ProductCategoryAggregate> getChildCategories(UUID parentId) {
		
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> result = buildQuery(session).createAlias("pc2.parentProductCategory", "gpc")
												   .add(eq("gpc.productCategoryId", parentId))
												   .list();
		return map(result);
	}
	
	/**
	 * <p>
	 * <h3>HQL:</h3>
	 * SELECT pc.productCategoryId, pc.productCategoryName, COUNT(productCategoryId) <br/>
	 * FROM ProductCategory <br/>
	 * RIGHT OUTER JOIN ProductCategory.parentProductCagory pc <br/>
	 * GROUP BY pc.productCategoryId, pc.productCategoryName </p>
	 * <p>
	 * <h3>SQL:</h3>
	 * Is exactly the same except for the right outer join which is shown below: <br/>
	 * RIGHT OUTER JOIN ProductCategory pc on pc.ProductCategoryId = ProductCategory.parentProductCategoryId </p>
	 */
	private static Criteria buildQuery(Session session) {

	   return session.createCriteria(ProductCategory.class, "pc1")
				     .createAlias("parentProductCategory", "pc2", RIGHT_OUTER_JOIN)
				     .setProjection(
				    		 
			    		 projectionList().add(groupProperty("pc2.productCategoryId"), "ProdCatId")
					  				   	 .add(groupProperty("pc2.productCategoryName"), "ProdCatName")
					  				   	 .add(count("productCategoryId"), "ProdCatCount")
		    		 );
	}
	
	private List<ProductCategoryAggregate> map(List<Object[]> list) {
		
		return list.stream()
				   .map(res -> new ProductCategoryAggregate((UUID)res[0], (String)res[1], (Long)res[2]))
				   .collect(toList());
	}

	@Override
	@Transactional
	public ProductCategory getById(UUID prodCatId) {
		
		Session session = sessionFactory.getCurrentSession();
		return (ProductCategory)session.get(ProductCategory.class, prodCatId);
	}

	@Override
	@Transactional
	public void save(ProductCategory productCategory) {
		
		Session session = sessionFactory.getCurrentSession();
		session.save(productCategory);
	}
}
