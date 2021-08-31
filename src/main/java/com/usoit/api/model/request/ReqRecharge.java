package com.usoit.api.model.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqRecharge {
		
	private String genId;
	
	private String publicId;
	
	private String accountId;
	
	private String amount;
	
	private String transectionId;
	
	private String refferenceNote;
	
	private Date date;
	
	private Date transectionDate;
	

}
