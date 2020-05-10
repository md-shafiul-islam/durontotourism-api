package com.usoit.api.servicesimpl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usoit.api.data.model.Credential;
import com.usoit.api.data.model.User;
import com.usoit.api.data.model.UserAddress;
import com.usoit.api.data.model.UserAddressTemp;
import com.usoit.api.data.model.UserTemp;
import com.usoit.api.repository.UserRepository;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.UserServices;

@SuppressWarnings("unchecked")
@Service
public class UserServicesImpl implements UserServices {

	private static final String SALT = "sa34";

	@Autowired
	private HelperServices helperServices;

	private static final Logger Log = LoggerFactory.getLogger(UserServices.class);

	@Autowired
	private UserRepository userRepository;

	private SessionFactory sessionFactory;

	private static User cUser;

	@Override
	public List<User> getAllUser() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public long getCount() {
		return userRepository.count();
	}

	@Override
	public boolean save(User user) {

		if (0 >= user.getId()) {

			long cId = userRepository.count() + 1;

			String genId = "EMP " + Long.toString(cId);

			user.setUserGemId(genId);

			user.setCredentials(new ArrayList<>());
			user.setStatus(0);

			Credential credential = new Credential();
			credential.setDate(new Date());
			credential.setStatus(1);
			credential.setUser(user);
			user.getCredentials().add(credential);

			credential.setPassword(getIncription("123456789"));
			user.setPublicId(helperServices.getRandomString(30));

			userRepository.save(user);

			if (user.getId() > 0) {
				return true;
			}
		}

		return false;
	}

	@Override
	public User getUerById(int i) {

		if (i > 0) {

			Optional<User> optional = userRepository.findById(i);

			if (optional != null) {

				return optional.get();

			}
		}

		return null;
	}

	@Override
	public User getUserByOfficePhoneNo(String officialPhoneNumber) {

		// User user = null;

		return userRepository.getUserByOfficialPhoneNumber(officialPhoneNumber);
	}

	@Override
	public User getUserByOfficialEmail(String officialEmail) {
		return userRepository.getUserByOfficialEmail(officialEmail);
	}

	@Override
	public User getUserByPersonalEmail(String personalEmail) {
		return userRepository.getUserByPersonalEmail(personalEmail);
	}

	@Override
	public User getUsrByPersonalPhone(String personalPhoneNumber) {
		return userRepository.getUserByPersonalPhoneNumber(personalPhoneNumber);
	}

	@Override
	public User chekUserValid(User user, String password) {

		if (user != null && password != null) {

			if (user.getCredentials() != null && !password.isEmpty()) {

				if (user.getCredentials().size() > 0) {

					for (Credential credential : user.getCredentials()) {

						String cPass = credential.getPassword();
						String pPass = password;

						System.out.println("C Pass: " + cPass + " In Pass: " + password);

						if (pPass.equals(cPass)) {

							System.out.println("DB Pass Match!!!");

						} else {
							System.out.println("DB Pass Not Match !!!!!!!!");
						}

						if (pPass.equals(cPass) && credential.getStatus() == 1) {

							System.out.println("DB Pass Match!!! With Active");
							UserServicesImpl.cUser = user;

							System.out.println("User Name: " + user.getName());
							return user;

						} else {
							System.out.println("DB Pass Not Match !!!!!!!! With Active");
						}

					}
				} else {
					UserServicesImpl.cUser = null;
					return null;
				}
			} else {
				UserServicesImpl.cUser = null;
				return null;
			}
		}
		UserServicesImpl.cUser = null;
		return null;
	}

	@Override
	public User getCurrentUser() {

		return cUser;

	}

	@Override
	public boolean updatePassword(Credential credential) {

		String encPass = this.getIncription(credential.getPassword());
		credential.setPassword(encPass);

		if (credential.getUser() != null) {

			return updateCredential(credential);
		}

		return false;
	}

	@Override
	public List<User> getAllInactiveUser() {
		return null;
	}

	@Override
	public List<User> getAllPendingUser() {
		return getAllPendingUsers();
	}

	@Override
	public User getUserByPublicID(String pubId) {
		return userRepository.getUserByPublicId(pubId);
	}

	@Override
	public boolean updateUserAddTempUser(String publicId) {

		if (publicId != null) {

			User dbUser = userRepository.getUserByPublicId(publicId);
			int dbId = 0;
			
			if (dbUser != null) {
				 dbId = dbUser.getId();
			}else {
				return false;
			}
			dbUser = null;
			
			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();
				
				User upateUser = session.get(User.class, dbId);

				upateUser.setUpdateApproveStatus(0);
				upateUser.setApprovalStatus(1);

				session.update(upateUser);
				

				transaction.commit();
				
				session.clear();

				return true;

			} catch (Exception e) {

				if (transaction != null) {

					transaction.rollback();
					e.printStackTrace();
				}
			}
		}

		return false;
	}
	
	@Override
	public boolean reverseTempUpdate(String publicId) {
		
		if (publicId != null) {

			User dbUser = userRepository.getUserByPublicId(publicId);
			
			int dbId = dbUser.getId();
			dbUser = null;
			
			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();
				
				User upateUser = session.get(User.class, dbId);

				upateUser.setUpdateApproveStatus(1);
				upateUser.setApprovalStatus(1);

				session.update(upateUser);
				

				transaction.commit();
				
				session.clear();

				return true;

			} catch (Exception e) {

				if (transaction != null) {

					transaction.rollback();
					e.printStackTrace();
				}
			}
		}
		
		return false;
		
	}

	@Override
	public List<User> getUpdatePandingUser() {

		int ap = 1;
		int uap = 0;
		Session session = sessionFactory.openSession();

		try {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
			Root<User> root = criteriaQuery.from(User.class);
			criteriaQuery.select(root);

			criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("approvalStatus"), ap), criteriaBuilder.equal(root.get("updateApproveStatus"), uap)));

			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			Query<User> query = session.createQuery(criteriaQuery);

			System.out.println("From Update Panding  Usres");

			session.clear();
			// session.close();
			System.out.println("After Session Clear and close !!");

			return query.getResultList();
			
		} catch (NoResultException e) {
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}

	@Override
	public boolean getUpdateUserReject(String publicId) {

		if (publicId != null) {

			if (publicId.length() == 30) {

				User dbUser = userRepository.getUserByPublicId(publicId);

				if (dbUser != null) {

					Session session = sessionFactory.openSession();
					Transaction transaction = null;

					try {

						transaction = session.beginTransaction();

						User updateUser = session.get(User.class, dbUser.getId());
						updateUser.setUpdateApproveStatus(1);
						session.update(updateUser);
						System.out.println("Update Done Session");
						transaction.commit();

						if (updateUser.getUpdateApproveStatus() == 1) {
							return true;
						} else {
							return false;
						}

					} catch (Exception e) {

						if (transaction != null) {

							transaction.rollback();
							e.printStackTrace();
						}

					}

				}
			}
		}

		return false;
	}

	private List<User> getAllPendingUsers() {

		int ap = 0;
		Session session = sessionFactory.openSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.equal(root.get("approvalStatus"), ap));

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
		Query<User> query = session.createQuery(criteriaQuery);

		System.out.println("From Action");

		session.clear();
		// session.close();
		System.out.println("After Session Clear and close !!");

		return query.getResultList();

	}
	
	@Override
	public List<User> getAllActiveUser() {
		
		int ap = 1;
		Session session = sessionFactory.openSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), ap), criteriaBuilder.equal(root.get("updateApproveStatus"), ap)));

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
		Query<User> query = session.createQuery(criteriaQuery);

		System.out.println("From Action");

		session.clear();
		System.out.println("After Session Clear and close !!");

		return query.getResultList();
	}

	@Override
	public boolean updateApprove(int id) {

		if (id > 0) {

			Optional<User> optional = userRepository.findById(id);

			if (optional != null) {

				User user = optional.get();

				user.setApprovalStatus(1);
				user.setStatus(1);

				userRepository.save(user);

				return true;
			}

		}

		return false;
	}

	private boolean updateCredential(Credential credential) {

		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaUpdate<Credential> criteriaQuery = criteriaBuilder.createCriteriaUpdate(Credential.class);
			Root<Credential> root = criteriaQuery.from(Credential.class);
			criteriaQuery.set(root.get("status"), 0);
			criteriaQuery.where(criteriaBuilder.equal(root.get("user").get("id"), credential.getUser().getId()));

			Query<Credential> query = session.createQuery(criteriaQuery);

			int affectedRow = query.executeUpdate();

			if (affectedRow >= 0) {
				session.save(credential);
			}

			session.clear();
			// session.close();
			System.out.println("After Session Clear and close !!");

			transaction.commit();

			if (credential.getId() > 0) {
				return true;
			}

		} catch (Exception e) {

			if (transaction != null) {

				transaction.rollback();
			}
		}

		return false;
	}

	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	private String getIncription(String getnPass) {

		BCryptPasswordEncoder enc = new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));

		return enc.encode(getnPass);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("UserSecurityServices Impl Loacl Run: User Name: " + username);

		User user = getUserByString(username);

		if (user == null) {
			Log.warn("User Name not found", username);
			throw new UsernameNotFoundException(username + "User Name not found");
		}

		return user;
	}

	@Override
	public User getUerByLongId(Long userId) {

		int id = Math.toIntExact(userId);
		return getUerById(id);
	}

	@Override
	public boolean updateUserByTempUser(User userTempData) {

		if (userTempData != null) {

			User dbUser = userRepository.getUserByPublicId(userTempData.getPublicId());

			if (dbUser != null) {

				Session session = sessionFactory.openSession();

				Transaction transaction = null;

				try {

					transaction = session.beginTransaction();

					User userUpgrade = session.get(User.class, dbUser.getId());

					if (userUpgrade != null) {

						dbUser = null;

						setUserTempDataToUser(userTempData, userUpgrade);
						userUpgrade.setUpdateApproveStatus(1);
						userUpgrade.setApprovalStatus(1);
						session.update(userUpgrade);

					} else {
						return false;
					}

					transaction.commit();
					
					return true;

				} catch (Exception e) {

					if (transaction != null) {

						transaction.rollback();
						e.printStackTrace();
					}
				}

			}
		}

		return false;
	}

	private void setUserTempDataToUser(User tempUserData, User userUpgrade) {

		userUpgrade.setAccountName(tempUserData.getAccountName());
		userUpgrade.setAccountNo(tempUserData.getAccountNo());
		userUpgrade.setAniversaryDate(tempUserData.getAniversaryDate());
		userUpgrade.setApplicationForJobUrl(tempUserData.getApplicationForJobUrl());
		userUpgrade.setAppointmentLetterUrl(tempUserData.getAppointmentLetterUrl());

		userUpgrade.setBachelorHonoursUrl(tempUserData.getBachelorHonoursUrl());
		userUpgrade.setBankName(tempUserData.getBankName());
		userUpgrade.setBranchName(tempUserData.getBranchName());
		userUpgrade.setBirthCertificateUrl(tempUserData.getBirthCertificateUrl());

		userUpgrade.setCaFcaCmaUrl(tempUserData.getCaFcaCmaUrl());
		userUpgrade.setContactName1(tempUserData.getContactName1());
		userUpgrade.setContactName2(tempUserData.getContactName2());

		userUpgrade.setDateOfBirth(tempUserData.getDateOfBirth());
		userUpgrade.setDiplomaUrl(tempUserData.getDiplomaUrl());
		userUpgrade.setDepartment(tempUserData.getDepartment());
		userUpgrade.setDesignation(tempUserData.getDesignation());

		userUpgrade.setEmergencyContactNo1(tempUserData.getEmergencyContactNo1());
		userUpgrade.setEmergencyContactNo2(tempUserData.getEmergencyContactNo2());
		userUpgrade.setEmploymentUrl(tempUserData.getEmploymentUrl());

		userUpgrade.setFatherName(tempUserData.getFatherName());
		userUpgrade.setFieldVerificationUrl(tempUserData.getFieldVerificationUrl());

		userUpgrade.setGender(tempUserData.getGender());

		userUpgrade.setHscEquivalentUrl(tempUserData.getHscEquivalentUrl());
		userUpgrade.setHusbandName(tempUserData.getHusbandName());

		userUpgrade.setJobAgreementUrl(tempUserData.getJobAgreementUrl());
		userUpgrade.setJoiningDate(tempUserData.getJoiningDate());

		userUpgrade.setMastersUrl(tempUserData.getMastersUrl());
		userUpgrade.setMobileAllowance(tempUserData.getMobileAllowance());
		userUpgrade.setMotherName(tempUserData.getMotherName());

		userUpgrade.setMaritalStatus(tempUserData.getMaritalStatus());

		userUpgrade.setName(tempUserData.getName());
		userUpgrade.setNationalIdNo(tempUserData.getNationalIdNo());
		userUpgrade.setNationalityCertificateUrl(tempUserData.getNationalityCertificateUrl());
		userUpgrade.setNidUrl(tempUserData.getNidUrl());

		userUpgrade.setOfficeLocation(tempUserData.getOfficeLocation());
		userUpgrade.setOfficialEmail(tempUserData.getOfficialEmail());
		userUpgrade.setOfficialPhoneNumber(tempUserData.getOfficialPhoneNumber());

		userUpgrade.setPassportNo(tempUserData.getPassportNo());
		userUpgrade.setPersonalEmail(tempUserData.getPersonalEmail());
		userUpgrade.setPersonalPhoneNumber(tempUserData.getPersonalPhoneNumber());
		userUpgrade.setPfCaFcaCmaUrl(tempUserData.getPfCaFcaCmaUrl());
		userUpgrade.setPledgeUrl(tempUserData.getPledgeUrl());
		userUpgrade.setProfileIimage(tempUserData.getProfileIimage());
		userUpgrade.setPublicId(tempUserData.getPublicId());

		userUpgrade.setResumeUrl(tempUserData.getResumeUrl());

		userUpgrade.setRole(tempUserData.getRole());

		userUpgrade.setSalary(tempUserData.getSalary());
		userUpgrade.setSecurityDeedUrl(tempUserData.getSecurityDeedUrl());
		userUpgrade.setSscEquivalentUrl(tempUserData.getSscEquivalentUrl());

		userUpgrade.setTinNno(tempUserData.getTinNno());
		userUpgrade.setUserSignaturesUrl(tempUserData.getUserSignaturesUrl());

		if (tempUserData.getUserAddresses() != null) {
			
			if (tempUserData.getUserAddresses().size() != 0 && tempUserData.getUserAddresses().size() <= 2) {
				
				System.out.println("User Address Size Pass!! " );

				if (tempUserData.getUserAddresses().get(0).getId() > 0 && tempUserData.getUserAddresses().get(1).getId() > 0) {
					
					System.out.println("User Address[0] ID Pass!! " );
					
					if (tempUserData.getUserAddresses().get(0) != null ) {

						UserAddress tempAddress = tempUserData.getUserAddresses().get(0);

						userUpgrade.getUserAddresses().get(0).setCity(tempAddress.getCity());
						userUpgrade.getUserAddresses().get(0).setCountry(tempAddress.getCountry());
						userUpgrade.getUserAddresses().get(0).setCountryCode(tempAddress.getCountryCode());
						userUpgrade.getUserAddresses().get(0).setHouse(tempAddress.getHouse());
						userUpgrade.getUserAddresses().get(0).setStreet(tempAddress.getStreet());
						userUpgrade.getUserAddresses().get(0).setTitle(tempAddress.getTitle());
						userUpgrade.getUserAddresses().get(0).setVillage(tempAddress.getVillage());
						userUpgrade.getUserAddresses().get(0).setZipCode(tempAddress.getZipCode());
					}

					if (tempUserData.getUserAddresses().get(1) != null) {
						
						System.out.println("User Address[1] ID Pass!! " );

						UserAddress tempAddress2 = tempUserData.getUserAddresses().get(1);

						userUpgrade.getUserAddresses().get(1).setCity(tempAddress2.getCity());
						userUpgrade.getUserAddresses().get(1).setCountry(tempAddress2.getCountry());
						userUpgrade.getUserAddresses().get(1).setCountryCode(tempAddress2.getCountryCode());
						userUpgrade.getUserAddresses().get(1).setHouse(tempAddress2.getHouse());
						userUpgrade.getUserAddresses().get(1).setStreet(tempAddress2.getStreet());
						userUpgrade.getUserAddresses().get(1).setTitle(tempAddress2.getTitle());
						userUpgrade.getUserAddresses().get(1).setVillage(tempAddress2.getVillage());
						userUpgrade.getUserAddresses().get(1).setZipCode(tempAddress2.getZipCode());
					}
				}else {
					System.out.println("User Address size Else!!");
					
					if (tempUserData.getUserAddresses().size() > 0) {
						
						List<UserAddress> userAddresses = new ArrayList<>();
						
						for (UserAddress tempAddress : tempUserData.getUserAddresses()) {
							
							UserAddress localAddress = new UserAddress();
							
							localAddress.setCity(tempAddress.getCity());
							localAddress.setCountry(tempAddress.getCountry());
							localAddress.setCountryCode(tempAddress.getCountryCode());
							localAddress.setHouse(tempAddress.getHouse());
							localAddress.setStreet(tempAddress.getStreet());
							localAddress.setTitle(tempAddress.getTitle());
							localAddress.setUser(userUpgrade);
							localAddress.setVillage(tempAddress.getVillage());
							localAddress.setZipCode(tempAddress.getZipCode());
							
							userAddresses.add(localAddress);
							
						}
						
						userUpgrade.setUserAddresses(userAddresses);
					}
				}
				
			}
		}

	}

	private User getUserByString(String stData) {

		User user = null;
		if (!stData.isEmpty()) {

			System.out.println("Pesonal Email Data not found");

			user = userRepository.getUserByOfficialEmail(stData);

			if (user == null) {
				System.out.println("Official Email Data not found");
				user = userRepository.getUserByPersonalPhoneNumber(stData);

				if (user == null) {

					System.out.println("Office Phone Data not found");

					user = userRepository.getUserByOfficialPhoneNumber(stData);

					if (user == null) {
						System.out.println("Pesonal Phone Data not found");

						return user = userRepository.getUserByPersonalEmail(stData);
					} else {// Official Phone

						return user;

					}

				} else {// Personal Phone

					return user;
				}

			} else { // Official Email

				return user;
			}

		} else { // Personal Email

			return user;
		}
	}

}
