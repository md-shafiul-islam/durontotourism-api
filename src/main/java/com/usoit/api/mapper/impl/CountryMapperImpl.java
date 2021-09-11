package com.usoit.api.mapper.impl;

import org.springframework.stereotype.Service;

import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.mapper.CountryMapper;
import com.usoit.api.model.Country;

@Service
public class CountryMapperImpl implements CountryMapper {

	@Override
	public RestCountry mappCountry(Country country) {
		
		if(country != null) {
			
			RestCountry restCountry = new RestCountry();
			
			restCountry.setDialOrPhoneCode(country.getDialOrPhoneCode());
			restCountry.setId(country.getId());
			restCountry.setIso3Code(country.getIso3Code());
			restCountry.setIsoCode(country.getIsoCode());
			restCountry.setName(country.getName());
			restCountry.setNiceName(country.getNiceName());
			restCountry.setNumCode(country.getNumCode());
			
			return restCountry;
		}
		
		return null;
	}

}
