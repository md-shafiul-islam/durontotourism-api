package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.Access;

public interface AccessServices {

	public List<Access> getAllAccess();

	public Access getAccessById(int id);
	
	public long getCount();
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
