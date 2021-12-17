package com.usoit.api.mapper;

import java.util.List;

import com.usoit.api.model.Agent;
import com.usoit.api.model.AgentCompany;
import com.usoit.api.model.AgentOwner;
import com.usoit.api.model.request.ReqAgent;
import com.usoit.api.model.request.ReqAgentCompany;
import com.usoit.api.model.request.ReqAgentOwner;
import com.usoit.api.model.request.ReqSubAgnet;
import com.usoit.api.model.response.ResSubAgent;
import com.usoit.api.model.response.RespAgent;

public interface AgentMapper {

	public Agent mapAgent(ReqAgent reqAgent);

	public RespAgent getRespAgent(Agent agent);

	public AgentCompany getAgentCompany(ReqAgentCompany reqCompany);

	public AgentOwner getAgentOwner(ReqAgentOwner reqOwner);

	public Agent mapSubAgent(ReqSubAgnet subAgent);

	public List<ResSubAgent> getResSubAgents(List<Agent> subAagents);
	
	public ResSubAgent mapResSubAgent(Agent agent);

	
}
