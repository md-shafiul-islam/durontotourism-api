package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.usoit.api.data.model.Address;
import com.usoit.api.data.model.ContactPerson;
import com.usoit.api.data.model.Itarnary;
import com.usoit.api.data.model.PaymentInfo;
import com.usoit.api.data.model.User;
import com.usoit.api.data.model.VendorCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqVendor implements Serializable{


	
	private static final long serialVersionUID = -8015002106790491336L;

	
	private String publicId;
		
	private String companyName;
	
	private String ownerName;
	
	private String comPhoneNo;
	
	private String phoneCode;
	
	private List<ReqContactPerson> contactPersons = new ArrayList<>();
	
	private List<ReqAddress> addresses = new ArrayList<>();
	
	
	private String email;
	
	private String website;
	
	private List<ReqPaymentInfo> paymentInfos = new ArrayList<>();
	
	
	private int vendorCategory;	
	
}
