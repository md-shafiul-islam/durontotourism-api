package com.usoit.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountTypeOption {

	private String label;
	
	private int value;
}
