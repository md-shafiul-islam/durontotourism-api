package com.usoit.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "temp_vendor")
public class TempVendor implements Serializable{

	private static final long serialVersionUID = 258404119110130077L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name ="public_id")
	private String publicId;
	
	@Column(name ="company_name")
	private String companyName;
	
	@Column(name ="owner_name")
	private String ownerName;
	
	@Column(name ="phone_no")
	private String comPhoneNo;
	
	@Column(name ="phone_code")
	private String phoneCode;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tempVendor")
	private List<TempContactPerson> contactPersons = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tempVendor")
	private List<TempVAddress> addresses = new ArrayList<>();
	
	@Column(name ="email")
	private String email;
	
	@Column(name ="website")
	private String website;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tempVendor")
	private List<TempPaymentInfo> paymentInfos = new ArrayList<>();
	
	@Column(name ="vendor_category")
	private int vendorCategory;	
	
	@Column(name = "valid_status")
	private int validStatus;
}
