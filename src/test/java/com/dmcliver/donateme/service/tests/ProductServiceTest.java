package com.dmcliver.donateme.service.tests;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.dmcliver.donateme.CommonCheckedException;
import com.dmcliver.donateme.builders.BrandBuildResult;
import com.dmcliver.donateme.builders.BrandBuilder;
import com.dmcliver.donateme.builders.ImageBuilder;
import com.dmcliver.donateme.builders.ProductBuilder;
import com.dmcliver.donateme.builders.ProductCategoryBuilder;
import com.dmcliver.donateme.datalayer.ProductCategoryDAO;
import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.domain.Image;
import com.dmcliver.donateme.domain.Product;
import com.dmcliver.donateme.domain.ProductCategory;
import com.dmcliver.donateme.models.ProductModel;
import com.dmcliver.donateme.services.ProductService;
import com.dmcliver.donateme.services.ProductServiceImpl;

import org.mockito.runners.MockitoJUnitRunner;
import org.primefaces.model.UploadedFile;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@Mock private ProductCategoryDAO prodCatDAO;
	@Mock private ProductDAO productDAO;
	@Mock private ProductBuilder productBuilder;
	@Mock private ProductCategoryBuilder productCategoryBuilder;
	@Mock private BrandBuilder brandBuilder;
	@Mock private ImageBuilder imageBuilder;

	@Test
	public void createBrand_WithExistingBrand_DoesntSaveBrand() throws CommonCheckedException {
		
		Brand brand = new Brand();
		ProductModel model = new ProductModel();
		
		when(brandBuilder.build(model)).thenReturn(new BrandBuildResult(true, brand));
		
		ProductServiceImpl service = new ProductServiceImpl(prodCatDAO, productDAO, productBuilder, productCategoryBuilder, brandBuilder, imageBuilder);
		Brand createdBrand = service.createBrand(model);
		
		verify(productDAO, never()).saveProductBrand(brand);
		assertThat(createdBrand, is(brand));
	}
	
	@Test
	public void createBand_WithNonExistingBrand_SavesBrand() throws CommonCheckedException {
		
		Brand brand = new Brand();
		ProductModel model = new ProductModel();
		
		when(brandBuilder.build(model)).thenReturn(new BrandBuildResult(false, brand));
		
		ProductServiceImpl service = new ProductServiceImpl(prodCatDAO, productDAO, productBuilder, productCategoryBuilder, brandBuilder, imageBuilder);
		Brand createdBrand = service.createBrand(model);
		
		verify(productDAO).saveProductBrand(brand);
		assertThat(createdBrand, is(brand));
	}
	
	@Test
	public void createProduct_WithBrand_CallsProductBuilderWithBrand() throws MalformedURLException, IOException,CommonCheckedException {
	
		Brand brand = new Brand();

		ProductService service = new ProductServiceImpl(prodCatDAO, productDAO, productBuilder, productCategoryBuilder, brandBuilder, imageBuilder);
		service.createProduct(brand, new ProductCategory(UUID.randomUUID(), ""), new ProductModel(), null);
		
		verify(this.productBuilder).with(brand);
	}
	
	@Test
	public void createProduct_WithImageFiles_CallsImageBuilderAndProductDAOToSaveEveryImage() throws MalformedURLException, IOException, CommonCheckedException {
	
		Brand brand = new Brand();
		List<UploadedFile> files = asList(mock(UploadedFile.class), mock(UploadedFile.class), mock(UploadedFile.class));
		when(imageBuilder.buildAll(any(Product.class), eq(files))).thenReturn(asList(mock(Image.class), mock(Image.class), mock(Image.class)));
		
		ProductService service = new ProductServiceImpl(prodCatDAO, productDAO, productBuilder, productCategoryBuilder, brandBuilder, imageBuilder);
		service.createProduct(brand, new ProductCategory(randomUUID(), ""), new ProductModel(), files);
		
		verify(this.imageBuilder).buildAll(any(Product.class), eq(files)); 
		verify(this.productDAO, times(3)).saveProductImage(any(Image.class));
	}
	
	@Test
	public void createProduct_WithEmptyImageList_DoesntCallThroughToImageBuilderAndProductDAO () throws MalformedURLException, IOException, CommonCheckedException {
		
		Brand brand = new Brand();
		List<UploadedFile> files = new ArrayList<UploadedFile>();
		
		ProductService service = new ProductServiceImpl(prodCatDAO, productDAO, productBuilder, productCategoryBuilder, brandBuilder, imageBuilder);
		service.createProduct(brand, new ProductCategory(randomUUID(), ""), new ProductModel(), files);
		
		verify(this.imageBuilder, never()).buildAll(any(Product.class), eq(files)); 
		verify(this.productDAO, never()).saveProductImage(any(Image.class));
	}
	
	@Test
	public void createProduct_WithNoImageList_DoesntCallThroughToImageBuilderAndProductDAO () throws MalformedURLException, IOException, CommonCheckedException {
		
		Brand brand = new Brand();
		List<UploadedFile> files = null;
		
		ProductService service = new ProductServiceImpl(prodCatDAO, productDAO, productBuilder, productCategoryBuilder, brandBuilder, imageBuilder);
		service.createProduct(brand, new ProductCategory(randomUUID(), ""), new ProductModel(), files);
		
		verify(this.imageBuilder, never()).buildAll(any(Product.class), eq(files)); 
		verify(this.productDAO, never()).saveProductImage(any(Image.class));
	}
}
