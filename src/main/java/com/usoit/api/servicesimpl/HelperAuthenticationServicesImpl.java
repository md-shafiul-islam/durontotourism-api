package com.usoit.api.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.usoit.api.model.User;
import com.usoit.api.services.HelperAuthenticationServices;
import com.usoit.api.services.UserServices;


@Service
public class HelperAuthenticationServicesImpl implements HelperAuthenticationServices{
	
	@Autowired
	private UserServices userServices;
	
	@Override
	public User getCurrentUser() {
		
//		System.out.println("Helper Get Current User !!");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		System.out.println("authentication.getPrincipal().toString()" + authentication.getPrincipal().toString());
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

}
