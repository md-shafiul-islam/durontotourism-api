package com.usoit.api.servicesimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.ContactPerson;
import com.usoit.api.model.Country;
import com.usoit.api.model.response.CountryOption;
import com.usoit.api.repository.CountryRepository;
import com.usoit.api.services.CountryServices;

@Service
public class CountryServicesImpl implements CountryServices{

	@Autowired
	private CountryRepository countryRepository;
	
	@Override
	public boolean isKeyExist(String key) {
		return false;
	}
	
	@Override
	public List<Country> getAllCountries() {
		return (List<Country>) countryRepository.findAll();
	}

	@Override
	public long getCount() {
		return countryRepository.count();
	}
	
	@Override
	public Country getCountryById(int id) {
		
		if (id > 0) {
			
			Optional<Country> optional = countryRepository.findById(id);
			
			if (optional != null) {
				
				return optional.get();
			}
		}
		
		return null;
	}
	
	@Override
	public boolean save(Country country) {
		
		if (0 >= country.getId()) {
			
			countryRepository.save(country);
			
			if (country.getId() > 0) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean update(Country country) {
		
		if (country.getId() > 0) {
			
			countryRepository.save(country);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public List<CountryOption> getCountryOptions() {
		List<Country> countries = (List<Country>) countryRepository.findAll();
		
		if(countries != null) {
			List<CountryOption> countryOptions = new ArrayList<CountryOption>();
			
			for (int i = 0; i < countries.size(); i++) {
				
				if(countries.get(i) != null) {
					CountryOption countryOption = new CountryOption();
					
					countryOption.setValue(countries.get(i).getId());
					countryOption.setLabel(countries.get(i).getName());
					
					countryOptions.add(countryOption);
				}
			}
			
			return countryOptions;
		}
		
		return null;
	}

}
