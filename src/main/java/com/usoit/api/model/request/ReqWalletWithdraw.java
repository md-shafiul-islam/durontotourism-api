package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqWalletWithdraw {

	private ReqWWDBankDetails reqWWDBankDetails;
	
	private ReqShipingAddress reqShipingAddress;
	
	private ReqMobileAccountDetails reqMobileAccountDetails;
	
	private String amount;
	
	private String chequeName;	

	private String withdrawType;

	private String receiveOption;
}
