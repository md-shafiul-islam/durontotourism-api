package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.MaritalStatus;

public interface MaritalStatusServices {

	public List<MaritalStatus> getAllMaritalStatus();

	public long getCount();
	
	public MaritalStatus getMaritalStatusById(int id);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
