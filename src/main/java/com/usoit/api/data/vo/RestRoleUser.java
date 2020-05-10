package com.usoit.api.data.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestRoleUser {

	private int id;

	private List<RestAccessUser> accesses = new ArrayList<>();

	private String name;

	private String description;

	private Date date;

	private Date dateGroupe;

}
