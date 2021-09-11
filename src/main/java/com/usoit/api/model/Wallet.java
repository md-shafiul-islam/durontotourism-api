package com.usoit.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity(name="wallet")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name="customer", referencedColumnName = "id")
	private Customer customer;
	
	@Column(name="total_amount")
	private double totalAmount;
	
	@Column(name="approve_status")
	private int approveStatus;
	
	@Column(name="active_status")
	private int activeStatus;
	
	private Date date;
}
