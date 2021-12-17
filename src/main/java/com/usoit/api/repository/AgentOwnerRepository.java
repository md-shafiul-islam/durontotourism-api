package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.AgentOwner;

public interface AgentOwnerRepository extends CrudRepository<AgentOwner, Integer>{

	public AgentOwner getAgentOwnerByPublicId(String publicId);

}
