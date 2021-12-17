package com.usoit.api.enduser.controller.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.mapper.BankDepositMapper;
import com.usoit.api.services.BankDepositServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/enu/v1/bankdeposit")
public class RestEuBankDepositController {

	@Autowired
	private BankDepositMapper bankDepositMapper;
	
	@Autowired
	private BankDepositServices bankDepositServices;
	
	
}
