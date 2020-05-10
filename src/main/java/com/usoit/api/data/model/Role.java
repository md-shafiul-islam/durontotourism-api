package com.usoit.api.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "public_id")
	private String publicId; 
	
	@Column(name = "gen_id")
	private String genId;
	
	/*
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	@JoinTable(name = "role_access_type", joinColumns = {@JoinColumn(name="role_id", referencedColumnName = "id")},inverseJoinColumns = {@JoinColumn(name="type_id", referencedColumnName = "id")})
	private List<AccessType> accessTypes = new ArrayList<>();
	*/
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	private List<Access> accesses = new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	private List<User> users = new ArrayList<>();
	
	private String name;
	
	private String description;
	
	private Date date;
	
	@Column(name = "date_group")
	private Date dateGroupe;
	
	@Column(name = "auth_status")
	private int authStatus;
	

}
