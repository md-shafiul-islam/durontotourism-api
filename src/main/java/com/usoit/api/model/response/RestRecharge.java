package com.usoit.api.model.response;

import java.util.Date;

import com.usoit.api.model.BankAccount;
import com.usoit.api.model.PaymetStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestRecharge {
	
	private String genId;
	
	private String publicId;
	
	private BankAccount bankAccount;
	
	private double amount;
	
	private String transectionId;
	
	private String refferenceNote;
	
	private String rejectedNote;
	
	private PaymetStatus paymetStatus;
	
	private boolean rejected;

	private Date date;
	
	private Date transectionDate;
	
}
