package com.usoit.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recharge {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="genareted_id")
	private String genId;
	
	@Column(name="public_id")
	private String publicId;
	
	@ManyToOne
	@JoinColumn(name="bank_account", referencedColumnName = "id")
	private BankAccount bankAccount;
	
	private double amount;
	
	@Column(name="ref_transection_id")
	private String transectionId;
	
	@Column(name="refference_note")
	private String refferenceNote;
	
	@Column(name="reject_note")
	private String rejectedNote;
	
	@ManyToOne
	@JoinColumn(name="status", referencedColumnName = "id")
	private PaymetStatus paymetStatus;
	
	@JoinColumn(name="reject_status", referencedColumnName = "id")
	private boolean rejected;

	private Date date;
	
	@Column(name="payment_date")
	private Date transectionDate;
}
