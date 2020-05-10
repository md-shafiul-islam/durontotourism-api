package com.usoit.api.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property = "id")
@Entity
@Table(name = "vendor")
public class Vendor {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "vendor_gen")
	private String vGenId;
	
	@Column(name = "public_id")
	private String publicId;
	
	@Column(name = "company_name")
	private String companyName;
	
	//@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendor")
	private List<ContactPerson> contactPersons = new ArrayList<>();
	
	//@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendor")
	private List<Address> addresses = new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendor")
	private List<Itarnary> itarnaries = new ArrayList<>();
	
	private String email;
	
	private String website;
	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendor")
	private List<PaymentInfo> paymentInfos = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "update_user", referencedColumnName = "id")
	private User updateUser;
	
	@Column(name = "approve")
	private int approveStatus;
	
	@Column(name = "update_approve")
	private int updateApprove;
	
	private Date date;
	
	@Column(name = "date_group")
	private Date dateGroup;
	
	
	
}
