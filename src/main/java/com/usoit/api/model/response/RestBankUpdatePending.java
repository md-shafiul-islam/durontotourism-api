package com.usoit.api.model.response;

import com.usoit.api.data.vo.RestCountry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBankUpdatePending {

	private String publicId;

	private RestEsUser user;

	private RestEsUser approveUser;

	private RestEsUser updateUser;

	private String accountName;

	private String accountNumber;

	private String bankName;

	private String branchName;

	private RestCountry country;

	private RestBankAccountType bankAccountType;

	private String amount;

	private boolean active;

	private RestUpdateInf restUpdateInf;
}
