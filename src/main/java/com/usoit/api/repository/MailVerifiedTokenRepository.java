package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.MailVerifiedToken;

@Repository
public interface MailVerifiedTokenRepository extends CrudRepository<MailVerifiedToken, String>{

}
