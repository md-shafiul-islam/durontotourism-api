package com.usoit.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "temp_ven_address")
public class TempVAddress implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="prev_id")
	private int prevId;
	
	@JsonBackReference("tempVendor_address")
	@ManyToOne
	@JoinColumn(name = "temp_vendor", referencedColumnName = "id")
	private TempVendor tempVendor;
	
	@Column(name ="house")
	private String house;
	
	@Column(name ="title")
	private String title;
	
	@Column(name ="village")
	private String village;
	
	@Column(name ="street")
	private String street;
	
	@Column(name = "zip_code")
	private String zipCode;
	
	@JoinColumn(name = "city")
	private String city;

	@Column(name ="country")
	private int country;
	
	@Column(name = "country_code")
	private String countryCode;
	
	@Transient
	private String countryName;
	
}
