package com.usoit.api.servicesimpl;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Customer;
import com.usoit.api.model.ProfileImage;
import com.usoit.api.repository.ProfileImageRepository;
import com.usoit.api.services.ProfileImageServices;

@Service
public class ProfileImageServicesImpl implements ProfileImageServices{

	private SessionFactory sessionFactory;

	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}
	
	@Autowired
	private ProfileImageRepository profileImageRepository;
	
	
	@Override
	public ProfileImage getProfileImageByCustomer(Customer customer) {
		Optional<ProfileImage> optional = profileImageRepository.getProfileImageByCustomerAndActive(customer, true);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
}
