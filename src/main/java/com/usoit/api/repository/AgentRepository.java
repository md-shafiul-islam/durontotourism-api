package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.Agent;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Integer>{

}
