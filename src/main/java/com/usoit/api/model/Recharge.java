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
@Table(name="wallet_recharge")
public class Recharge {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="genareted_id")
	private String genId;
	
	@Column(name="public_id")
	private String publicId;
	
	@ManyToOne
	@JoinColumn(name="customer", referencedColumnName = "id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="user", referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="bank_account", referencedColumnName = "id")
	private BankAccount bankAccount;
	
	private double amount;
	
	@Column(name="charge_amount")
	private double chargeAmount;
	
	@Column(name="net_wallet_amount")
	private double netAddWalletAmount;
	
	@Column(name="ref_transection_id")
	private String transectionId;
	
	@Column(name="refference_note")
	private String refferenceNote;
	
	@Column(name="reject_note")
	private String rejectedNote;
	
	@ManyToOne
	@JoinColumn(name="payment_status", referencedColumnName = "id")
	private PaymentStatus paymentStatus;
	
	private boolean rejected; 
	
	@Column(name="attach_url")
	private String attachUrl;

	private Date date;
	
	@Column(name="payment_date")
	private Date transectionDate;
	
	@Column(name="approve_status")
	private int approveStatus; //0 pending, 1 approve, 2 reected
	
	@Column(name="trans_type")
	private String transType;
	
	@Column(name="wallet_status")
	private boolean walletStatus;
	
	@Column(name="action_date")
	private Date actionDate;
}
