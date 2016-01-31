package com.dmcliver.donateme.builders;

import com.dmcliver.donateme.models.ProductModel;

public interface BrandBuilder {

	BrandBuildResult build(ProductModel model);

}