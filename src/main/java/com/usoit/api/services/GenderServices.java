package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.Gender;

public interface GenderServices {

	public List<Gender> getAllGenders();

	public long getCount();
	
	public Gender getGenderById(int id);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
