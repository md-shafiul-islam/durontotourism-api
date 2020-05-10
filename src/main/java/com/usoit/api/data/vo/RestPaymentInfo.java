package com.usoit.api.data.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPaymentInfo {

	private int id;
	
	private RestVendor vendor;
	
	private String accountNo;
	
	private String accountName;
	
	private String branchName;
	
	private String bankName;
	
	private RestCountry country;
	
	private String city;
	
	private Date date;
	

	
	
}
