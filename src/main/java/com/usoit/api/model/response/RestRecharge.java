package com.usoit.api.model.response;

import java.util.Date;

import com.usoit.api.model.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestRecharge {
	
	private String genId;
	
	private String publicId;
	
	private RestBankAccount bankAccount;
	
	private double amount;
	
	private String transectionId;
	
	private String refferenceNote;
	
	private String rejectedNote;
	
	private RestPaymentStatus paymentStatus;
	
	private boolean rejected;

	private Date date;
	
	private Date transectionDate;
	
	private int approveStatus;
	
}
