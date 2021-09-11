package com.usoit.api.services;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.ContactPerson;

@Repository
public interface ContactPersonRepository extends CrudRepository<ContactPerson, Integer>{

	public ContactPerson getContactPersonByPublicId(String key);

}
