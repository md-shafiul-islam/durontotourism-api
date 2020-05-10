package com.usoit.api.servicesimpl;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.usoit.api.data.model.User;
import com.usoit.api.repository.UserRepository;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserRepository userRepository;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	private static final Logger Log = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String userName = null; 
		
		if(authentication.getPrincipal() instanceof Principal) {
            userName = ((Principal)authentication.getPrincipal()).getName();

       }else {
           userName = ((User)authentication.getPrincipal()).getUsername();
       }
		
		Principal uPrincipal = request.getUserPrincipal();
		
		if (uPrincipal != null) {
			userName = uPrincipal.getName();
		}
		
		System.out.println("Current login Name: " + userName);
		
		if (userName != null) {
			
			User user = userRepository.getUserByPersonalEmail(userName);
			
			System.out.println("User Name:" + user.getName());
			
			session.setAttribute("currentUser", user);
		}else {
			System.out.println("user Name is Null");
		}
		
		handeller(request, response, authentication);
		clearAuthenticationAttribute(request);
		
	}

	private void clearAuthenticationAttribute(HttpServletRequest request) {
		
		
	}

	private void handeller(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		
		String targetUrl = "/";
		
		if (response.isCommitted()) {
			
			Log.debug(
	                "Response has already been committed. Unable to redirect to "
	                        + targetUrl);
	        return;
		}
		 
		try {
			redirectStrategy.sendRedirect(request, response, targetUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String detarminTargetURL(Authentication authentication) {
		// TODO Auto-generated method stub
		return null;
	}

}
