package com.usoit.api.enduser.services;

import java.util.List;

import com.usoit.api.model.Agent;
import com.usoit.api.model.AgentCompany;
import com.usoit.api.model.AgentOwner;
import com.usoit.api.model.request.ReqAgentCompany;
import com.usoit.api.model.request.ReqRestMail;
import com.usoit.api.model.request.ReqRestPassword;
import com.usoit.api.model.request.ReqRestPhone;

public interface AgentServices {

	public Agent getAgentByUsername(String username);

	public boolean addAgent(Agent agent);

	public Agent getAgentByAuthUsername(String name);

	public boolean updateAgentCompanyInfo(AgentCompany agentCompany, Agent agent);

	public boolean addAgentOwnerInfo(AgentOwner agentOwner, Agent agent);

	public boolean updateAgentOwnerInfo(AgentOwner agentOwner, Agent agent);

	public boolean createSubAgent(Agent subAgent, Agent cAgent);

	public List<Agent> getSubAagents(Agent cAgent);

	public boolean restAgnetPassword(ReqRestPassword restPass, Agent cAgent);

	public boolean changeAgnetMail(ReqRestMail restMail, Agent cAgent);

	public boolean changeAgnetPhone(ReqRestPhone restPhone, Agent cAgent);


}
