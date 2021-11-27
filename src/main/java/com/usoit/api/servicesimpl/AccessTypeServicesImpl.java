package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.model.Access;
import com.usoit.api.model.AccessType;
import com.usoit.api.repository.AccessTypeRepository;
import com.usoit.api.services.AccessTypeServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccessTypeServicesImpl implements AccessTypeServices{

	@Autowired
	private AccessTypeRepository accessTypeRepository;
	
	@Override
	public boolean isKeyExist(String key) {
		if(key != null) {
			AccessType option = accessTypeRepository.getAccessTypeByPublicId(key);
			
			if(option != null) {
				option = null;
				return true;
			}
		}
		
		
		return false;
	}
	
	@Override
	public List<AccessType> getAllAccessType() {
		
		return (List<AccessType>) accessTypeRepository.findAll();
	}

	@Override
	public long getCount() {
		return accessTypeRepository.count();
	}

}
