package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{

	public Role getRoleByPublicId(String publicId);

}
