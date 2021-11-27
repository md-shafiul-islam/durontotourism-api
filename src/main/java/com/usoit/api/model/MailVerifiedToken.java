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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="mail_verified_token")
public class MailVerifiedToken {

	@Id
	private String id;
	
	private String token;
	
	@Column(name="digit_code")
	private String digitCode;
	
	private Date date;
	
}
