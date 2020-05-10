package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.Role;
import com.usoit.api.data.vo.RestRole;

public interface RoleServices {

	public List<Role> getAllRoles();

	public long getCount();
	
	public Role getRoleById(int id);
	
	public boolean save(Role role);
	
	public boolean update(Role role);

	public RestRole getRestRoleByPublicId(String publicId);

	public List<Role> getAllGeneralRole();
	

}
