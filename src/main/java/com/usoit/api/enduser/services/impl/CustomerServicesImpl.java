package com.usoit.api.enduser.services.impl;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usoit.api.enduser.services.CustomerServices;
import com.usoit.api.model.Customer;
import com.usoit.api.model.CustomerCredential;
import com.usoit.api.model.MailVerifiedToken;
import com.usoit.api.model.PhoneVerificationToken;
import com.usoit.api.model.ProfileImage;
import com.usoit.api.model.Traveler;
import com.usoit.api.model.request.ChangePhone;
import com.usoit.api.model.request.ReqMailChange;
import com.usoit.api.model.request.ReqProfileImage;
import com.usoit.api.repository.CustomerRepository;
import com.usoit.api.services.UtilsServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServicesImpl implements CustomerServices {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UtilsServices utilsServices;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private SessionFactory sessionFactory;

	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("Customer User Auth Load user by name Run :) " + username);
		Customer customer = getEndCustomerByString(username);

//		System.out.println("GenPassword: "+ passwordEncoder.encode("123456"));

		if (customer == null) {
			log.warn("Client Name not found", username);
			throw new UsernameNotFoundException(username + "User Name not found");
		}

		return customer;

	}

	@Override
	public Customer getCustomerByUsername(String userName) {

		if (userName != null) {
			Customer customer = getEndCustomerByString(userName);
			if (customer != null) {
				log.info("Client found by " + userName);
				String clientType = "customer";
				if (customer.getClientType().equals(clientType)) {
					return customer;
				}

			}
		}

		throw new UsernameNotFoundException(userName + " Customer Name not found");

	}

	@Override
	public Customer getCustomerByPublicId(String publicId) {
		Optional<Customer> optional = customerRepository.getCustomerByPublicId(publicId);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Customer getCustomerByAuthUsername(String name) {

		return getCustomerUsingQuery("primaryEmail", name);
	}

	@Override
	public Customer getCustomerUsernameIsExist(String email) {

		if (email != null) {

			Optional<Customer> optional = customerRepository.getCustomerByPrimaryEmail(email);

			if (optional.isPresent()) {
				return optional.get();
			}
		}

		return null;
	}

	@Override
	public boolean addCustomer(Customer nCustomer) {

		if (nCustomer != null) {
			Date date = new Date();

			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				CustomerCredential credential = new CustomerCredential();
				credential.setActive(true);
				credential.setCustomer(nCustomer);
				credential.setDate(date);
				log.info("Agent Password "+ nCustomer.getPwd());
				if(nCustomer.getPassword() == null) {
					throw new RuntimeException("Password can't null");
				}
				credential.setPassword(passwordEncoder.encode(nCustomer.getPwd()));

				nCustomer.getCredentials().add(credential);
				if (nCustomer.getTravelers() != null) {
					if (nCustomer.getTravelers().get(0) != null) {
						Traveler traveler = nCustomer.getTravelers().get(0);
						traveler.setCustomer(nCustomer);
						log.info("Traveler First Name: " + traveler.getFirstName());
						log.info("Traveler Last Name: " + traveler.getLastName());
						log.info("Traveler Primary: " + traveler.isPrimary());
						session.persist(traveler);
//						List<Traveler> travelers = new ArrayList<>();
//						travelers.add(traveler);
//						nCustomer.setTravelers(travelers);
					}

				}

				nCustomer.setDate(date);
				nCustomer.setDateGroup(date);
				nCustomer.setPublicId(utilsServices.getCustomerPublicId());
				nCustomer.setGenId(utilsServices.getCustomerGenaretedId());

				session.persist(nCustomer);

				MailVerifiedToken mailVerifiedToken = new MailVerifiedToken();
				mailVerifiedToken.setDate(date);
				mailVerifiedToken.setId(nCustomer.getPublicId());
				mailVerifiedToken.setToken(utilsServices.getMailVerifiedToken());
				mailVerifiedToken.setDigitCode(utilsServices.getMailVerDegitCode());

				session.persist(mailVerifiedToken);
				transaction.commit();
				session.clear();
				session.close();

				return true;

			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
				}

				e.printStackTrace();
			}

		}

		return false;
	}

	@Override
	public boolean verifingCustomerPhone(int id) {

		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Customer customer = session.get(Customer.class, id);
			customer.setPhoneVerified(true);
			PhoneVerificationToken token = session.get(PhoneVerificationToken.class, customer.getPublicId());
			session.delete(token);
			session.update(customer);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean verifingCustomerMail(int id) {

		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Customer customer = session.get(Customer.class, id);
			customer.setEmailVerified(true);
			MailVerifiedToken token = session.get(MailVerifiedToken.class, customer.getPublicId());
			session.delete(token);
			session.update(customer);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public MailVerifiedToken updateCustomerVerificationMail(String publicId) {

		if (publicId != null) {
			if (!publicId.isEmpty()) {

				Session session = sessionFactory.openSession();
				Transaction transaction = null;

				try {

					transaction = session.beginTransaction();

					MailVerifiedToken dbToken = session.get(MailVerifiedToken.class, publicId);
					dbToken.setDate(new Date());
					dbToken.setDigitCode(utilsServices.getMailVerDegitCode());
					dbToken.setToken(utilsServices.getMailVerifiedToken());
					session.update(dbToken);

					transaction.commit();
					session.clear();
					session.close();
					return dbToken;
				} catch (Exception e) {

					if (transaction != null) {
						transaction.rollback();
					}
					session.close();
				}

			}
		}

		return null;
	}

	@Override
	public PhoneVerificationToken updateCustomerVerificationSMS(String publicId) {
		if (publicId != null) {
			if (!publicId.isEmpty()) {

				Session session = sessionFactory.openSession();
				Transaction transaction = null;

				try {

					transaction = session.beginTransaction();

					PhoneVerificationToken dbToken = session.get(PhoneVerificationToken.class, publicId);
					dbToken.setDate(new Date());
					dbToken.setCode(utilsServices.getPhoneOtpCode());
					session.update(dbToken);

					transaction.commit();
					session.clear();
					session.close();
					return dbToken;
				} catch (Exception e) {

					if (transaction != null) {
						transaction.rollback();
					}
					session.close();
				}

			}
		}

		return null;
	}

	@Override
	public Traveler getCustomerInformation(int id) {
		Traveler traveler = null;
		if (id > 0) {
			Session session = sessionFactory.openSession();

			Customer customer = session.get(Customer.class, id);
			if (customer != null) {

				if (customer.getTravelers() != null) {
					for (Traveler tvlr : customer.getTravelers()) {
						if (tvlr.isPrimary()) {
							traveler = tvlr;
						}
					}
				}
			}

			session.clear();
			session.close();
		}
		return traveler;
	}

	@Override
	public boolean changeCustomerMail(Customer customer, ReqMailChange mailChange) {

		boolean status = false;

		if (customer != null && mailChange != null) {
			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				Customer dbCustomer = session.get(Customer.class, customer.getId());

				if (dbCustomer != null) {

					dbCustomer.setPrimaryEmail(mailChange.getEmail());
					dbCustomer.setEmailVerified(false);
					session.update(dbCustomer);
					transaction.commit();
					status = true;
					session.clear();
				}

			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
				}
				session.clear();
			}

			session.close();
		}

		return status;
	}

	@Override
	public boolean changeCustomerPhone(Customer customer, ChangePhone changePhone) {

		boolean status = false;

		if (customer != null && changePhone != null) {

			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				Customer dbCustomer = session.get(Customer.class, customer.getId());

				if (dbCustomer != null) {

					dbCustomer.setPhoneCode(changePhone.getCode());
					dbCustomer.setPhoneNo(changePhone.getPhoneNo());
					dbCustomer.setPhoneVerified(false);
					transaction.commit();
					status = true;
				}

				session.clear();

			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
				}

				session.clear();

			}

			session.close();
		}

		return status;
	}

	@Override
	public Customer getCustomerPhoneIsExist(String phoneNo, String code) {

		Customer customer = null;
		try {
			Session session = sessionFactory.openSession();

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
			Root<Customer> root = criteriaQuery.from(Customer.class);
			criteriaQuery.select(root);

			criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("phoneNo"), phoneNo),
					criteriaBuilder.equal(root.get("phoneCode"), code)));

			Query<Customer> query = session.createQuery(criteriaQuery);

			customer = query.getSingleResult();
			session.clear();
			session.close();
		}catch (NoResultException ne) {
			log.info("Customer Not found using Phone No Msg "+ne.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customer;
	}
	
	@Override
	public boolean addOrUpdateProfileImage(Customer customer, ReqProfileImage image) {
		
		boolean status = false;
		
		
		if(customer != null && image != null) {
			ProfileImage profileImage = getCustomerActiveImage(customer);
			
			Session session = sessionFactory.openSession();
			Transaction transaction = null;
			try {
				
				transaction = session.beginTransaction();
				
				if(profileImage != null) {
					log.info("Customer Profile Image Url "+image.getUrl());
					ProfileImage dbProfileImage = session.get(ProfileImage.class, profileImage.getId());
					log.info("Customer DB Profile Image Url "+image.getUrl() + " ID "+dbProfileImage.getId());
					profileImage = null;
					dbProfileImage.setActive(false);
					session.update(dbProfileImage);
				}
				
				ProfileImage nProfileImage = new ProfileImage();
				nProfileImage.setActive(true);
				nProfileImage.setCustomer(customer);
				nProfileImage.setDate(new Date());
				nProfileImage.setUrl(image.getUrl());
				session.persist(nProfileImage);
								
				transaction.commit();
				session.clear();
				status = true;
			} catch (Exception e) {
				
				if(transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
				session.clear();
				
			}
			
			session.close();
			
			
		}
		
		return status;
	}
	

	
	private ProfileImage getCustomerActiveImage(Customer customer) {
		ProfileImage image = null;
		Session session = sessionFactory.openSession();
		try {
			

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ProfileImage> criteriaQuery = criteriaBuilder.createQuery(ProfileImage.class);
			Root<ProfileImage> root = criteriaQuery.from(ProfileImage.class);
			criteriaQuery.select(root);

			criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("customer").get("id"), customer.getId()),
					criteriaBuilder.equal(root.get("active"), true)));

			Query<ProfileImage> query = session.createQuery(criteriaQuery);

			image = query.getSingleResult();
			session.clear();
			
		}catch (NoResultException ne) {
			log.info("Customer Profile Image Not found. Msg "+ne.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
		return image;
	}

	private Customer getEndCustomerByString(String username) {

		Customer customer = null;

		customer = getCustomerByPrimaryEmail(username);

		if (customer == null) {
			customer = getCustomerByPhoneNo(username);
		}

		return customer;
	}

	private Customer getCustomerByPhoneNo(String phoneNo) {
		return getCustomerUsingQuery("phoneNo", phoneNo);
	}

	private Customer getCustomerByPrimaryEmail(String primaryEmail) {
		return getCustomerUsingQuery("primaryEmail", primaryEmail);
	}

	private Customer getCustomerUsingQuery(String fieldName, String value) {

		Customer customer = null;
		if (fieldName != null) {
			Session session = sessionFactory.openSession();

			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
				Root<Customer> root = criteriaQuery.from(Customer.class);
				criteriaQuery.select(root);

				criteriaQuery.where(criteriaBuilder.equal(root.get(fieldName), value));
				Query<Customer> query = session.createQuery(criteriaQuery);
				customer = query.uniqueResult();
				transaction.commit();

				if (customer != null) {
					customer.getCredentials();
					if (customer.getCredentials() != null) {
						for (CustomerCredential credential : customer.getCredentials()) {
							credential.getPassword();
							credential.isActive();
						}
					}
					if (customer.getTravelers() != null) {
						for (Traveler traveler : customer.getTravelers()) {
							traveler.getFirstName();
							traveler.isPrimary();
						}
					}
					log.info("Customer Services Imp Customer Find By " + fieldName + " : " + value);
				}
				session.clear();
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				session.close();
			}

		}
		return customer;

	}

}
