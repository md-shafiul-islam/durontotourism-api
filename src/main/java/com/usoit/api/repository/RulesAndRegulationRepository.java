package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.RulesAndRegulation;

@Repository
public interface RulesAndRegulationRepository extends CrudRepository<RulesAndRegulation, Integer>{

	

}
