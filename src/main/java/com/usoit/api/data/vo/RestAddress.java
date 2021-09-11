package com.usoit.api.data.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestAddress {

	
	private int id;
	
	private String house;
	
	private String village;
	
	private String street;
	
	private String zipCode;
	
	private String city;

	private RestCountry country;
	
	private String countryCode;
	
	
}
