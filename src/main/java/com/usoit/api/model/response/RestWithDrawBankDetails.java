package com.usoit.api.model.response;

import java.util.Date;

import com.usoit.api.model.Customer;
import com.usoit.api.model.WalletWithDraw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestWithDrawBankDetails {

	private int id;

	private Customer customer;

	private WalletWithDraw walletWithDarw;

	private String accountName;

	private String bankAccountNumber;

	private String bankName;

	private String branchName;

	private Date date;
}
