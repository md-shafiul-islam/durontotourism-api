package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.Department;
import com.usoit.api.model.Designation;

public interface DesignationServices {

	public List<Designation> getAllDesignations();

	public long getCount();
	
	public Designation getDesignationById(int id);

	public boolean save(Designation designation);

	public boolean update(Designation designation);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);


	
}
