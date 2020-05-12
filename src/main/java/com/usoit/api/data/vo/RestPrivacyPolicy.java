package com.usoit.api.data.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestPrivacyPolicy {

	private String publicId;
	
	private String name;
	
	private String description;
	
	private Date date;
}
