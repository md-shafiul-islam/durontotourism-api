package com.usoit.api.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.model.Access;
import com.usoit.api.data.model.User;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.UserServices;

@Controller
@RequestMapping("")
@SessionAttributes(names = { "currentUser" })
public class MainController {

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private UserServices userServices;

	private User cUser;

	@RequestMapping("")
	public String getIndexPage(Model model, HttpSession httpSession, HttpServletRequest request) {

		/*
		 * User userByName = null; Principal principal = request.getUserPrincipal();
		 * 
		 * if (principal != null) { System.out.println("SVR User Name: " +
		 * principal.getName());
		 * 
		 * userByName = userServices.getUserByPersonalEmail(principal.getName());
		 * model.addAttribute("currentUser", userByName); }
		 * 
		 */

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {
			
			System.out.println("Access Is Null");
			System.out.println("Current User : " + cUser.getName() + "user Name: " + cUser.getUsername());
			
			return "redirect:/user/login";

		} else {

			System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());
			
			System.out.println("V: " + access.getView() + " A & V: " + access.getAdd() + "A P: "+access.getApprove() + " E: " + access.getEdit() + " E AP: "+ access.getUpdateApproval() + " AA:" + access.getAll() + " No Access: " + access.getNoAccess());
			
			if (access.getAdd() == 1 || access.getAll() == 1 || access.getView() == 1 || access.getUpdateApproval() == 1
					|| access.getEdit() == 1 || access.getApprove() == 1 || access.getNoAccess() == 1 || access.getNoAccess() == 0) {

				System.out.println("Access Get Add Pass & All Access !!");

			} else {
				
				System.out.println("Access Not null");
				System.out.println("Current User : " + cUser.getName() + "user Name: " + cUser.getUsername());
				return "redirect:/user/login";
			}

		}
		// Access Start
		System.out.println("Current User : " + cUser.getName() + "user Name: " + cUser.getUsername());

		System.out.println("Index Page Controller Run ...");
		return "/index";
	}

}
