package com.usoit.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResEsCustomer {
	
	private String publicId;
	
	private boolean mailVrified;
	
	private boolean phoneVrified;
	
	private String userType;
	
	private String fullName;
	
	private String firstName;
	
	private String lastName;
	
	private String phone;
	
	private String email;
	
	private String walletAmount;
	
	private String phoneCode;
}
