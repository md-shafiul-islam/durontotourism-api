package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqWalletApprove {
	
	private String publicId;
	
	private String approveNote;
	
	private String transactionId; //  Bank Transaction ID Or Mobile Transaction ID
	
	private String accountId; // Provider Account Public ID

}
