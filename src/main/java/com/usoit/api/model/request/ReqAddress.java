package com.usoit.api.model.request;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property = "id")
public class ReqAddress implements Serializable{

	
	private static final long serialVersionUID = -5529518142993304387L;

	@JsonProperty("id")
	private int id;
	
	@JsonProperty("house")
	private String house;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("village")
	private String village;
	
	@JsonProperty("street")
	private String street;
	
	@JsonProperty("zipCode")
	@Column(name = "zip_code")
	private String zipCode;
	
	@JsonProperty("city")
	@JoinColumn(name = "city")
	private String city;

	@JsonProperty("country")
	private int country;
	
	@JsonProperty("code_name")
	@Column(name = "country_code")
	private String countryCode;
	
	private String countryName;
	
	
	
}
