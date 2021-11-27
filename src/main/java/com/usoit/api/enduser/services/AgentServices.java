package com.usoit.api.enduser.services;

import com.usoit.api.model.Agent;

public interface AgentServices {

	public Agent getAgentByUsername(String username);

	public boolean addAgent(Agent agent);

}
