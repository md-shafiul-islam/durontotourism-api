package com.usoit.api.mapper.impl;

import org.springframework.stereotype.Service;

import com.usoit.api.mapper.AgentMapper;
import com.usoit.api.model.Agent;
import com.usoit.api.model.AgentOwner;
import com.usoit.api.model.request.ReqAgent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgentMapperImpl implements AgentMapper {

	@Override
	public Agent mapAgent(ReqAgent reqAgent) {

		if (reqAgent != null) {
			Agent agent = new Agent();
			agent.setApplicantName(reqAgent.getApplicantName());
			agent.setPhoneCode(reqAgent.getCode());
			agent.setPhoneNo(reqAgent.getPhone());
			agent.setPrimaryEmail(reqAgent.getEmail());
			agent.setPwd(reqAgent.getPwd());
			
			log.info("Map Agent Email "+ reqAgent.getEmail());
			log.info("Map Agent Passord "+ reqAgent.getPwd());
			
			if(reqAgent.getPwd() == null) return null;
			if(reqAgent.getPwd().isEmpty()) return null;
			
			if (reqAgent.getOwnCode() != null || reqAgent.getOwnerEmail() != null || reqAgent.getOwnerName() != null
					|| reqAgent.getOwnPhone() != null) {
				AgentOwner agentOwner = new AgentOwner();
				
				agentOwner.setAgent(agent);
				agentOwner.setCode(reqAgent.getOwnCode());
				agentOwner.setName(reqAgent.getOwnerName());
				agentOwner.setEmail(reqAgent.getOwnerEmail());
				agentOwner.setPhone(reqAgent.getOwnPhone());
				
				agent.getAgentOwners().add(agentOwner);

			}
			
			return agent;
		}

		return null;
	}
}
