package com.usoit.api.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItarnaryDescriptionAPI {

	private int id;
	
	private String description;
	
	private RestItarnary itarnary;
		
}
