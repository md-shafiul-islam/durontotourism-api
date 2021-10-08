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
@Table(name="withdraw_bank_details")
public class WithDrawBankDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "customer", referencedColumnName = "id")
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name="wallet_withdraw", referencedColumnName = "id")
	private WalletWithDraw walletWithDarw;
	
	@Column(name = "account_name")
	private String accountName;	

	@Column(name = "account_number")
	private String bankAccountNumber;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "branch_name")
	private String branchName;

	private Date date;
	
	@Column(name="date_group")
	private Date dateGroup;
}
