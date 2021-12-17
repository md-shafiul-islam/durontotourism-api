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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="rest_temp_mail")
public class RestMail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String mail;
	
	@Column(name="prev_mail")
	private String prevMail;
	
	@Column(name="ref_id")
	private int refId;
	
	private boolean active;
	
	private Date date;
}
