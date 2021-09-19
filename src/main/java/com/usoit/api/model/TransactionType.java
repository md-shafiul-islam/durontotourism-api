package com.usoit.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction_type")
public class TransactionType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String key;
	
	private String name;
	
	private String code;
	
}
