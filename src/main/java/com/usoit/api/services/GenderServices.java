package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.Gender;

public interface GenderServices {

	public List<Gender> getAllGenders();

	public long getCount();
	
	public Gender getGenderById(int id);

}
