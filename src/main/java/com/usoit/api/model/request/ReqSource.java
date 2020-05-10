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
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqSource implements Serializable{
	
	
	private static final long serialVersionUID = 5685120499642653078L;

	private int id;
	
	@Transient
	private MultipartFile file;
	
	@Transient
	private MultipartFile fil2;
	
	private String sourceUrl;
	
	private String sourceUrl2;
	
	private Date expDate;

	
}
