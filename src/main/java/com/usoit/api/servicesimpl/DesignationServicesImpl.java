package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.model.Department;
import com.usoit.api.model.Designation;
import com.usoit.api.repository.DesignationRepository;
import com.usoit.api.services.DesignationServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DesignationServicesImpl implements DesignationServices{

	@Autowired
	private DesignationRepository designationRepository;
	
	@Override
	public boolean isKeyExist(String key) {
		
		return false;
	}
	
	@Override
	public boolean save(Designation designation) {
		
		if (designation != null) {
			
			designationRepository.save(designation);
			
			if (designation.getId() > 0) {
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean update(Designation designation) {
		
		if (designation.getId() > 0) {
			
			designationRepository.save(designation);
			log.info("Update Success");
			return true;
		}
		
		return false;
	}
	
	@Override
	public List<Designation> getAllDesignations() {
		return (List<Designation>) designationRepository.findAll();
	}
	
	@Override
	public long getCount() {
		return designationRepository.count();
	}
	
	@Override
	public Designation getDesignationById(int id) {
		
		if (id > 0) {
			Optional<Designation> optional = designationRepository.findById(id);
			
			if (optional != null) {
				return optional.get();
			}
		}
		
		return null;
	}
	
	
}
