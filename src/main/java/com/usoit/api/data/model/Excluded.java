package com.usoit.api.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
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
@Table(name = "excluded")
public class Excluded {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO )
	private int id;
	
	private String name;
	
	private String description;
	
	private String value;
	
	
}
