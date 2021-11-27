package com.usoit.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="phone_verification_token")
public class PhoneVerificationToken {
	
	@Id
	private String id;
	
	private String code;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	private Date date;

}
