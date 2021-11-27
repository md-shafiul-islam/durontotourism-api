package com.usoit.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestEsCustomer {

	private String publicId;
	
	private String fullName;
	
	private String phone;
	
	private String email;
	
	private String walletAmount;
	
	private String phoneCode;
	
}
