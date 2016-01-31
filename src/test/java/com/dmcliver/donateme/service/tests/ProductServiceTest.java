package com.dmcliver.donateme.service.tests;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import com.dmcliver.donateme.builders.BrandBuildResult;
import com.dmcliver.donateme.builders.BrandBuilder;
import com.dmcliver.donateme.builders.ImageBuilder;
import com.dmcliver.donateme.builders.ProductBuilder;
import com.dmcliver.donateme.builders.ProductCategoryBuilder;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.services.ProductServiceImpl;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@Mock private ProductCategoryDAO prodCatDAO;
	@Mock private ProductDAO productDAO;
	@Mock private ProductBuilder productBuilder;
	@Mock private ProductCategoryBuilder productCategoryBuilder;
	@Mock private BrandBuilder brandBuilder;
	@Mock private ImageBuilder imageBuilder;

	@Test
	public void createBrand_WithExistingBrand_DoesntSaveBrand() {
		
		Brand brand = new Brand();
		ProductModel model = new ProductModel();
		
		when(brandBuilder.build(model)).thenReturn(new BrandBuildResult(true, brand));
		
		ProductServiceImpl service = new ProductServiceImpl(prodCatDAO, productDAO, productBuilder, productCategoryBuilder, brandBuilder, imageBuilder);
		service.createBrand(model);
		
		verify(productDAO, never()).saveProductBrand(brand);
		verify(productBuilder).with(brand);
	}
	
	@Test
	public void createBand_WithNonExistingBrand_SavesBrand() {
		
		Brand brand = new Brand();
		ProductModel model = new ProductModel();
		
		when(brandBuilder.build(model)).thenReturn(new BrandBuildResult(false, brand));
		
		ProductServiceImpl service = new ProductServiceImpl(prodCatDAO, productDAO, productBuilder, productCategoryBuilder, brandBuilder, imageBuilder);
		service.createBrand(model);
		
		verify(productDAO).saveProductBrand(brand);
		verify(productBuilder).with(brand);
	}
}
