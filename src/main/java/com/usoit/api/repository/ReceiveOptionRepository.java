package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.ReceiveOption;

public interface ReceiveOptionRepository extends CrudRepository<ReceiveOption, Integer>{

	@Query
	public Optional<ReceiveOption> getReceiveOptionByValue(String value);

}
