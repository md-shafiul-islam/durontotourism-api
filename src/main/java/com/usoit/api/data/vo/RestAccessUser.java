package com.usoit.api.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestAccessUser {

	private int id;

	private RestAccessTypeUser accessType;

	private String name;

	private String description;

	private int view;

	private int noAccess;

	private int add;

	private int edit;

	private int approve;

	private int updateApproval;

	private int all;

}
