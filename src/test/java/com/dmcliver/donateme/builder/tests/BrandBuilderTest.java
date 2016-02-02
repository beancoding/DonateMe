package com.dmcliver.donateme.builder.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.dmcliver.donateme.builders.BrandBuildResult;
import com.dmcliver.donateme.builders.BrandBuilder;
import com.dmcliver.donateme.builders.BrandBuilderImpl;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.models.ProductModel;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrandBuilderTest {

	@Mock private ProductDAO productDAO;

	@Test
	public void build_WithNoMatchingBrandName_ReturnsResultWithExistingParameterAsFalse() {
		
		when(productDAO.getProductBrand(anyString())).thenReturn(null);
		
		BrandBuilder builder = new BrandBuilderImpl(productDAO);
		BrandBuildResult result = builder.build(new ProductModel());
		
		assertThat(result.isExisting(), is(false));
		assertThat(result.getBrand(), notNullValue());
	}
	
	@Test
	public void build_WithMatchingBrandName_ReturnsResultWithExistingParameterAsTrue() {
		
		Brand brand = new Brand();
		
		when(productDAO.getProductBrand(anyString())).thenReturn(brand);
		
		BrandBuilder builder = new BrandBuilderImpl(productDAO);
		BrandBuildResult result = builder.build(new ProductModel());
		
		assertThat(result.isExisting(), is(true));
		assertThat(result.getBrand(), is(brand));
	}
}
