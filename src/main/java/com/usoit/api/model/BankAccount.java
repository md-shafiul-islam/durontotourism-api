package com.usoit.api.model;

import java.math.BigDecimal;
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
@Table(name="bank_account")
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="public_id")
	private String publicId;
	
	@ManyToOne
	@JoinColumn(name="user", referencedColumnName = "id")
	private User createdUser;
	
	@ManyToOne
	@JoinColumn(name="approve_user", referencedColumnName = "id")
	private User approveUser;
	
	@ManyToOne
	@JoinColumn(name="update_user", referencedColumnName = "id")
	private User updateUser;
	
	@ManyToOne
	@JoinColumn(name="update_approve_user", referencedColumnName = "id")
	private User updateApproveUser;
	
	@Column(name = "account_name")
	private String accountName;
	
	@Column(name = "account_number")
	private String accountNumber;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "branch_name")
	private String branchName;	
	
	@ManyToOne
	@JoinColumn(name = "country", referencedColumnName = "id")
	private Country country;	
	
	@ManyToOne
	@JoinColumn(name = "account_type", referencedColumnName = "id")
	private BankAccountType bankAccountType;
	
	private BigDecimal amount;
	
	@Column(name="bank_logo_url")
	private String bankLogoUrl;
	
	@ManyToOne
	@JoinColumn(name="status", referencedColumnName = "id")
	private Status status;
	
	private boolean active; //0 Or 1
	
	@Column(name="update_status")
	private int updateStatus; //Defult 0, 1 pending, 2 approve
	
	@Column(name="approve_status")
	private int approveStatus; //0,1,2,3 pending approve, update, reject
	
	@Column(name="update_request_status")
	private boolean updateReqStatus; // have update request or not
	
	@Column(name="wallet_enable")
	private boolean walletEnable;
	
	private Date date;

}
