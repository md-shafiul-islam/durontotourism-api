package com.usoit.api.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.data.model.AccessType;
import com.usoit.api.repository.AccessTypeRepository;
import com.usoit.api.services.AccessTypeServices;

@Service
public class AccessTypeServicesImpl implements AccessTypeServices{

	@Autowired
	private AccessTypeRepository accessTypeRepository;
	
	@Override
	public List<AccessType> getAllAccessType() {
		
		return (List<AccessType>) accessTypeRepository.findAll();
	}

	@Override
	public long getCount() {
		return accessTypeRepository.count();
	}

}
