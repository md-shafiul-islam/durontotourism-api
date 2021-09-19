package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqWWDBankDetails {

	private String accountName;

	private String bankAccountNumber;

	private String bankName;

	private String branchName;
}
