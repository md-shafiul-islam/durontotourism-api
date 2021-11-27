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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="passport")
public class Passport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="traveler", referencedColumnName = "id")
	private Traveler traveler;
	
	@Column(name="issuing_country")
	private String country;
	
	private String number;
		
	private String attach;
	
	private boolean active;	
	
	@Column(name="expiry_date")
	private Date expiryDate;
	
	private Date date;

}
