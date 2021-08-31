package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.VendorCategory;

public interface VendorCategoryServices {

	public VendorCategory getVendorCatById(int vendorCategory);
	
	public List<VendorCategory> getAllVendorCats();
	
	public long getCount();
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
