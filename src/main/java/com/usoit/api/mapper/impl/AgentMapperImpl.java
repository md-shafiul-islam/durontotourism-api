package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.usoit.api.mapper.AgentMapper;
import com.usoit.api.model.Agent;
import com.usoit.api.model.AgentCompany;
import com.usoit.api.model.AgentOwner;
import com.usoit.api.model.request.ReqAgent;
import com.usoit.api.model.request.ReqAgentCompany;
import com.usoit.api.model.request.ReqAgentOwner;
import com.usoit.api.model.request.ReqSubAgnet;
import com.usoit.api.model.response.ResSubAgent;
import com.usoit.api.model.response.RespAgent;
import com.usoit.api.model.response.RespAgentCompany;
import com.usoit.api.model.response.RespAgentGenarelInfo;
import com.usoit.api.model.response.RespAgentOwner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgentMapperImpl implements AgentMapper {

	@Override
	public Agent mapAgent(ReqAgent reqAgent) {

		if (reqAgent != null) {
			Agent agent = new Agent();
			agent.setApplicantName(reqAgent.getApplicantName());
			agent.setPhoneCode(reqAgent.getCode());
			agent.setPhoneNo(reqAgent.getPhone());
			agent.setPrimaryEmail(reqAgent.getEmail());
			agent.setPwd(reqAgent.getPwd());

			log.info("Map Agent Email " + reqAgent.getEmail());
			log.info("Map Agent Passord " + reqAgent.getPwd());

			if (reqAgent.getPwd() == null)
				return null;
			if (reqAgent.getPwd().isEmpty())
				return null;

			if (reqAgent.getOwnCode() != null || reqAgent.getOwnerEmail() != null || reqAgent.getOwnerName() != null
					|| reqAgent.getOwnPhone() != null) {
				AgentOwner agentOwner = new AgentOwner();

				agentOwner.setAgent(agent);
				agentOwner.setCode(reqAgent.getOwnCode());
				agentOwner.setName(reqAgent.getOwnerName());
				agentOwner.setEmail(reqAgent.getOwnerEmail());
				agentOwner.setPhone(reqAgent.getOwnPhone());

				agent.getAgentOwners().add(agentOwner);

			}

			return agent;
		}

		return null;
	}

	@Override
	public RespAgent getRespAgent(Agent agent) {
		if (agent != null) {
			RespAgent respAgent = new RespAgent();

			RespAgentGenarelInfo genarelInfo = new RespAgentGenarelInfo();

			genarelInfo.setApplicantName(agent.getApplicantName());
			genarelInfo.setId(agent.getGenId());
			genarelInfo.setPhoneCode(agent.getPhoneCode());
			genarelInfo.setPhoneNo(agent.getPhoneNo());
			genarelInfo.setEmail(agent.getPrimaryEmail());
			genarelInfo.setPhoto(agent.getPhoto());
			genarelInfo.setPublicId(agent.getPublicId());
			genarelInfo.setSince(agent.getDate());
			respAgent.setAgentGenarelInfo(genarelInfo);
			RespAgentCompany company = mapRespAgentCompany(agent.getActiveCompany());
			if (company != null) {
				respAgent.setAgentCompany(company);
			}

			List<RespAgentOwner> agentOwners = mapRespActiveAgnetOwners(agent.getAgentOwners());

			if (agentOwners != null) {
				respAgent.setAgentOwners(agentOwners);
			}

			respAgent.setActive(agent.isActive());
			respAgent.setReject(agent.isReject());
			respAgent.setApproveStatus(agent.getApproveStatus());
			respAgent.setUpdateReq(agent.isUpdateReq());
			respAgent.setSubAgent(agent.isSubAgent());
			return respAgent;
		}
		return null;
	}

	@Override
	public AgentCompany getAgentCompany(ReqAgentCompany reqCompany) {

		if (reqCompany != null) {

			AgentCompany agentCompany = new AgentCompany();

			agentCompany.setBinAttach(reqCompany.getBinAttach());
			agentCompany.setBinCertificateNo(reqCompany.getBinCertificateNo());

			agentCompany.setCode(reqCompany.getCode());
			agentCompany.setCompanyLogoAttach(reqCompany.getCompanyLogoAttach());
			agentCompany.setCompanyName(reqCompany.getCompanyName());
			agentCompany.setCountry(reqCompany.getCountry());
			agentCompany.setDistrictOrCity(reqCompany.getDistrictOrCity());
			agentCompany.setEmail(reqCompany.getEmail());
			agentCompany.setHouseOrVillage(reqCompany.getHouseOrVillage());
			agentCompany.setPhone(reqCompany.getPhone());
			agentCompany.setPoliceStation(reqCompany.getPoliceStation());
			agentCompany.setPostalCode(reqCompany.getPostalCode());
			agentCompany.setRoadNameOrNo(reqCompany.getRoadNameOrNo());

			agentCompany.setTinAttach(reqCompany.getTinAttach());
			agentCompany.setTinCertificateNo(reqCompany.getTinCertificateNo());

			agentCompany.setTradeAttach(reqCompany.getTradeAttach());
			agentCompany.setTradeLiceseno(reqCompany.getTradeLiceseno());

			return agentCompany;
		}

		return null;
	}

	@Override
	public AgentOwner getAgentOwner(ReqAgentOwner reqOwner) {
		if (reqOwner != null) {

			AgentOwner agentOwner = new AgentOwner();

			agentOwner.setPublicId(reqOwner.getId());

			agentOwner.setCode(reqOwner.getCode());
			agentOwner.setCountry(reqOwner.getCountry());

			agentOwner.setDistrict(reqOwner.getDistrict());
			agentOwner.setEmail(reqOwner.getEmail());
			agentOwner.setHouseNoOrVillage(reqOwner.getHouseNoOrVillage());

			agentOwner.setName(reqOwner.getName());
			agentOwner.setNationalIdAttach(reqOwner.getNationalIdAttach());
			agentOwner.setNationalIdNo(reqOwner.getNationalIdNo());

			agentOwner.setOwnerImage(reqOwner.getOwnerImage());

			agentOwner.setPassportAttach(reqOwner.getPassportAttach());
			agentOwner.setPassportNo(reqOwner.getPassportNo());
			agentOwner.setPhone(reqOwner.getPhone());
			agentOwner.setPoliceStation(reqOwner.getPoliceStation());
			agentOwner.setPostalCode(reqOwner.getPostalCode());

			agentOwner.setRoadNameOrNo(reqOwner.getRoadNameOrNo());

			return agentOwner;
		}
		return null;
	}

	@Override
	public Agent mapSubAgent(ReqSubAgnet subAgent) {
		Agent agent = null;
		if (subAgent != null) {
			agent = new Agent();
			agent.setActive(false);
			agent.setApproveStatus(0);
			agent.setEmailVerified(true);
			agent.setPhoneVerified(true);
			agent.setSubAgent(true);

			agent.setPhoneCode(subAgent.getCode());
			agent.setPhoneNo(subAgent.getPhone());
			agent.setPrimaryEmail(subAgent.getEmail());
			agent.setApplicantName(subAgent.getName());
			agent.setPwd(subAgent.getPwd());

		}
		return agent;
	}

	@Override
	public List<ResSubAgent> getResSubAgents(List<Agent> subAagents) {

		List<ResSubAgent> agents = null;

		if (subAagents != null) {
			agents = new ArrayList<>();

			for (Agent agent : subAagents) {
				ResSubAgent resAgnet = mapResSubAgent(agent);

				if (resAgnet != null) {
					agents.add(resAgnet);
				}
			}

			return agents;
		}

		return agents;
	}

	@Override
	public ResSubAgent mapResSubAgent(Agent agent) {
		if (agent != null) {
			ResSubAgent resSubAgent = new ResSubAgent();
			resSubAgent.setId(agent.getPublicId());
			resSubAgent.setActive(agent.isActive());
			resSubAgent.setDate(agent.getDate());
			resSubAgent.setEmail(agent.getPrimaryEmail());
			resSubAgent.setName(agent.getApplicantName());
			resSubAgent.setPhone(agent.getPhoneNo());
			resSubAgent.setCode(agent.getPhoneCode());

			return resSubAgent;
		}
		return null;
	}



	private List<RespAgentOwner> mapRespActiveAgnetOwners(List<AgentOwner> agentOwners) {
		if (agentOwners != null) {
			List<RespAgentOwner> respAgentOwners = new ArrayList<>();
			for (AgentOwner agentOwner : agentOwners) {
				RespAgentOwner owner = mapRespActiveOwner(agentOwner);
				if (owner != null) {
					respAgentOwners.add(owner);
				}
			}

			return respAgentOwners;
		}
		return null;
	}

	private RespAgentOwner mapRespActiveOwner(AgentOwner agentOwner) {
		if (agentOwner != null) {

			if (agentOwner.isActive()) {

				RespAgentOwner owner = new RespAgentOwner();

				owner.setCode(agentOwner.getCode());
				owner.setCountry(agentOwner.getCountry());

				owner.setDistrict(agentOwner.getDistrict());

				owner.setEmail(agentOwner.getEmail());

				owner.setHouseNoOrVillage(agentOwner.getHouseNoOrVillage());

				owner.setRoadNameOrNo(agentOwner.getRoadNameOrNo());

				owner.setName(agentOwner.getName());
				owner.setNationalIdAttach(agentOwner.getNationalIdAttach());
				owner.setNationalIdNo(agentOwner.getNationalIdNo());

				owner.setOwnerImage(agentOwner.getOwnerImage());

				owner.setPassportAttach(agentOwner.getPassportAttach());
				owner.setPassportNo(agentOwner.getPassportNo());
				owner.setPhone(agentOwner.getPhone());
				owner.setPoliceStation(agentOwner.getPoliceStation());
				owner.setPostalCode(agentOwner.getPostalCode());
				owner.setPublicId(agentOwner.getPublicId());

				return owner;
			}

		}
		return null;
	}

	private RespAgentCompany mapRespAgentCompany(AgentCompany agentCompany) {
		if (agentCompany != null) {

			RespAgentCompany company = new RespAgentCompany();
			company.setBinAttach(agentCompany.getBinAttach());
			company.setBinCertificateNo(agentCompany.getBinCertificateNo());
			company.setCode(agentCompany.getCode());
			company.setCompanyLogoAttach(agentCompany.getCompanyLogoAttach());
			company.setCompanyName(agentCompany.getCompanyName());
			company.setCountry(agentCompany.getCountry());
			company.setDistrictOrCity(agentCompany.getDistrictOrCity());
			company.setEmail(agentCompany.getEmail());
			company.setHouseOrVillage(agentCompany.getHouseOrVillage());
			company.setPhone(agentCompany.getPhone());
			company.setPoliceStation(agentCompany.getPoliceStation());
			company.setPostalCode(agentCompany.getPostalCode());
			company.setRoadNameOrNo(agentCompany.getRoadNameOrNo());
			company.setTinAttach(agentCompany.getTinAttach());
			company.setTinCertificateNo(agentCompany.getTinCertificateNo());
			company.setTradeAttach(agentCompany.getTradeAttach());
			company.setTradeLiceseno(agentCompany.getTradeLiceseno());

			return company;
		}
		return null;
	}
}
