package com.usoit.api.data.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.usoit.api.model.Country;
import com.usoit.api.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestUserAddress implements Serializable{

	
	private static final long serialVersionUID = 1L;


	@JsonProperty("id")
	private int id;
	
	
	private String title;
	
	@JsonProperty("house")
	private String house;
	
	@JsonProperty("village")
	private String village;
	
	@JsonProperty("street")
	private String street;
	
	@JsonProperty("zip_code")
	@Column(name = "zip_code")
	private String zipCode;
	
	@JsonProperty("city")
	@JoinColumn(name = "city")
	private String city;

	@JsonProperty("country")
	private RestCountry country;
	
	@JsonProperty("code_name")
	private String countryCode;

}
