package com.usoit.api.enduser.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.enduser.services.CustomerServices;
import com.usoit.api.model.Customer;
import com.usoit.api.model.request.ChangePhone;
import com.usoit.api.model.request.ReqMailChange;
import com.usoit.api.services.HelperAuthenticationServices;

@RestController
@RequestMapping(value="/api/enu/v1/changes")
public class RestChangesController {
	
	@Autowired
	private CustomerServices customerServices;
	
	@Autowired
	private HelperAuthenticationServices helperAuthenticationServices;
	
	@PutMapping(value = "/phone", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPhoneChangeAction(@RequestBody ChangePhone changePhone) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);
		
		if(changePhone != null) {
			Customer customer = helperAuthenticationServices.getCurrentCustomer();
			
			Customer existCustomer = customerServices.getCustomerPhoneIsExist(changePhone.getPhoneNo(), changePhone.getCode());
			if(existCustomer != null) {
				map.put("message", "Provided Phone is taken. Change failed");
				map.put("status", false);
				map.put("data", null);
			}
			
			if(customer != null && existCustomer == null) {
				boolean status = customerServices.changeCustomerPhone(customer, changePhone);
				
				if(status) {
					map.put("message", "Customer Phone change done.");
					map.put("status", true);
					map.put("data", null);
				}
			}
		}

		return ResponseEntity.ok(map);
	}
	
	@PutMapping(value = "/mail", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMailChangeAction(@RequestBody ReqMailChange mailChange) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Customer Mail change failed");
		map.put("status", false);
		map.put("data", null);
		
		if(mailChange != null) {
			Customer customer = helperAuthenticationServices.getCurrentCustomer();
			
			Customer existCustomer = customerServices.getCustomerUsernameIsExist(mailChange.getEmail());
			if(existCustomer != null) {
				map.put("message", "Provided Mail is taken. Change failed");
				map.put("status", false);
				map.put("data", null);
			}
			if(customer != null && existCustomer == null) {
				boolean status = customerServices.changeCustomerMail(customer, mailChange);
				
				if(status) {
					map.put("message", "Customer Mail change done.");
					map.put("status", true);
					map.put("data", null);
				}
			}
		}

		return ResponseEntity.ok(map);
	}
	
}
