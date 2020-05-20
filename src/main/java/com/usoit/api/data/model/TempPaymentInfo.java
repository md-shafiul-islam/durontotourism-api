package com.usoit.api.data.model;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="temp_pay_inf")
public class TempPaymentInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="prev_id")
	private int prevId;
	
	@JsonBackReference("tempVendor_pay_inf")
	@ManyToOne
	@JoinColumn(name = "temp_vendor", referencedColumnName = "id")
	private TempVendor tempVendor;
	
	@Column(name = "account_no")
	private String accountNo;
	
	@Column(name = "account_name")
	private String accountName;
	
	@Column(name = "branch_name")
	private String branchName;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "country")
	private int country;
	
	@Column(name = "city")
	private String city;
	
	@Transient
	private String countryName;
}
