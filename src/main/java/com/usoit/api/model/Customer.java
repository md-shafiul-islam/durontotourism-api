package com.usoit.api.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "customer")
public class Customer extends User{

	private static final long serialVersionUID = 1L;
	
	
	@OneToOne(mappedBy = "customer")
	private Wallet wallet;
	
	@OneToMany(mappedBy = "customer")
	private List<WalletWithDraw> walletWithDraws;
	


}
