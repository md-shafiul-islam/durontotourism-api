package com.usoit.api.controller.user;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {

	
	//(value = "/username", method = RequestMethod.GET)
	public void currentUserName(Principal principal) {
		 
		System.out.println("Current User Name via Controller:" + principal.getName());
	}
	
}
