package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqMailSender {
	
	private String id;
	
	private String mailReceiver;
	
	private String subject;
	
	private String content;
	
	private boolean status;
}
