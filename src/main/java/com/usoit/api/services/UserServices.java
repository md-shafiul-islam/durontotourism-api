package com.usoit.api.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.usoit.api.data.model.Credential;
import com.usoit.api.data.model.User;

public interface UserServices extends UserDetailsService{

	public List<User> getAllUser();

	public long getCount();

	public boolean save(User user);

	public User getUserByPersonalEmail(String email);

	public User getUserByOfficialEmail(String email);

	public User getUsrByPersonalPhone(String email);

	public User getUserByOfficePhoneNo(String email);

	public User chekUserValid(User user, String pass);
	
	public User getCurrentUser();

	public User getUerById(int i);

	public boolean updatePassword(Credential credential);

	public List<User> getAllInactiveUser();

	public List<User> getAllPendingUser();

	public boolean updateApprove(int id);

	public User getUerByLongId(Long userId);

	public User getUserByPublicID(String pubId);

	public boolean updateUserAddTempUser(String userTemp);

	public List<User> getUpdatePandingUser();

	public boolean getUpdateUserReject(String publicId);

	public boolean updateUserByTempUser(User user);

	public boolean reverseTempUpdate(String publicId);

	public List<User> getAllActiveUser();

	public boolean addRejectUserByPublicId(String publicId, String msg);

	public boolean addApproveAction(String pubId, String msg);

	public boolean getActiveActionByPubId(String pubId, String msg);

	public boolean getInactiveActionByPubId(String pubId, String msg);

	public List<User> getAllConfrimUsers(String msg);

	public List<User> getAllRejectedUser();



}
