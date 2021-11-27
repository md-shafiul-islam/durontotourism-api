package com.usoit.api.enduser.services.impl;

import java.util.ArrayList;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usoit.api.enduser.services.AgentServices;
import com.usoit.api.model.Agent;
import com.usoit.api.model.AgentOwner;
import com.usoit.api.model.Customer;
import com.usoit.api.model.CustomerCredential;
import com.usoit.api.repository.AgentRepository;
import com.usoit.api.services.UtilsServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgentServicesImpl implements AgentServices{
	
	@Autowired
	private UtilsServices utilsServices;
	
	@Autowired
	private AgentRepository agentRepository;
	
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
	public Agent getAgentByUsername(String username) {
		
		if (username != null) {
			Agent agent = getEndAgentByString(username);
			System.out.println("Agent Is "+ agent);
			if (agent != null) {
				System.out.println("Agent Type, "+agent.getClientType());
				
				log.info("Client found by "+ username);
				String clientType = "agent";
				if(agent.getClientType().equals(clientType)) {
					return agent;
				}
				
			}
		}

		throw new UsernameNotFoundException(username + " Agnet Name not found");
	}

	private Agent getEndAgentByString(String username) {
		
		Agent agent = null;
		
		agent = getAgnetByPrimaryEmail("primaryEmail", username);
		
		if(agent == null) {
			agent = getAgentByPhoneNo("phoneNo", username);
		}	
		
		return agent;
	}

	private Agent getAgentByPhoneNo(String fieldName, String username) {		
		
		return getAgentUsingQuery(fieldName, username);
	}

	private Agent getAgnetByPrimaryEmail(String fieldName, String username) {
		return getAgentUsingQuery(fieldName, username);
	}

	@Override
	public boolean addAgent(Agent agent) {
		
		if(agent != null) {
			
			Session session = sessionFactory.openSession();
			Transaction transaction = null;
			
			try {
				
				transaction = session.beginTransaction();
				Date date = new Date();

				CustomerCredential credential = new CustomerCredential();
				credential.setActive(true);
				credential.setDate(date);
				credential.setCustomer(agent);
				
				log.info("Save Agent Passord "+ agent.getPwd());
				
				credential.setPassword(passwordEncoder.encode(agent.getPwd())); 
				
				agent.setPublicId(utilsServices.getCustomerPublicId());
				agent.setGenId(utilsServices.getAgentGenID());
				
				if(agent.getAgentOwners() != null) {
					AgentOwner tmpAgentOwner = agent.getAgentOwners().get(0);
					if(tmpAgentOwner != null) {
						AgentOwner agentOwner = new AgentOwner();
						agentOwner.setPublicId(utilsServices.getUnicId());
						agentOwner.setAgent(agent);
						agentOwner.setCode(tmpAgentOwner.getCode());
						agentOwner.setEmail(tmpAgentOwner.getEmail());
						agentOwner.setName(tmpAgentOwner.getName());
						agentOwner.setPhone(tmpAgentOwner.getPhone());
						List<AgentOwner> agentOwners = new ArrayList<>();
						agentOwners.add(agentOwner);
						agent.setAgentOwners(agentOwners);
						session.persist(agentOwner);
					}
					
					
				}
				agent.getCredentials().add(credential);
				session.persist(agent);
				
				transaction.commit();
				session.clear();
				session.close();
				
				return true;
				
			} catch (Exception e) {
				
				if(transaction != null) {
					transaction.rollback();
				}
				
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
	private Agent getAgentUsingQuery(String fieldName, String value) {

		Agent agent = null;
		if (fieldName != null) {
			Session session = sessionFactory.openSession();

			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				CriteriaQuery<Agent> criteriaQuery = criteriaBuilder.createQuery(Agent.class);
				Root<Agent> root = criteriaQuery.from(Agent.class);
				criteriaQuery.select(root);

				criteriaQuery.where(criteriaBuilder.equal(root.get(fieldName), value));
				Query<Agent> query = session.createQuery(criteriaQuery);
				agent = query.uniqueResult();
				transaction.commit();

				if (agent != null) {
					agent.getCredentials();
					if(agent.getCredentials() != null) {
						for (CustomerCredential credential : agent.getCredentials()) {
							credential.getPassword();
							credential.isActive();
						}
					}
					
					if(agent.getAgentOwners() != null) {
						for (AgentOwner owner : agent.getAgentOwners()) {
							owner.getEmail();
						}
					}
					System.out.println("Agent Services Imp 188 Find By " + fieldName + " : " + value);
				}

			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				session.close();
			}

		}
		return agent;

	}


}
