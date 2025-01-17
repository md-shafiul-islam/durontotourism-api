package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.TermsAndConditions;

public interface TermsAndConditionsRepository extends CrudRepository<TermsAndConditions, Integer>{

	public TermsAndConditions getTermsAndConditionsByPublicId(String pubId);

}
