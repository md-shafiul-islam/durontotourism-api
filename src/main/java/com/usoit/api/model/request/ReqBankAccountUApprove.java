package com.usoit.api.model.request;

import com.usoit.api.model.response.RestUpdateInf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqBankAccountUApprove {
	
	private String publicId;
	private String accountName;
	private String accountNumber;
	private String bankName;
	private String branchName;
	private String country;
	private String bankAccountType;
	private String amount;
	
	private RestUpdateInf restUpdateInf;
}
