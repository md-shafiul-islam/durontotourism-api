package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Address;
import com.usoit.api.repository.AddressRepository;
import com.usoit.api.services.AddressServices;

@Service
public class AddressServicesImpl implements AddressServices{

	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public boolean isKeyExist(String key) {
	
		return false;
	}
	
	@Override
	public boolean save(Address address) {
		
		if (0 >= address.getId()) {
			
			addressRepository.save(address);
			
			if (address.getId() > 0) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Address getAddressById(int id) {
		
		if (id > 0) {
			
			Optional<Address> optional = addressRepository.findById(id);
			
			if (optional != null) {
				
				return optional.get();
			}
		}
		
		return null;
	}

	@Override
	public boolean update(Address address) {
		
		if (address.getId() > 0) {
			
			addressRepository.save(address);
			
			return true;
		}
		
		return false;
	}

	@Override
	public List<Address> getAllAddress() {
		
		return (List<Address>) addressRepository.findAll();
	}

	@Override
	public long getCount() {
		return addressRepository.count();
	}

}
