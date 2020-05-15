package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.VendorCategory;

public interface VendorCategoryServices {

	public VendorCategory getVendorCatById(int vendorCategory);
	
	public List<VendorCategory> getAllVendorCats();
	
	public long getCount();
	

}
