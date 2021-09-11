package com.usoit.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestUpdateInf {
	
	private String fieldName;
	
	private String value;

}
