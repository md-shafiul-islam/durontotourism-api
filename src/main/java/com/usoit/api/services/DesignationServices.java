package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.Department;
import com.usoit.api.data.model.Designation;

public interface DesignationServices {

	public List<Designation> getAllDesignations();

	public long getCount();
	
	public Designation getDesignationById(int id);

	public boolean save(Designation designation);

	public boolean update(Designation designation);


	
}
