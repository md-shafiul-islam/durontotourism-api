package com.usoit.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property = "id")
@Entity
@Table(name = "contact_person")
public class ContactPerson {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "public_id")
	private String publicId;
	
	private String name;
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "vendor", referencedColumnName = "id")
	private Vendor vendor;
	
	@Column(name = "phone_no")
	private String phoneNo;
	
	@Column(name = "phone_code")
	private String conPhoneCode;
	
	@JsonIgnore
	@Transient
	private Country country;
	
	@JsonIgnore
	@Transient
	private Country country2;
	
	private String email;
	
	private String designation;
	

	
	
}
