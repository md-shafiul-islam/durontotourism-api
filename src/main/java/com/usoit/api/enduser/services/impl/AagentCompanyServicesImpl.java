package com.usoit.api.enduser.services.impl;

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

import com.usoit.api.enduser.services.AagentCompanyServices;
import com.usoit.api.model.Agent;
import com.usoit.api.model.AgentCompany;
import com.usoit.api.model.AgentOwner;
import com.usoit.api.model.CustomerCredential;
import com.usoit.api.repository.AgentCompanyRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AagentCompanyServicesImpl implements AagentCompanyServices {

	@Autowired
	private AgentCompanyRepository agentCompanyRepository;

	private SessionFactory sessionFactory;

	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public AgentCompany getActiveAgentCompanyByAgentID(int id) {

		AgentCompany agentCompany = null;
		if (id > 0) {

			Session session = sessionFactory.openSession();

			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<AgentCompany> criteriaQuery = criteriaBuilder.createQuery(AgentCompany.class);
				Root<AgentCompany> root = criteriaQuery.from(AgentCompany.class);
				criteriaQuery.select(root);

				criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("agent").get("id"), id),
						criteriaBuilder.equal(root.get("active"), true)));
				Query<AgentCompany> query = session.createQuery(criteriaQuery);
				agentCompany = query.getSingleResult();
				transaction.commit();
				session.clear();
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				session.close();
			}

		}
		return agentCompany;
	}

	@Override
	public List<AgentCompany> getAgentCompaniesByAgentID(int id) {
		List<AgentCompany> agentCompanies = null;
		if (id > 0) {

			Session session = sessionFactory.openSession();

			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<AgentCompany> criteriaQuery = criteriaBuilder.createQuery(AgentCompany.class);
				Root<AgentCompany> root = criteriaQuery.from(AgentCompany.class);
				criteriaQuery.select(root);

				criteriaQuery.where(criteriaBuilder.equal(root.get("agent").get("id"), id));
				Query<AgentCompany> query = session.createQuery(criteriaQuery);
				agentCompanies = query.getResultList();
				transaction.commit();
				session.clear();
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				session.close();
			}

			return agentCompanies;
		}

		return null;
	}

}
