package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqQueryBankAccount {

	private String bankname;
	
	private String branchName;
	
	private String accountName;
}
