package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqSubAgnet {

	private String agentId;
	private String code;
	private String email;
	private String name;
	private String phone;
	private String pwd;
}
