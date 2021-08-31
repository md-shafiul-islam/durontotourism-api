package com.usoit.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "source")
public class Source {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "itarnary", referencedColumnName = "id")
	private Itarnary itarnary;
	
	@Transient
	private MultipartFile file;
	
	@Transient
	private MultipartFile fil2;
	
	@Column(name = "source_url")
	private String sourceUrl;
	
	@Column(name = "source_url2")
	private String sourceUrl2;
	
	@Column(name = "exp_date")
	private Date expDate;
	

	
}
