package com.usoit.api.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationAPI {

	private int id;
	
	private RestCountry country;
	
	
	private RestPackage packages;
	
	
}
