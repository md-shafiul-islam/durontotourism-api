package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>{

	public Role getRoleByPublicId(String publicId);

}
