package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqBankAccount {
	
	private String accountName;
	
	private String accountNumber;
	
	private String bankName;
	
	private String branchName;	
	
	private int country;	
	
	private int bankAccountType;
	
	private String initialAmount;
	
	private String logoUrl;

}
