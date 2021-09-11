package com.usoit.api.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestBankAccountType {
	
	private int id;
	
	private String name;
	
	private String value;
	
	private String numValue;
	
	private List<RestBankAccount> bankAccounts; 

}
