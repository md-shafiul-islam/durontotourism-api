package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.Duration;

public interface DurationRespository extends CrudRepository<Duration, Integer> {

}
