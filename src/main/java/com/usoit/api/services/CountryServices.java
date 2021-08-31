package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.Country;

public interface CountryServices {

	public List<Country> getAllCountries();

	public long getCount();

	public boolean update(Country country);

	public Country getCountryById(int id);

	public boolean save(Country country);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
