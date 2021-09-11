package com.usoit.api.model.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqRecharge {
		
	private String accountId;
	
	private String amount;
		
	private String transType;
	
	private String transectionId;
	
	private String refferenceNote;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date transectionDate;
	
	private String attach;
	

}
