package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqCredential implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1302954979441050470L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int status;
	
	private String password;
	
	
	
}
