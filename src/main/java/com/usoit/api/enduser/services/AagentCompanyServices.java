package com.usoit.api.enduser.services;

import java.util.List;

import com.usoit.api.model.AgentCompany;

public interface AagentCompanyServices {

	public List<AgentCompany> getAgentCompaniesByAgentID(int id);

	public AgentCompany getActiveAgentCompanyByAgentID(int id);

}
