package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.AccessType;

public interface AccessTypeServices {

	public List<AccessType> getAllAccessType();

	public long getCount();
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
