package com.usoit.api.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.usoit.api.services.HelperServices;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private Environment environment;
	
	@Autowired
	private HelperServices helperServices;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	private static final String SALT = "sa34";
	
	private static final String[] PUBLIC_MATCHERS = {
			
			"/build/**",
			"/dist/**",
			"/plugins/**",
			"/asset-img/**"
			
	};
	
	@Autowired
	private UserDetailsService userSecurityServices;
	
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
			//.and().addFilter(new AuthenticationFilter(authenticationManager()));
		
		http.csrf().disable().cors().and()
			.formLogin().failureUrl("/user/login?error").defaultSuccessUrl("/").loginPage("/user/login").permitAll().successHandler(authenticationSuccessHandler)
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/user/login").deleteCookies("remember-me").permitAll()
			.and().rememberMe();   
		
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		System.out.println("Run Configur Param: AuthenticationManagerBuilder");
		//auth.userDetailsService(() userSecurityServices).passwordEncoder(passwordEncoder());
		auth.userDetailsService(userSecurityServices).passwordEncoder(passwordEncoder());
	}

	
	
}
