package com.dmcliver.donateme.services;

import com.dmcliver.donateme.datalayer.ProductListingDAO;
import com.dmcliver.donateme.domain.ProductListing;

public class ProductListingServiceImpl implements ProductListingService {
	
	private ProductListingDAO prodListDAO;

	@Override
	public void saveListing(ProductListing listing) {
		prodListDAO.save(listing);
	}
}