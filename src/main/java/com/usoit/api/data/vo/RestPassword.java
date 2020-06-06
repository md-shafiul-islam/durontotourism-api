package com.usoit.api.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPassword {

	private String oldPass;
	
	private String newPassword;
	
	private String confPassword;
	
	private String userId;
	
	
}
