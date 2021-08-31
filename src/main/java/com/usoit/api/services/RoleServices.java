package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.vo.RestRole;
import com.usoit.api.model.Role;

public interface RoleServices {

	public List<Role> getAllRoles();

	public long getCount();
	
	public Role getRoleById(int id);
	
	public boolean save(Role role);
	
	public boolean update(Role role);

	public RestRole getRestRoleByPublicId(String publicId);

	public List<Role> getAllGeneralRole();
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

	public List<Role> getAllRoleWitAccess();

	public Role getRoleWitAccessById(int id);
	

}
