package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.mapper.CountryMapper;
import com.usoit.api.model.Country;
import com.usoit.api.model.response.RespCountry;
import com.usoit.api.model.response.SelectOption;

@Service
public class CountryMapperImpl implements CountryMapper {
	
	@Override
	public RespCountry getRestCountry(Country country) {
		
		if(country != null) {
			RespCountry respCountry = new RespCountry();
			respCountry.setDialOrPhoneCode(country.getDialOrPhoneCode());
			respCountry.setId(country.getId());
			respCountry.setIso3Code(country.getIso3Code());
			respCountry.setIsoCode(country.getIsoCode());
			respCountry.setName(country.getName());
			
			return respCountry;
		}
		
		return null;
	}
	
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
			restCountry.setNumCode(country.getNumCode());;
			
			return restCountry;
		}
		
		return null;
	}
	
	@Override
	public List<SelectOption> getCountryOption(List<Country> countries) {
		
		if(countries != null) {
			List<SelectOption> options = new ArrayList<>();
			
			for (Country country : countries) {
				SelectOption option = new SelectOption();
				option.setLabel(country.getName());
				option.setValue(country.getIsoCode());
				options.add(option);
			}
			
			return options;
		}
		
		return null;
	}
}
