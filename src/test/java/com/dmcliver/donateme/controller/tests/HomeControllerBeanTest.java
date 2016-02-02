package com.dmcliver.donateme.controller.tests;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.Test;
import org.primefaces.model.TreeNode;

import com.dmcliver.donateme.builders.TreeNodeBuilder;
import com.dmcliver.donateme.controllers.HomeControllerBean;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.ProductCategoryAggregate;

public class HomeControllerBeanTest {

	@Test
	@SuppressWarnings("unchecked")
	public void initialize_WithTopLevelCategoryEntries_BuildsTreeNodeList(){
		
		TreeNode root = mock(TreeNode.class);
		
		ProductCategoryDAO prodCatDAO = mock(ProductCategoryDAO.class);
		when(prodCatDAO.getTopLevelInfo()).thenReturn(buildProductCategories());
		
		TreeNodeBuilder builder = mock(TreeNodeBuilder.class);
		when(builder.build()).thenReturn(root);
		
		HomeControllerBean controller = new HomeControllerBean(prodCatDAO, builder);
		controller.getCategories();
		
		verify(builder, times(2)).buildNode(anyList(), any(ProductCategoryAggregate.class));
	}

	private List<ProductCategoryAggregate> buildProductCategories() {
		
		return asList(
			
			new ProductCategoryAggregate(randomUUID(), "one", 0L),
			new ProductCategoryAggregate(randomUUID(), "two", 0L)
		);
	}
}
