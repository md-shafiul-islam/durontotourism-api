package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.ReceiveOption;
import com.usoit.api.repository.ReceiveOptionRepository;
import com.usoit.api.services.ReceiveOptionServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReceiveOptionServicesImpl implements ReceiveOptionServices{

	@Autowired
	private ReceiveOptionRepository receiveOptionRepository;
	
	@Override
	public ReceiveOption getReceiveOptionByValue(String receiveOption) {
		
		Optional<ReceiveOption> optional = receiveOptionRepository.getReceiveOptionByValue(receiveOption);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}
	
	@Override
	public List<ReceiveOption> getAllReceiveOption() {
		
		return (List<ReceiveOption>) receiveOptionRepository.findAll();
	}
}
