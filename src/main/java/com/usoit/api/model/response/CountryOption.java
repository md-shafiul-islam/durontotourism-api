package com.usoit.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryOption {
	
	private String label;
	
	private int value;
}
