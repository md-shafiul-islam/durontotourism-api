package com.usoit.api.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
	


}
