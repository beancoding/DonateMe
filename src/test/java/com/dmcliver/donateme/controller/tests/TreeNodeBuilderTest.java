package com.dmcliver.donateme.controller.tests;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.mockito.Mockito;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.dmcliver.donateme.controllers.TreeNodeBuilderImpl;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.domain.ProductCategoryAggregate;
import com.dmcliver.donateme.models.TreeModel;

public class TreeNodeBuilderTest {

	@Test
	public void buildChildren_WithDatabaseDataThatContainsChildren_AddsDummyChildToTreeNode(){
		
		UUID parentId = randomUUID();
		ArrayList<TreeNode> children = new ArrayList<TreeNode>();
		
		ProductCategoryDAO prodCatDAO = mock(ProductCategoryDAO.class);
		Mockito.when(prodCatDAO.getChildCategories(parentId)).thenReturn(asList(new ProductCategoryAggregate(new Object[]{null, "One", 1L})));
		
		TreeNodeBuilderImpl builder = new TreeNodeBuilderImpl(prodCatDAO);
		builder.buildChildren(children, new TreeModel("ProdCat1", parentId));

		assertThat(children.size(), is(1));
		assertThat(children.get(0).getChildCount(), is(1));
	}
	
	@Test
	public void buildChildren_WithNoDatabaseDataContainingChildren_DoesntAddDummyChildToTreeNode(){
		
		UUID parentId = randomUUID();
		ArrayList<TreeNode> children = new ArrayList<TreeNode>();
		
		ProductCategoryDAO prodCatDAO = mock(ProductCategoryDAO.class);
		Mockito.when(prodCatDAO.getChildCategories(parentId)).thenReturn(asList(new ProductCategoryAggregate(new Object[]{null, "One", 0L})));
		
		TreeNodeBuilderImpl builder = new TreeNodeBuilderImpl(prodCatDAO);
		builder.buildChildren(children, new TreeModel("ProdCat1", parentId));

		assertThat(children.size(), is(1));
		assertThat(children.get(0).getChildCount(), is(0));
	}
	
	@Test
	public void buildChildren_WithExistingDummyChild_RemovesDummyChildInTreeNode(){
		
		UUID parentId = randomUUID();
		List<TreeNode> children = new ArrayList<TreeNode>();
		children.add(new DefaultTreeNode(-1));
		
		ProductCategoryDAO prodCatDAO = mock(ProductCategoryDAO.class);
		Mockito.when(prodCatDAO.getChildCategories(parentId)).thenReturn(asList(new ProductCategoryAggregate(new Object[]{null, "One", 0L})));
		
		TreeNodeBuilderImpl builder = new TreeNodeBuilderImpl(prodCatDAO);
		builder.buildChildren(children, new TreeModel("ProdCat1", parentId));

		assertThat(children.size(), is(1));
		assertThat(children.get(0).getChildCount(), is(0));
	}
}
