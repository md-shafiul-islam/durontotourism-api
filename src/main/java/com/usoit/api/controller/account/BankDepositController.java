package com.usoit.api.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.mapper.BankDepositMapper;
import com.usoit.api.services.BankDepositServices;

@RestController
@RequestMapping(value = "/api/bankdeposit")
public class BankDepositController {

	@Autowired
	private BankDepositMapper bankDepositMapper;
	
	@Autowired
	private BankDepositServices bankDepositServices;
	
	
}
