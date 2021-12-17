package com.usoit.api.model.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespAgent {

	private List<RespAgentOwner> agentOwners = new ArrayList<>();
	
	private RespAgentCompany agentCompany;
	
	private RespAgentGenarelInfo agentGenarelInfo;

	private List<RespAgent> subAgents = new ArrayList<>();

	private RespAgent parentAgent;

	private boolean active;

	private int approveStatus; // 0,1,2

	private boolean reject;

	private boolean updateReq;
	
	private boolean subAgent;
}
