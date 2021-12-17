package com.usoit.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBank {

	private String id;
	
	private String name;

	private String logoUrl;

	private String contactInf;
}
