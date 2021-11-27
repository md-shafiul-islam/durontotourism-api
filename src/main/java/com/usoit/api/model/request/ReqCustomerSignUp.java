package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqCustomerSignUp {

	private String firstName;
	private String  lastName;
	private String  email;
	private String  phoneNo;
	private String  code;
	private String  password;
}
