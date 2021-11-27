package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.model.Access;
import com.usoit.api.repository.AccessRepository;
import com.usoit.api.services.AccessServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccessServicesImpl implements AccessServices{

	@Autowired
	private AccessRepository accessRepository;
	
	
	@Override
	public List<Access> getAllAccess() {
		return (List<Access>) accessRepository.findAll();
	}

	@Override
	public Access getAccessById(int id) {

		if (id > 0) {
			
			Optional<Access> optional = accessRepository.findById(id);
			
			if (optional != null) {
				return optional.get();
			}
		}
		
		return null;
	}
	
	@Override
	public long getCount() {
		return accessRepository.count();
	}
	
	@Override
	public boolean isKeyExist(String key) {
		
		if(key != null) {
			Access option = accessRepository.getAccessByPublicId(key);
			
			if(option != null) {
				option = null;
				return true;
			}
		}
		
		
		return false;
	}

}
