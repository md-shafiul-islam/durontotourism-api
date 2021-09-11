package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.Address;

public interface AddressServices {

	public boolean save(Address address);

	public Address getAddressById(int id);

	public boolean update(Address address);

	public List<Address> getAllAddress();

	public long getCount();
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);
	
}
