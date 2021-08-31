package com.usoit.api.controller.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.mapper.BankMapper;
import com.usoit.api.model.BankAccount;
import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.request.ReqBankAccount;
import com.usoit.api.model.response.RestBankAccount;
import com.usoit.api.model.response.RestBankAccountType;
import com.usoit.api.services.BankServices;

@RestController
@RequestMapping("/api/banks")
public class BankController {

	@Autowired
	private BankServices bankServices;

	@Autowired
	private BankMapper bankMapper;
	
	@GetMapping
	private ResponseEntity<?> getBankAccounts() {

		return ResponseEntity.ok(bankServices.getBankAccounts());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getBankAccount(@PathVariable("id") String id) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);
		
		if(id != null) {
			
			if(!id.isEmpty()) {
				BankAccount account = bankServices.getBankAccount(id);
				
				RestBankAccount restBankAccount = bankMapper.mappBankAccount(account);
				
				if(restBankAccount != null) {
					
					map.put("message", "Account found");
					map.put("status", true);
					map.put("data", restBankAccount);
				}
				
			}
		}
		

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/types/{id}")
	public ResponseEntity<?> getAccountTypes(@PathVariable("id") String id) {

		Map<String, Object> map = new HashMap<>();
		
		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);
		
		if(id != null) {
			if(!id.isEmpty()) {
				
				BankAccountType accountType = bankServices.getBankAccountType(id);
				
				if(accountType != null) {
					
					RestBankAccountType restBankType = bankMapper.mappBankAccountType(accountType);
					
					if(restBankType != null)  {
						map.put("message", "Account Type found");
						map.put("status", true);
						map.put("data", restBankType);
					}
				}
			}
		}

		return ResponseEntity.ok(map);
	}
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddBankAction(@RequestBody ReqBankAccount ReqBankAccount) {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);
		
		return ResponseEntity.ok(map);
	}
	
}
