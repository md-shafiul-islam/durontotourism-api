package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqRechargeReject {
	
	private String publicId;
	
	private int rejcetStatus;
	
	private int status;
	
	private String rejctNote;

}
