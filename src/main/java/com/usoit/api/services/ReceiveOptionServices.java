package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.ReceiveOption;

public interface ReceiveOptionServices {

	public ReceiveOption getReceiveOptionByValue(String receiveOption);
	
	public List<ReceiveOption> getAllReceiveOption();

}
