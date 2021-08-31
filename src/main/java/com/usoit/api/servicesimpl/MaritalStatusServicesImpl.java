package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Department;
import com.usoit.api.model.MaritalStatus;
import com.usoit.api.repository.MaritalStatusRepository;
import com.usoit.api.services.MaritalStatusServices;

@Service
public class MaritalStatusServicesImpl implements MaritalStatusServices{

	@Autowired
	private MaritalStatusRepository maritalStatusRepository;
	
	@Override
	public boolean isKeyExist(String key) {
		
		return false;
	}
	
	@Override
	public List<MaritalStatus> getAllMaritalStatus() {
		return (List<MaritalStatus>) maritalStatusRepository.findAll();
	}

	@Override
	public long getCount() {
		return maritalStatusRepository.count();
	}

	@Override
	public MaritalStatus getMaritalStatusById(int id) {
		
		if (id > 0) {
			
			Optional<MaritalStatus> optional = maritalStatusRepository.findById(id);
			
			if (optional != null) {
				
				return optional.get();
			}
		}
		
		return null;
	}

}
