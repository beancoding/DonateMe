package com.dmcliver.donateme.datalayer.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.ProductCategoryAggregate;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration("classpath:/servlet-context.xml")
public class ProductCategoryDAOTest {

	@Autowired
	private ProductCategoryDAO prodCatDAO;
	
	@Test
	@Transactional
	public void getTopLevelCount_ReturnsProperCount(){
		
		List<ProductCategoryAggregate> topLevelCount = prodCatDAO.getTopLevelInfo();
		
		ProductCategoryAggregate data = topLevelCount.get(0);
		
		assertThat(topLevelCount.size(), is(1));
		assertThat(data.getProductCategoryName(), is("GrandParent"));
		assertThat(data.getChildCount(), is(1L));
	}
}