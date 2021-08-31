package com.usoit.api.model;

import javax.validation.constraints.NotBlank;

public class AjaxId {

	//@NotBlank(message = "Id can't empty!")
	public int id;

	
	public AjaxId() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
