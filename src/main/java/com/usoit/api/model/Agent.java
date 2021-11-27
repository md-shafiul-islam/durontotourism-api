package com.usoit.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(value = "agent")
public class Agent extends Customer{
	
	//DTA-00-0000-0000
	//Agent Gen ID DTA(MN)(YN)(RND4N)
	//Company name 2 digit, User Category 1 digit,  Month 2 Digit, Year 4 Digit, Random number 4 digit
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "agent", fetch = FetchType.LAZY)
	private List<AgentOwner> agentOwners = new ArrayList<>();
	
	@Column(name="applicant_name")
	private String applicantName;	
	
	@Column(name="personal_ponecode")
	private String  personalPhoneCode;
	
	@Column(name="personal_phone")
	private String pesonalPhoneNo;
	
	@Column(name="secondary_email")
	private String email;
	
	@OneToOne(mappedBy = "agent")
	private AgentCompany agentCompany;
	
	@OneToMany(mappedBy = "parentAgent")
	private List<Agent> subAgents = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="parent_agent", referencedColumnName = "id")
	private Agent parentAgent;
		
	private boolean active; 
	
	@Column(name="approve_status")
	private int approveStatus; //0,1,2
	
	private boolean reject;
	
	@Column(name="update_req")
	private boolean updateReq;
	
}
