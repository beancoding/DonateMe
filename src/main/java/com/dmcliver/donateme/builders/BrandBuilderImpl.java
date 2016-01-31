package com.dmcliver.donateme.builders;

import static java.util.UUID.randomUUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.datalayer.ProductDAO;
import com.dmcliver.donateme.domain.Brand;
import com.dmcliver.donateme.models.ProductModel;

@Component
public class BrandBuilderImpl implements BrandBuilder {

	private ProductDAO productDAO;

	@Autowired
	public BrandBuilderImpl(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public BrandBuildResult build(ProductModel model) {
		
		Brand brand = productDAO.getProductBrand(model.getBrand());
		
		if(brand != null)
			return new BrandBuildResult(true, brand);
		
		brand = new Brand();
		brand.setBrandId(randomUUID());
		brand.setBrandName(model.getBrand());
		return new BrandBuildResult(false, brand); 
	}
}