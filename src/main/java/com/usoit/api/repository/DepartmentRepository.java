package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer>{

	
}
