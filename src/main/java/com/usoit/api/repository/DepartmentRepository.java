package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer>{

	
}
