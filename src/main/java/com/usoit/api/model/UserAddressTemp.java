package com.usoit.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_address_temp")
public class UserAddressTemp implements Serializable{

	private static final long serialVersionUID = 3340636248629634095L;

	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	//@JsonManagedReference("user_temp_address")
	@ManyToOne
	@JoinColumn(name = "user_temp", referencedColumnName = "id")
	private UserTemp userTemp;
	
	@JsonProperty("house")
	private String house;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("village")
	private String village;
	
	@JsonProperty("street")
	private String street;
	
	@JsonProperty("zip_code")
	@Column(name = "zip_code")
	private String zipCode;
	
	@JsonProperty("city")
	@Column(name = "city")
	private String city;

	@JsonProperty("country")
	private int country;
	
	@JsonProperty("code_name")
	@Column(name = "country_code")
	private String countryCode;
}
