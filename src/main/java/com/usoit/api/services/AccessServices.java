package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.Access;

public interface AccessServices {

	public List<Access> getAllAccess();

	public Access getAccessById(int id);
	
	public long getCount();

}
