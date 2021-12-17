package com.usoit.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "temp_phone")
public class TempPhone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "prev_code")
	private String prevCode;

	@Column(name = "prev_phone")
	private String prevPhoneNo;

	private String code;

	private String phone;

	@Column(name = "ref_id")
	private int refId;
	
	private boolean active;

	private Date date;

}
