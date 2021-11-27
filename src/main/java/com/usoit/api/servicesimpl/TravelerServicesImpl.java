package com.usoit.api.servicesimpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Customer;
import com.usoit.api.model.Passport;
import com.usoit.api.model.Traveler;
import com.usoit.api.model.request.ReqTraveler;
import com.usoit.api.repository.TravelerRepository;
import com.usoit.api.services.PassportServices;
import com.usoit.api.services.TravelerServices;
import com.usoit.api.services.UtilsServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TravelerServicesImpl implements TravelerServices {

	@Autowired
	private PassportServices passportServices;

	@Autowired
	private TravelerRepository travelerRepository;

	@Autowired
	private UtilsServices utilsServices;

	private SessionFactory sessionFactory;

	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public List<Traveler> getTravelersByCustomerId(int id) {
		
		if(id > 0) {
			List<Traveler> travelers = null;
			
			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {
				transaction = session.beginTransaction();

				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Traveler> criteriaQuery = criteriaBuilder.createQuery(Traveler.class);
				Root<Traveler> root = criteriaQuery.from(Traveler.class);
				criteriaQuery.select(root);

				criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("customer").get("id"), id), criteriaBuilder.equal(root.get("primary"), false)));

				Query<Traveler> query = session.createQuery(criteriaQuery);

				travelers = query.getResultList();
				
				for (Traveler traveler : travelers) {
					if(traveler != null) {
						for (Passport passport : traveler.getPassports()) {
							passport.getId();
						}
					}
				}
				
				transaction.commit();
				session.clear();

			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
				}
				session.clear();
				e.printStackTrace();
			}

			session.close();
			
			return travelers;
		}
		
		return null;
	}
	
	@Override
	public boolean updatePrimaryTraveler(int id, ReqTraveler travelerInf) {

		log.info("Update Primary Traveler " + id + " Traveler ", travelerInf.getFirstName());

		boolean status = false;
		if (id > 0 && travelerInf != null) {

			if (travelerInf.getStatus() == 1) {
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				try {

					transaction = session.beginTransaction();

					Traveler traveler = session.get(Traveler.class, id);

					boolean saveStatus = false;

					if (traveler == null) {
						traveler = new Traveler();
						saveStatus = true;
					}

					if (traveler != null) {

						if (travelerInf.getDateOfBirth() != null)
							traveler.setDateOfBirth(travelerInf.getDateOfBirth());
						if (travelerInf.getFirstName() != null)
							traveler.setFirstName(travelerInf.getFirstName());
						if (travelerInf.getLastName() != null)
							traveler.setLastName(travelerInf.getLastName());
						if (travelerInf.getGender() != null)
							traveler.setGender(travelerInf.getGender());
						if (travelerInf.getNationality() != null)
							traveler.setNationality(travelerInf.getNationality());

						if (travelerInf.getPassportAttach() != null && travelerInf.getPassportExpiry() != null
								&& travelerInf.getPassportIssuCuntry() != null && travelerInf.getPassportNo() != null) {

							Passport activePassportDB = passportServices
									.getActivePassportUsingTravelerID(traveler.getId());
							if (activePassportDB != null) {
								Passport dbPassport = session.get(Passport.class, activePassportDB.getId());
								dbPassport.setActive(false);
								session.update(dbPassport);
							}

							Passport passport = new Passport();
							passport.setTraveler(traveler);
							passport.setActive(true);
							passport.setAttach(travelerInf.getPassportAttach());
							passport.setExpiryDate(travelerInf.getPassportExpiry());
							passport.setCountry(travelerInf.getPassportIssuCuntry());
							passport.setNumber(travelerInf.getPassportNo());
							passport.setDate(new Date());
							session.persist(passport);

						}
					}

					if (saveStatus) {
						session.save(traveler);
					} else {
						session.update(traveler);
					}

					transaction.commit();
					session.clear();
					status = true;

				} catch (Exception e) {

					if (transaction != null) {
						transaction.rollback();
					}
					session.close();
					e.printStackTrace();
				}

				session.close();

			}
		}

		return status;
	}

	@Override
	public boolean addTraveler(Traveler traveler) {
		boolean status = false;
		if (traveler != null) {

			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {
				transaction = session.beginTransaction();
				traveler.setPublicId(utilsServices.getUnicId());

				if (traveler.getPassports() != null) {
					if (traveler.getPassports().size() > 0) {

						Passport passport = null;
						for (Passport pspt : traveler.getPassports()) {
							if (pspt.isActive()) {
								passport = pspt;
							}
						}

						passport.setTraveler(traveler);
						passport.setDate(new Date());
						session.persist(passport);
					}
				}
				traveler.setPassports(null);
				session.persist(traveler);

				transaction.commit();
				session.clear();
				status = true;
			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
				session.clear();
			}

			session.close();

		}

		return status;
	}

	@Override
	public boolean createGuestTraveler(ReqTraveler travelerInf, Customer customer) {
		boolean status = false;
		if (customer != null && travelerInf != null) {
			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();
				Traveler traveler = getTraveler(travelerInf);
				Passport passport = getPassport(travelerInf);

				if (traveler != null) {
					traveler.setCustomer(customer);
					session.persist(traveler);
				}

				if (passport != null) {
					passport.setTraveler(traveler);
					session.persist(passport);
					status = true;
				}

				transaction.commit();
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

	private Passport getPassport(ReqTraveler travelerInf) {

		if (travelerInf != null) {
			Passport passport = new Passport();

			passport.setActive(true);
			passport.setAttach(travelerInf.getPassportAttach());
			passport.setCountry(travelerInf.getPassportIssuCuntry());
			passport.setDate(new Date());
			passport.setExpiryDate(travelerInf.getPassportExpiry());
			passport.setNumber(travelerInf.getPassportNo());

			return passport;
		}
		return null;
	}

	private Traveler getTraveler(ReqTraveler travelerInf) {
		if (travelerInf != null) {

			Traveler traveler = new Traveler();
			traveler.setDate(new Date());
			traveler.setDateOfBirth(travelerInf.getDateOfBirth());
			traveler.setFirstName(travelerInf.getFirstName());
			traveler.setGender(travelerInf.getGender());
			traveler.setLastName(travelerInf.getLastName());
			traveler.setNationality(travelerInf.getNationality());
			traveler.setPublicId(utilsServices.getUnicId());
			traveler.setPhoneCode(travelerInf.getPhoneCode());
			traveler.setPhoneNo(travelerInf.getPhoneNo());
			return traveler;
		}
		return null;
	}

	@Override
	public boolean updateTraveler(ReqTraveler travelerInf) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Traveler getPrimaryTravelerUsingCustomerID(int id) {
		Traveler traveler = null;
		if (id > 0) {
			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {
				transaction = session.beginTransaction();

				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Traveler> criteriaQuery = criteriaBuilder.createQuery(Traveler.class);
				Root<Traveler> root = criteriaQuery.from(Traveler.class);
				criteriaQuery.select(root);

				criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("customer").get("id"), id),
						criteriaBuilder.equal(root.get("primary"), true)));

				Query<Traveler> query = session.createQuery(criteriaQuery);

				traveler = query.getSingleResult();

				transaction.commit();
				session.clear();

			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
				}
				session.clear();
				e.printStackTrace();
			}

			session.close();
		}

		return traveler;
	}

}
