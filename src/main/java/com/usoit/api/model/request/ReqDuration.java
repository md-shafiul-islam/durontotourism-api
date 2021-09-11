package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqDuration implements Serializable{

	private static final long serialVersionUID = -1117486512309516781L;

	private int id;
	
	private String name;
	
	private String day;
	
	private String night;


	
	
	
}
