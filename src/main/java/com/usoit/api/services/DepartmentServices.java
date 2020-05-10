package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.Department;

public interface DepartmentServices {

	public boolean save(Department department);

	public List<Department> getAllDepartments();

	public long getCount();
	
	public Department getDepartmentById(int id);

	public boolean update(Department department);


}
