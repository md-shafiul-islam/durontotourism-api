package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.MaritalStatus;

public interface MaritalStatusServices {

	public List<MaritalStatus> getAllMaritalStatus();

	public long getCount();
	
	public MaritalStatus getMaritalStatusById(int id);

}
