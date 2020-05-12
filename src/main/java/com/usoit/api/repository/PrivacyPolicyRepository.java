package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.PrivacyPolicy;

public interface PrivacyPolicyRepository extends CrudRepository<PrivacyPolicy, Integer>{

	public PrivacyPolicy getPrivacyPolicyByPublicId(String pubId);

}
