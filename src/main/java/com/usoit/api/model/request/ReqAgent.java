package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqAgent {

	private String applicantName;
	
	private String  code;
	
	private String  phone;
	
	private String  email;
	
	private String  companyName;
	
	private String  ownerName;
	
	private String  ownerEmail;
	
	private String  ownPhone;
	
	private String  ownCode;
	
	private String  pwd;
}
