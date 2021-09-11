package com.usoit.api.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.ContactPerson;
import com.usoit.api.services.ContactPersonRepository;
import com.usoit.api.services.ContactPersonServices;

@Service
public class ContactPersonServicesImpl implements ContactPersonServices{
	
	@Autowired
	private ContactPersonRepository contactPersonRepository;
	
	@Override
	public boolean isKeyExist(String key) {
		
		if(key != null) {
			ContactPerson option = contactPersonRepository.getContactPersonByPublicId(key);
			
			if(option != null) {
				option = null;
				return true;
			}
		}
		
		return false;
	}
}
