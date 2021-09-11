package com.usoit.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="update_bank_account")
public class BankAccountUpdateInf {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="bank_id")
	private String bankId;

	@Column(name="field_name")
	private String fieldName;

	private String value;

	private int status;

	private int approve;

	private boolean active;

}
