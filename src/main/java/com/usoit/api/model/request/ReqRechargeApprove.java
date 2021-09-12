package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqRechargeApprove {
	
	private String publicId;
	
	private double netAmount;
	
	private double chargeAmount;
	
	private int status;
	
	private int rejectStatus;

}
