package com.usoit.api.servicesimpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Passport;
import com.usoit.api.model.User;
import com.usoit.api.services.PassportServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PassportServicesImpl implements PassportServices {

	private SessionFactory sessionFactory;

	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public Passport getActivePassportUsingTravelerID(int id) {
		Passport passport = null;
		if (id > 0) {
			Session session = sessionFactory.openSession();

			try {

				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Passport> criteriaQuery = criteriaBuilder.createQuery(Passport.class);
				Root<Passport> root = criteriaQuery.from(Passport.class);
				criteriaQuery.select(root);

				criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("traveler").get("id"), id),
						criteriaBuilder.equal(root.get("active"), 1)));

				Query<Passport> query = session.createQuery(criteriaQuery);
				
				passport = query.getSingleResult();


			} catch (NoResultException e) {
				log.info("Passport Not found using traveler");
			} catch (Exception e) {
				e.printStackTrace();
			}
			session.clear();
			session.close();
			
		}

		return passport;
	}
}
