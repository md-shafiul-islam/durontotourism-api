package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.mapper.BankAccountMapper;
import com.usoit.api.mapper.CustomerMapper;
import com.usoit.api.mapper.RechargeMapper;
import com.usoit.api.mapper.StatusMapper;
import com.usoit.api.model.BankAccount;
import com.usoit.api.model.Recharge;
import com.usoit.api.model.request.ReqRecharge;
import com.usoit.api.model.response.RestRecharge;
import com.usoit.api.services.BankAccountServices;
import com.usoit.api.services.HelperServices;

@Service
public class RechargeMapperImpl implements RechargeMapper{
	
	@Autowired
	private HelperServices helperServices;
	
	@Autowired
	private BankAccountServices bankAccountServices;
	
	@Autowired
	private BankAccountMapper bankAccountMapper;
	
	@Autowired
	private StatusMapper statusMapper;
	
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public Recharge mappRecharge(ReqRecharge reqRecharge) {
		
		System.out.println("Req Recharge Mapper Run");
		
		if(reqRecharge != null) {
			
			Recharge recharge = new Recharge();
			
			recharge.setAmount(helperServices.stringToDouble(reqRecharge.getAmount()));
			
			if(reqRecharge.getAccountId() != null) {
				
				BankAccount bankAccount = bankAccountServices.getBankAccountByPublicID(reqRecharge.getAccountId());
				
				recharge.setBankAccount(bankAccount);
			}
			
			recharge.setRefferenceNote(reqRecharge.getRefferenceNote());
			recharge.setTransectionDate(reqRecharge.getTransectionDate());
			recharge.setTransectionId(reqRecharge.getTransectionId());
			recharge.setTransType(reqRecharge.getTransType());
			recharge.setAttachUrl(reqRecharge.getAttach());
			
			return recharge;
		}
		
		return null;
	}
	
	@Override
	public RestRecharge mapRestRecharge(Recharge recharge) {
		
		if(recharge != null) {
			
			RestRecharge restRecharge = new RestRecharge();
			
			restRecharge.setAmount(recharge.getAmount());
			restRecharge.setBankAccount(bankAccountMapper.mapRechargBankAccount(recharge.getBankAccount()));
			
			restRecharge.setDate(recharge.getDate());
			restRecharge.setGenId(recharge.getGenId());
			restRecharge.setPaymentStatus(statusMapper.mapRestPaymentStatus(recharge.getPaymentStatus()));
			restRecharge.setPublicId(recharge.getPublicId());
			restRecharge.setRefferenceNote(recharge.getRefferenceNote());
			restRecharge.setRejectedNote(recharge.getRejectedNote());
			restRecharge.setRejected(recharge.isRejected());
			restRecharge.setTransectionDate(recharge.getTransectionDate());
			restRecharge.setTransectionId(recharge.getTransectionId());
			restRecharge.setApproveStatus(recharge.getApproveStatus());
			return restRecharge;
		}
		
		return null;
	}
	
	@Override
	public RestRecharge mapRestRechargeViaAdd(Recharge recharge) {
		
		if(recharge != null) {
			
			RestRecharge restRecharge = new RestRecharge();
			
			restRecharge.setAmount(recharge.getAmount());
			restRecharge.setBankAccount(bankAccountMapper.mapOnlyBankAccount(recharge.getBankAccount()));
			
			restRecharge.setDate(recharge.getDate());
			restRecharge.setGenId(recharge.getGenId());
			restRecharge.setPaymentStatus(statusMapper.mapRestPaymentStatus(recharge.getPaymentStatus()));
			restRecharge.setPublicId(recharge.getPublicId());
			restRecharge.setRefferenceNote(recharge.getRefferenceNote());
			restRecharge.setRejectedNote(recharge.getRejectedNote());
			restRecharge.setRejected(recharge.isRejected());
			restRecharge.setTransectionDate(recharge.getTransectionDate());
			restRecharge.setTransectionId(recharge.getTransectionId());
			restRecharge.setApproveStatus(recharge.getApproveStatus());
			return restRecharge;
		}

		return null;
	}
	
	@Override
	public List<RestRecharge> mapRestRecharges(List<Recharge> recharges) {
		
		if(recharges != null) {
			
			List<RestRecharge> restRecharges = new ArrayList<>();
			
			for (Recharge recharge : recharges) {
				RestRecharge restRecharge = mapRestRecharge(recharge);
				
				if(restRecharge != null) {
					recharges.add(recharge);
				}
			}
			
			return restRecharges;
		}
		
		return null;
	}
	
	@Override
	public List<RestRecharge> mapRestRechargesOnly(List<Recharge> recharges) {
		
		if(recharges != null) {
			
			List<RestRecharge> restRecharges = new ArrayList<>();
			
			for (Recharge recharge : recharges) {
				
				RestRecharge restRecharge = mapRestRechargeOnly(recharge);
				
				if(restRecharge != null) {
					restRecharges.add(restRecharge);
				}
			}
			
			return restRecharges;
		}
		
		return null;
	}

	@Override
	public RestRecharge mapRestRechargeOnly(Recharge recharge) {
		
		if(recharge != null) {
			
			RestRecharge restRecharge = new RestRecharge();
			
			if(recharge.getBankAccount() == null) {
				return null;
			}
			
			restRecharge.setBankAccount(bankAccountMapper.mapOnlyBankAccount(recharge.getBankAccount()));
			restRecharge.setAmount(recharge.getAmount());
			restRecharge.setApproveStatus(recharge.getApproveStatus());
			restRecharge.setDate(recharge.getDate());
			restRecharge.setGenId(recharge.getGenId());
			restRecharge.setPaymentStatus(statusMapper.mapRestPaymentStatus(recharge.getPaymentStatus()));
			
			restRecharge.setPublicId(recharge.getPublicId());
			restRecharge.setRefferenceNote(recharge.getRefferenceNote());
			restRecharge.setRejected(recharge.isRejected());
			restRecharge.setRejectedNote(recharge.getRejectedNote());
			restRecharge.setTransectionDate(recharge.getTransectionDate());
			restRecharge.setTransectionId(recharge.getTransectionId());
			restRecharge.setAttachUrl(recharge.getAttachUrl());
			restRecharge.setCustomer(customerMapper.mapEsRestCustomer(recharge.getCustomer()));
						
			return restRecharge;
		}
		
		return null;
	}
}
