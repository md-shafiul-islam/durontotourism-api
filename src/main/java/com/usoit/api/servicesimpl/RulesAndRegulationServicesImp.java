package com.usoit.api.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.repository.RulesAndRegulationRepository;
import com.usoit.api.services.RulesAndRegulationServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RulesAndRegulationServicesImp implements RulesAndRegulationServices{

	@Autowired
	private RulesAndRegulationRepository rulesAndRegulationRepository;
	
	@Override
	public boolean isKeyExist(String key) {
		
		return false;
	}
	
}
