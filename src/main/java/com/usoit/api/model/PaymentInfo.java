package com.usoit.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "payment_inf")
public class PaymentInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "public_id")
	private String publicId;
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "vendor", referencedColumnName = "id")
	private Vendor vendor;
	
	@Column(name = "account_no")
	private String accountNo;
	
	@Column(name = "account_name")
	private String accountName;
	
	@Column(name = "branch_name")
	private String branchName;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "country", referencedColumnName = "id")
	private Country country;
	
	@JoinColumn(name = "city")
	private String city;
	
	private Date date;
	
	
	
	
}
