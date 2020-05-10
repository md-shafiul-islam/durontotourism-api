package com.usoit.api.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property = "id")
@Entity
@Table(name = "country")
public class Country implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8706365553053341030L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	@JsonBackReference("cnt_itarnaries")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY)
	private List<Itarnary> itarnaries = new ArrayList<>();
	
	@JsonBackReference("addresses")
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<Address> addresses = new ArrayList<>();
	
	@JsonBackReference("paymentInfs")
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<PaymentInfo> paymentInfs = new ArrayList<>();
	
	@JsonBackReference("countries")
	@ManyToMany(mappedBy = "countries")
	private Set<Packages> packages = new HashSet<Packages>();
	
	@JsonProperty("isoCode")
	@Column(name = "iso")
	private String isoCode;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("niceName")
	@Column(name = "nice_name")
	private String niceName;
	
	@JsonProperty("iso3Code")
	@Column(name = "iso3")
	private String iso3Code;
	
	@JsonProperty("numCode")
	@Column(name = "num_code")
	private int numCode;
	
	@JsonProperty("dialOrPhoneCode")
	@Column(name = "phone_code")
	private String dialOrPhoneCode;

	
	
}
