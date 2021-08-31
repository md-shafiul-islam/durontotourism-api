package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Department;
import com.usoit.api.model.Gender;
import com.usoit.api.repository.GenderRepository;
import com.usoit.api.services.GenderServices;

@Service
public class GenderServicesImpl implements GenderServices {

	@Autowired
	private GenderRepository genderRepository;

	@Override
	public boolean isKeyExist(String key) {
		
		return false;
	}

	@Override
	public List<Gender> getAllGenders() {
		return (List<Gender>) genderRepository.findAll();
	}

	@Override
	public long getCount() {
		return genderRepository.count();
	}

	@Override
	public Gender getGenderById(int id) {

		if (id > 0) {
			Optional<Gender> optional = genderRepository.findById(id);

			if (optional != null) {
				return optional.get();
			}
		}

		return null;
	}

}
