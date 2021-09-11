package com.usoit.api.model.response;

import com.usoit.api.data.vo.RestCountry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestEnBankAccount {

	private String id;
	
	private String accountName;
	
	private String accountNumber;
	
	private String bankName;
	
	private String branchName;	
	
	private RestCountry country;	
	
	private RestBankAccountType bankAccountType;
	
	
}
