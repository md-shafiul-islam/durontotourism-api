package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.PhoneVerificationToken;

@Repository
public interface PhoneVerificationTokenRepository extends CrudRepository<PhoneVerificationToken, String>{

}
