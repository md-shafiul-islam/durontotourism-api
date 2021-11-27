package com.usoit.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="traveler")
public class Traveler {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="public_id")
	private String publicId;
	
	@OneToMany(mappedBy ="traveler")
	private List<Passport> passports = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="customer", referencedColumnName = "id")
	private Customer customer;
	
	@Column(name="nationality")
	private String nationality;
	
	@Column(name="first_name")
	private String firstName;
	
	private String lastName;
	
	@Column(name="phone_code")
	private String phoneCode;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="is_primary")
	private boolean primary;
	
	@Column(name="date_of_birth")
	private Date dateOfBirth;	
	
	private Date date;
}
