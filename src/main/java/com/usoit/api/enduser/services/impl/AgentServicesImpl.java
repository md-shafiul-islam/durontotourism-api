package com.usoit.api.enduser.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usoit.api.enduser.services.AgentServices;
import com.usoit.api.model.Agent;
import com.usoit.api.model.AgentCompany;
import com.usoit.api.model.AgentOwner;
import com.usoit.api.model.Customer;
import com.usoit.api.model.CustomerCredential;
import com.usoit.api.model.RestMail;
import com.usoit.api.model.TempPhone;
import com.usoit.api.model.request.ReqAgentCompany;
import com.usoit.api.model.request.ReqRestMail;
import com.usoit.api.model.request.ReqRestPassword;
import com.usoit.api.model.request.ReqRestPhone;
import com.usoit.api.repository.AgentOwnerRepository;
import com.usoit.api.repository.AgentRepository;
import com.usoit.api.services.UtilsServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgentServicesImpl implements AgentServices {

	@Autowired
	private UtilsServices utilsServices;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private SessionFactory sessionFactory;

	@Autowired
	private AgentOwnerRepository agentOwnerRepository;

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
			System.out.println("Agent Is " + agent);
			if (agent != null) {
				System.out.println("Agent Type, " + agent.getClientType());

				log.info("Client found by " + username);
				String clientType = "agent";
				if (agent.getClientType().equals(clientType)) {
					return agent;
				}

			}
		}

		throw new UsernameNotFoundException(username + " Agnet Name not found");
	}

	@Override
	public Agent getAgentByAuthUsername(String name) {

		return getEndAgentByString(name);
	}

	@Override
	public boolean createSubAgent(Agent subAgent, Agent cAgent) {
		boolean status = false;

		if (subAgent != null && cAgent != null) {
			
			if(!isNullOrEmpty(subAgent.getPwd()) && isNullOrEmpty(subAgent.getPrimaryEmail())) {
				Session session = sessionFactory.openSession();
				Transaction transaction = null;

				try {
					transaction = session.beginTransaction();
					Date date = new Date();
					subAgent.setDate(date);
					subAgent.setDateGroup(date);
					subAgent.setGenId(utilsServices.getSubAgentGenID());
					subAgent.setPublicId(utilsServices.getCustomerPublicId());
					subAgent.setParentAgent(cAgent);
					session.persist(subAgent);
					
					CustomerCredential credential = new CustomerCredential();
					credential.setCustomer(subAgent);
					credential.setActive(true);
					credential.setDate(date);
					credential.setPassword(passwordEncoder.encode(subAgent.getPwd()));
					
					session.persist(credential);
					
					transaction.commit();
					session.clear();
					if (subAgent.getId() > 0) {
						status = true;
					}
				} catch (Exception e) {

					if (transaction != null) {
						transaction.rollback();
					}
					e.printStackTrace();
				}

				session.close();
			}
			
			
		}

		return status;
	}

	private boolean isNullOrEmpty(String str) {
		if(str != null) {
			if(!str.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Agent> getSubAagents(Agent cAgent) {

		List<Agent> agents = null;
		Session session = sessionFactory.openSession();
		try {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Agent> criteriaQuery = criteriaBuilder.createQuery(Agent.class);
			Root<Agent> root = criteriaQuery.from(Agent.class);
			criteriaQuery.select(root);

			criteriaQuery.where(criteriaBuilder.equal(root.get("parentAgent").get("id"), cAgent.getId()));

			Query<Agent> query = session.createQuery(criteriaQuery);

			agents = query.getResultList();
			session.clear();

		} catch (NoResultException ne) {
			log.info("Sub Agent Not found using parent Msg " + ne.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
		return agents;

	}

	private Agent getEndAgentByString(String username) {

		Agent agent = null;

		agent = getAgnetByPrimaryEmail("primaryEmail", username);

		if (agent == null) {
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

		if (agent != null) {

			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();
				Date date = new Date();

				CustomerCredential credential = new CustomerCredential();
				credential.setActive(true);
				credential.setDate(date);
				credential.setCustomer(agent);

				log.info("Save Agent Passord " + agent.getPwd());

				credential.setPassword(passwordEncoder.encode(agent.getPwd()));

				agent.setPublicId(utilsServices.getCustomerPublicId());
				agent.setGenId(utilsServices.getAgentGenID());

				if (agent.getAgentOwners() != null) {
					AgentOwner tmpAgentOwner = agent.getAgentOwners().get(0);
					if (tmpAgentOwner != null) {
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
				agent.setDate(date);
				agent.setDateGroup(date);
				agent.getCredentials().add(credential);
				session.persist(agent);

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
	public boolean updateAgentCompanyInfo(AgentCompany nCompany, Agent agent) {
		boolean status = false;
		if (agent != null && nCompany != null) {

			Session session = sessionFactory.openSession();

			Transaction transaction = null;

			try {
				Date date = new Date();
				transaction = session.beginTransaction();
				Agent dbAgent = session.get(Agent.class, agent.getId());
				nCompany.setAgent(dbAgent);

				if (dbAgent != null) {
					int companyId = 0;
					if (dbAgent.getAgentCompanies() != null) {
						for (AgentCompany dbCompany : dbAgent.getAgentCompanies()) {
							if (dbCompany.isActive()) {
								companyId = dbCompany.getId();
							}
						}
					}
					log.info("Agent Company ID " + companyId);

					AgentCompany dbCompany = session.get(AgentCompany.class, companyId);
					log.info("Agent Company DB Company " + dbCompany);
					if (dbCompany != null) {
						mapAgentCompanyUpdateInfo(dbCompany, nCompany);
						dbCompany.setActive(false);
						session.update(dbCompany);
					}

					nCompany.setActive(true);
					nCompany.setDate(date);
					nCompany.setDateGroup(date);
					session.persist(nCompany);
				}

				transaction.commit();

			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
				}
				log.info("Agent Company Update Failed. Error, " + e.getMessage());

				e.printStackTrace();
			}

			session.close();
		}
		return status;
	}

	@Override
	public boolean addAgentOwnerInfo(AgentOwner agentOwner, Agent agent) {
		boolean status = false;
		log.info("Strat Agent Owner Add ...");

		if (agentOwner != null && agent != null) {
			Session session = sessionFactory.openSession();

			Transaction transaction = null;

			try {
				transaction = session.beginTransaction();

				agentOwner.setAgent(agent);
				agentOwner.setDate(new Date());
				agentOwner.setPublicId(utilsServices.getCustomerPublicId());
				agentOwner.setActive(true);
				session.persist(agentOwner);
				transaction.commit();
				if (agentOwner.getId() > 0) {
					status = true;
				}
				session.clear();
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
	public boolean updateAgentOwnerInfo(AgentOwner agentOwner, Agent agent) {
		boolean status = false;

		AgentOwner tempOwner = agentOwnerRepository.getAgentOwnerByPublicId(agentOwner.getPublicId());

		log.info("Agent Owner Update ... SR " + tempOwner);

		if (agent != null && agentOwner != null && tempOwner != null) {
			int ownerId = tempOwner.getId();

			tempOwner = null;
			Session session = sessionFactory.openSession();
			Transaction transaction = null;
			log.info("Update ... Agent Owner ID " + ownerId);
			try {
				transaction = session.beginTransaction();

				AgentOwner dbOwner = session.get(AgentOwner.class, ownerId);
				if (dbOwner != null) {
					mapOwnerUpdate(dbOwner, agentOwner);
					dbOwner.setActive(false);
					session.update(dbOwner);
				}
				agentOwner.setActive(true);
				agentOwner.setDate(new Date());
				agentOwner.setAgent(agent);
				agentOwner.setPublicId(utilsServices.getCustomerPublicId());
				session.persist(agentOwner);

				transaction.commit();
				session.clear();

				if (agentOwner.getId() > 0) {
					status = true;
				}
			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
				}
				session.clear();
				e.printStackTrace();
			}

			session.close();
		}

		return status;
	}

	@Override
	public boolean restAgnetPassword(ReqRestPassword restPass, Agent cAgent) {
		boolean status = false;
		if (cAgent != null && restPass != null) {

			if (passwordEncoder.matches(restPass.getPrevPassword(), cAgent.getPassword())) {

				Session session = sessionFactory.openSession();
				Transaction transaction = null;

				try {

					transaction = session.beginTransaction();

					Agent agent = session.get(Agent.class, cAgent.getId());
					int cId = 0;
					for (CustomerCredential crd : agent.getCredentials()) {
						if (crd.isActive()) {
							cId = crd.getId();
						}
					}

					if (cId > 0 && agent != null) {
						CustomerCredential dbCredential = session.get(CustomerCredential.class, cId);
						dbCredential.setActive(false);
						session.update(dbCredential);

						CustomerCredential credential = new CustomerCredential();
						credential.setActive(true);
						credential.setCustomer(agent);
						if (restPass.getPwd() != null) {
							credential.setPassword(passwordEncoder.encode(restPass.getPwd()));
						}

						credential.setDate(new Date());
						session.persist(credential);
						if (credential.getId() > 0) {
							status = true;
						}

					}
					transaction.commit();

				} catch (Exception e) {

					if (transaction != null) {
						transaction.rollback();
					}
					e.printStackTrace();
				}
				session.close();
			}

		}
		return status;
	}

	@Override
	public boolean changeAgnetMail(ReqRestMail restMail, Agent cAgent) {

		boolean status = false;

		if (restMail != null && cAgent != null) {

			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();
				Agent agent = session.get(Agent.class, cAgent.getId());
				RestMail mailTemp = new RestMail();
				if (agent != null) {
					agent.setMailChangeStatus(true);
					session.update(agent);

					mailTemp.setMail(restMail.getEmail());
					mailTemp.setPrevMail(agent.getPrimaryEmail());
					mailTemp.setRefId(agent.getId());
					mailTemp.setDate(new Date());
					mailTemp.setActive(true);

					session.persist(mailTemp);
				}

				// TODO: Impliment Mail change

				transaction.commit();
				if (mailTemp.getId() > 0) {
					status = true;
				}
				session.clear();
			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
				}

				e.printStackTrace();
			}

			session.close();

		}

		return status;
	}

	@Override
	public boolean changeAgnetPhone(ReqRestPhone restPhone, Agent cAgent) {
		boolean status = false;

		if (restPhone != null && cAgent != null) {
			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {
				transaction = session.beginTransaction();

				TempPhone tempPhone = new TempPhone();
				Agent agent = session.get(Agent.class, cAgent.getId());
				if (agent != null) {
					agent.setPhoneChangeStatus(true);
					session.update(agent);

					tempPhone.setCode(restPhone.getCode());
					tempPhone.setPhone(restPhone.getPhone());
					tempPhone.setPrevCode(agent.getPhoneCode());
					tempPhone.setPrevPhoneNo(agent.getPhoneNo());
					tempPhone.setRefId(agent.getId());
					tempPhone.setActive(true);
					
					session.persist(tempPhone);
				}

				transaction.commit();
				if (tempPhone.getId() > 0) {
					status = true;
				}
				session.clear();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
			}

			session.close();
		}

		return status;
	}

	private void mapOwnerUpdate(AgentOwner dbOwner, AgentOwner agentOwner) {

		log.info("mapOwnerUpdate ...");

		if (dbOwner != null && agentOwner != null) {

			String code = !isStrEmpty(agentOwner.getCode()) ? agentOwner.getCode() : dbOwner.getCode();
			String country = !isStrEmpty(agentOwner.getCountry()) ? agentOwner.getCountry() : dbOwner.getCountry();

			String district = !isStrEmpty(agentOwner.getDistrict()) ? agentOwner.getDistrict() : dbOwner.getDistrict();
			String email = !isStrEmpty(agentOwner.getEmail()) ? agentOwner.getEmail() : dbOwner.getEmail();
			String houseNoOrVillage = !isStrEmpty(agentOwner.getHouseNoOrVillage()) ? agentOwner.getHouseNoOrVillage()
					: dbOwner.getHouseNoOrVillage();
			String name = !isStrEmpty(agentOwner.getName()) ? agentOwner.getName() : dbOwner.getName();
			String nationalIdAttach = !isStrEmpty(agentOwner.getNationalIdAttach()) ? agentOwner.getNationalIdAttach()
					: dbOwner.getNationalIdAttach();
			String nationalIdNo = !isStrEmpty(agentOwner.getNationalIdNo()) ? agentOwner.getNationalIdNo()
					: dbOwner.getNationalIdNo();
			String ownerImage = !isStrEmpty(agentOwner.getOwnerImage()) ? agentOwner.getOwnerImage()
					: dbOwner.getOwnerImage();

			String passportAttach = !isStrEmpty(agentOwner.getPassportAttach()) ? agentOwner.getPassportAttach()
					: dbOwner.getPassportAttach();
			String passportNo = !isStrEmpty(agentOwner.getPassportNo()) ? agentOwner.getPassportNo()
					: dbOwner.getPassportNo();
			String phone = !isStrEmpty(agentOwner.getPhone()) ? agentOwner.getPhone() : dbOwner.getPhone();
			String policeStation = !isStrEmpty(agentOwner.getPoliceStation()) ? agentOwner.getPoliceStation()
					: dbOwner.getPoliceStation();
			String postalCode = !isStrEmpty(agentOwner.getPostalCode()) ? agentOwner.getPostalCode()
					: dbOwner.getPostalCode();

			String roadNameOrNo = !isStrEmpty(agentOwner.getRoadNameOrNo()) ? agentOwner.getRoadNameOrNo()
					: dbOwner.getRoadNameOrNo();

			agentOwner.setCode(code);
			agentOwner.setCountry(country);
			agentOwner.setDistrict(district);
			agentOwner.setEmail(email);
			agentOwner.setHouseNoOrVillage(houseNoOrVillage);
			agentOwner.setName(name);
			agentOwner.setNationalIdAttach(nationalIdAttach);
			agentOwner.setNationalIdNo(nationalIdNo);
			agentOwner.setOwnerImage(ownerImage);
			agentOwner.setPassportAttach(passportAttach);
			agentOwner.setPassportNo(passportNo);
			agentOwner.setPhone(phone);
			agentOwner.setPoliceStation(policeStation);
			agentOwner.setPostalCode(postalCode);
			agentOwner.setRoadNameOrNo(roadNameOrNo);
		}

	}

	private void mapAgentCompanyUpdateInfo(AgentCompany dbCompany, AgentCompany nCompany) {

		if (dbCompany != null && nCompany != null) {

			log.info("Session Map ...");

			log.info("BIN Attach  ..." + isStrEmpty(nCompany.getBinAttach()) + " DB " + dbCompany.getBinAttach());
			log.info("nCompany.getCompanyLogoAttach()  ..." + nCompany.getCompanyLogoAttach() + " DB "
					+ dbCompany.getCompanyLogoAttach());
			log.info(
					"nCompany.getTradeAttach()  ..." + nCompany.getTradeAttach() + " DB " + dbCompany.getTradeAttach());
			log.info("nCompany.getTinAttach() ..." + nCompany.getTinAttach() + " DB " + dbCompany.getTinAttach());

			String binUrl = !isStrEmpty(nCompany.getBinAttach()) ? nCompany.getBinAttach() : dbCompany.getBinAttach();

			String binCertificateNo = !isStrEmpty(nCompany.getBinCertificateNo()) ? nCompany.getBinCertificateNo()
					: dbCompany.getBinCertificateNo();

			String code = !isStrEmpty(nCompany.getCode()) ? nCompany.getCode() : dbCompany.getCode();

			String companyLogoAttach = !isStrEmpty(nCompany.getCompanyLogoAttach()) ? nCompany.getCompanyLogoAttach()
					: dbCompany.getCompanyLogoAttach();

			String companyName = !isStrEmpty(nCompany.getCompanyName()) ? nCompany.getCompanyName()
					: dbCompany.getCompanyName();

			String country = !isStrEmpty(nCompany.getCountry()) ? nCompany.getCountry() : dbCompany.getCountry();

			String districtOrCity = !isStrEmpty(nCompany.getDistrictOrCity()) ? nCompany.getDistrictOrCity()
					: dbCompany.getDistrictOrCity();

			String email = !isStrEmpty(nCompany.getEmail()) ? nCompany.getEmail() : dbCompany.getEmail();

			String houseOrVillage = !isStrEmpty(nCompany.getHouseOrVillage()) ? nCompany.getHouseOrVillage()
					: dbCompany.getHouseOrVillage();

			String phone = !isStrEmpty(nCompany.getPhone()) ? nCompany.getPhone() : dbCompany.getPhone();

			String policeStation = !isStrEmpty(nCompany.getPoliceStation()) ? nCompany.getPoliceStation()
					: dbCompany.getPoliceStation();

			String postalCode = !isStrEmpty(nCompany.getPostalCode()) ? nCompany.getPostalCode()
					: dbCompany.getPostalCode();

			String roadNameOrNo = !isStrEmpty(nCompany.getRoadNameOrNo()) ? nCompany.getRoadNameOrNo()
					: dbCompany.getRoadNameOrNo();

			String tinAttach = !isStrEmpty(nCompany.getTinAttach()) ? nCompany.getTinAttach()
					: dbCompany.getTinAttach();

			String tinCertificateNo = !isStrEmpty(nCompany.getTinCertificateNo()) ? nCompany.getTinCertificateNo()
					: dbCompany.getTinCertificateNo();

			String tradeAttach = !isStrEmpty(nCompany.getTradeAttach()) ? nCompany.getTradeAttach()
					: dbCompany.getTradeAttach();

			String tradeLiceseno = !isStrEmpty(nCompany.getTradeLiceseno()) ? nCompany.getTradeLiceseno()
					: dbCompany.getTradeLiceseno();

			nCompany.setBinAttach(binUrl);
			nCompany.setBinCertificateNo(binCertificateNo);
			nCompany.setCode(code);
			nCompany.setCompanyLogoAttach(companyLogoAttach);
			nCompany.setCompanyName(companyName);
			nCompany.setCountry(country);
			nCompany.setDistrictOrCity(districtOrCity);
			nCompany.setEmail(email);
			nCompany.setHouseOrVillage(houseOrVillage);
			nCompany.setPhone(phone);
			nCompany.setPoliceStation(policeStation);
			nCompany.setPostalCode(postalCode);
			nCompany.setRoadNameOrNo(roadNameOrNo);
			nCompany.setTinCertificateNo(tinCertificateNo);
			nCompany.setTinAttach(tinAttach);
			nCompany.setTradeAttach(tradeAttach);
			nCompany.setTradeLiceseno(tradeLiceseno);

			log.info("After nCompany.getBinAttach()  ..." + nCompany.getBinAttach() + " String " + binUrl);
			log.info("nCompany.getCompanyLogoAttach()  ..." + nCompany.getCompanyLogoAttach() + " Str "
					+ companyLogoAttach);
			log.info("nCompany.getTradeAttach()  ..." + nCompany.getTradeAttach() + " Str " + tradeAttach);
			log.info("nCompany.getTinAttach() ..." + nCompany.getTinAttach() + " Str " + tinAttach);
			log.info(
					"nCompany.getTinCertificateNo() ..." + nCompany.getTinCertificateNo() + " Str " + tinCertificateNo);

		}

	}

	private boolean isStrEmpty(String str) {
		if (str != null) {
			if (!str.isEmpty()) {
				return false;
			}
		}
		return true;
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
					if (agent.getCredentials() != null) {
						for (CustomerCredential credential : agent.getCredentials()) {
							credential.getPassword();
							credential.isActive();
						}
					}

					if (agent.getAgentOwners() != null) {
						for (AgentOwner owner : agent.getAgentOwners()) {
							owner.getEmail();
						}
					}

					if (agent.getAgentCompanies() != null) {
						for (AgentCompany company : agent.getAgentCompanies()) {
							if (company.isActive()) {
								agent.setActiveCompany(company);
							}
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
