package com.usoit.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidLoginResponse {

	private String username;
	
	private String password;
	
	public InvalidLoginResponse() {

		username = "User not valid";
		password = "Password invalid";
		
	}
}
