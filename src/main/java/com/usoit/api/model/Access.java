package com.usoit.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "access")
public class Access implements Serializable{
	
	
	private static final long serialVersionUID = -6020323598091984120L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "public_id")
	private String publicId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "access_type", referencedColumnName = "id")
	private AccessType accessType;
	
	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;
	
	private String name;
	
	private String description;
	
	@Column(name = "read_acc")
	private int view;
	
	@Column(name = "zero_access")
	private int noAccess;
	
	@Column(name = "add_acc")
	private int add;
	
	@Column(name = "edit_acc")
	private int edit;
	
	@Column(name = "add_approve")
	private int approve;
	
	@Column(name = "edit_approve")
	private int updateApproval;
	
	@Column(name = "all_access")
	private int all;
	
	

	
	
}
