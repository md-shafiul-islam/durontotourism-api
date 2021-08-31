package com.usoit.api.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
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
	@JoinColumn(name = "bank_type", referencedColumnName = "id")
	private BankType bankingType;
	
	private int status;
	
	private boolean active;

}
