package com.usoit.api.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property = "id")
@Entity
@Table(name = "user_address")
public class UserAddress {

	
	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id")
	private User user;
	
	@Column(name = "title")
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

	@JsonManagedReference
	@JsonProperty("country")
	@ManyToOne
	@JoinColumn(name = "country", referencedColumnName = "id")
	private Country country;
	
	@JsonProperty("code_name")
	@Column(name = "country_code")
	private String countryCode;

	
	
}
