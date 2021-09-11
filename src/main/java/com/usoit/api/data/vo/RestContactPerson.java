package com.usoit.api.data.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestContactPerson {

	
	private String publicId;
	
	private String name;
	
	private String phoneNo;
	
	private String conPhoneCode;
	
	private String email;
	
	private String designation;
	
	
	
	
}
