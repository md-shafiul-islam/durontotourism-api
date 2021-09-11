package com.usoit.api.model;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public String getGenId() {
		return genId;
	}

	public void setGenId(String genId) {
		this.genId = genId;
	}

	public List<Access> getAccesses() {
		return accesses;
	}

	public void setAccesses(List<Access> accesses) {
		this.accesses = accesses;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDateGroupe() {
		return dateGroupe;
	}

	public void setDateGroupe(Date dateGroupe) {
		this.dateGroupe = dateGroupe;
	}

	public int getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(int authStatus) {
		this.authStatus = authStatus;
	}
	
	
	
}
