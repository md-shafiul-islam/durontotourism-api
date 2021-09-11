package com.usoit.api.exception;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionResponse implements Serializable{

	
	private static final long serialVersionUID = -2684820846690897452L;
	
	private Date timeStamp;
	
	private String message;
	
	private String details;
	

	public ExceptionResponse(Date timeStamp, String message, String details) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}
	
	
	

}
