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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "temp_contact_person")
public class TempContactPerson implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="prev_id")
	private int prevId;
	
	@JsonBackReference("tempVendor")
	@ManyToOne
	@JoinColumn(name = "temp_vendor", referencedColumnName = "id")
	private TempVendor tempVendor;
	
	@Column(name ="name")
	private String name;
	
	@Column(name ="phone_no")
	private String phoneNo;
	
	@Column(name ="phone_code")
	private String conPhoneCode;
	
	@Column(name ="country")
	private int country;
		
	@Column(name ="email")
	private String email;
	
	@Column(name = "designation")
	private String designation;
}
