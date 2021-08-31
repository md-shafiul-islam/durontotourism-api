package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.PackageCat;

public interface PackageCatServices {

	public List<PackageCat> getAllPackCats();

	public long getCount();

	public boolean save(PackageCat packageCat);

	public PackageCat getPackCatById(int id);

	public boolean update(PackageCat packageCat);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
