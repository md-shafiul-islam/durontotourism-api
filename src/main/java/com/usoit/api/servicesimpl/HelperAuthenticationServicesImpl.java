package com.usoit.api.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.enduser.services.CustomerServices;
import com.usoit.api.model.Customer;
import com.usoit.api.model.User;
import com.usoit.api.services.HelperAuthenticationServices;
import com.usoit.api.services.UserServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelperAuthenticationServicesImpl implements HelperAuthenticationServices{
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private CustomerServices customerServices; 
	
	@Override
	public User getCurrentUser() {
		
//		log.info("Helper Get Current User !!");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		log.info("authentication.getPrincipal().toString()" + authentication.getPrincipal().toString());
		if(authentication != null) {
						
			if(authentication.getName() != null) {
				
				if(authentication != null && authentication.getCredentials() != null) {
					
					return userServices.getUserByUserNameAndPass(authentication.getName(), authentication.getCredentials().toString());
				}
				
				return userServices.getUserByUsername(authentication.getName());
			}
			
			if(authentication.getPrincipal() != null) {
				return userServices.getUserByUsername(authentication.getPrincipal().toString());
			}
			
		}
		
		return null;
	}
	
	@Override
	public Customer getCurrentCustomer() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication != null) {
			log.info("Current Customer "+ authentication.getName());
			log.info("Current Customer "+ authentication.getPrincipal());
			if(authentication.getName() != null) {
				
				return customerServices.getCustomerByAuthUsername(authentication.getName());
			}
		}
		return null;
		
	}

}
