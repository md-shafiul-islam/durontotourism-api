package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqBankAccountUpdateInf {

	private String bankId;
	
	private String fieldName;
	
	private String value;
	
	
	
}
