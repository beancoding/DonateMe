package com.dmcliver.donateme.builders;

import com.dmcliver.donateme.domain.Brand;

public class BrandBuildResult {
	
	private boolean isExisting;
	private Brand brand;

	public BrandBuildResult(boolean isExisting, Brand brand) {

		this.isExisting = isExisting;
		this.brand = brand;
	}

	public boolean isExisting() {
		return isExisting;
	}

	public Brand getBrand() {
		return brand;
	}
}