package com.usoit.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="withdraw_mob_acccount")
public class WithDrawMobilBanking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name="wallte_withdarw", referencedColumnName = "id")
	private WalletWithDraw walletWithDarw;
	
	@Column(name="mobile_bank_name")
	private String mobileBankName;
	
	@Column(name = "mobile_bank_account_no")
	private String mobilBankPhoneNo;

}
