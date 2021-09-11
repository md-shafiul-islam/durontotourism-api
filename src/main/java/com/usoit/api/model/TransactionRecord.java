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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="transection_record")
public class TransactionRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="public_id")
	private String publicId;
	
	@OneToOne
	@JoinColumn(name="bank_account", referencedColumnName = "id")
	private BankAccount bankAccount;
	
	@OneToOne
	@JoinColumn(name="recharge", referencedColumnName = "id")
	private Recharge recharge;
	
	@ManyToOne
	@JoinColumn(name = "customer", referencedColumnName = "id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="user", referencedColumnName = "id")
	private User user;
	
	/*
	@OneToOne
	@JoinColumn(name="air_booking", referencedColumnName = "id")
	private AirBooking airBooking;
	
	@OneToOne
	@JoinColumn(name="wallet_with_draw", referencedColumnName = "id")
	private WalletWithDraw walletWithDraw;
	
	@OneToOne
	@JoinColumn(name="bank_withdarw")
	private BankWithDraw bankWithDraw;
	
	@OneToOne
	@JoinColumn(name="with_darw")
	private WithDraw withDraw;
	
	@OneToOne
	@JoinColumn(name="bank_deposit")
	private BankDeposit bankDeposit;
	
	@OneToOne
	@JoinColumn(name="deposit")
	private Deposit deposit;
	*/
	
	private double amount;
	
	private int pay;
	
	private int receive;
	
	private Date date;
	
	@Column(name = "date_group")
	private Date dateGroup;
	
	
	
	
	
	
}

