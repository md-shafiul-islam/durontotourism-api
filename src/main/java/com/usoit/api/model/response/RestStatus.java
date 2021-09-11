package com.usoit.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestStatus {

	private int id;
	
	private String name;
	
	private String value;
	
	private int numValue;
}
