package com.usoit.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.Agent;
import com.usoit.api.model.AgentOwner;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Integer>{



}
