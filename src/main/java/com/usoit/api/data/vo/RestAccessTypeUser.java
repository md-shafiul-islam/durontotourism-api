package com.usoit.api.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestAccessTypeUser {

	private int id;

	private String name;

	private String value;

	private int numValue;
}
