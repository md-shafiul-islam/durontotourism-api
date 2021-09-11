package com.usoit.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_acount_type")
public class BankAccountType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private String value;
	
	@Column(name="num_value")
	private String numValue;
	
	@OneToMany(mappedBy = "bankAccountType")
	private List<BankAccount> bankAccounts; 

}
