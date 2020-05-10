package com.usoit.api.model.request;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecAddress implements Serializable{

	
	
	private static final long serialVersionUID = 8560806066429727368L;

	private int id;
	
	private String house;
	
	private String village;
	
	private String street;
	
	private String zipCode;
	
	private String city;

	private ReqCountry country;
	
	private String countryCode;
	
	
}
