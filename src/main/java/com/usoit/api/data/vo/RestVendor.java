package com.usoit.api.data.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestVendor {
	
	@JsonProperty("publicId")
	private String publicId;
	
	@JsonProperty("vGenId")
	private String vGenId;
	
	@JsonProperty("companyName")
	private String companyName;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("website")
	private String website;
	
	private List<RestContactPerson> contactPersons;
	
	private List<RestAddress> addresses;
	

	
	
	
}
