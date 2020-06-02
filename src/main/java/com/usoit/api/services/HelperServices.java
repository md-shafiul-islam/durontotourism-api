package com.usoit.api.services;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.usoit.api.data.model.Access;
import com.usoit.api.data.model.User;
import com.usoit.api.data.vo.RestAccessUser;


public interface HelperServices {

public double decimelTwoPoint(double value);
	
	public User checkUserAccess(HttpSession httpSession, String type, int numType);

	public int getPageSize();
	
	public String getStaticDir();
	
	public String getUploadDir();

	/**
	 * 
	 * @param {@link MultipartFile} file
	 * @param {@link String path type DIR}
	 * @return {@link String} image DIR
	 */
	public String uploadImageAndGetUrl(MultipartFile file, String string);

	public Access getCurrentAccess();
	
	/**
	 * 
	 * @param int length
	 * @return {@link String} Generated Random string
	 */
	public String getRandomString(int length);

	/**
	 * 
	 * @param authUser Curent User Autor
	 * @param string Option
	 * @param i Option Num Value
	 * @return Access
	 */
	public Access getAccessByUser(User authUser, String string, int i);

	public boolean isValidAndLenghtCheck(String publicId, int lenght);

	public Map<String, RestAccessUser> getRestAccessByUser(User user);

	public User getUserByPrincipal(Principal principal);

	public Map<String, RestAccessUser> getAccessMapByPrincipal(Principal principal);
	
}
