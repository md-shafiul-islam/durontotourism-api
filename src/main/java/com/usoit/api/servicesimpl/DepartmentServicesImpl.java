package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Department;
import com.usoit.api.repository.DepartmentRepository;
import com.usoit.api.services.DepartmentServices;

@Service
public class DepartmentServicesImpl implements DepartmentServices{

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public boolean isKeyExist(String key) {
		
		return false;
	}
	
	@Override
	public boolean save(Department department) {
		
		if (0 >= department.getId()) {
			
			departmentRepository.save(department);
			
			if (department.getId() > 0 ) {
				return true;
			}
		}
		
		
		return false;
	}
	
	@Override
	public List<Department> getAllDepartments() {
		return (List<Department>) departmentRepository.findAll();
	}
	
	@Override
	public long getCount() {
		return departmentRepository.count();
	}
	
	@Override
	public Department getDepartmentById(int id) {
		
		if (id > 0) {
			
			Optional<Department> optional = departmentRepository.findById(id);
			
			if (optional != null) {
				return optional.get();
			}
		}
		return null;
	}
	
	@Override
	public boolean update(Department department) {

		if (department.getId() > 0) {
			departmentRepository.save(department);
			return true;
		}
		
		return false;
	}
}
