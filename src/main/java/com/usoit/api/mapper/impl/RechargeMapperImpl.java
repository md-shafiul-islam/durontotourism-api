package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.mapper.BankMapper;
import com.usoit.api.mapper.RechargeMapper;
import com.usoit.api.mapper.StatusMapper;
import com.usoit.api.model.BankAccount;
import com.usoit.api.model.Recharge;
import com.usoit.api.model.request.ReqRecharge;
import com.usoit.api.model.response.RestRecharge;
import com.usoit.api.services.BankServices;
import com.usoit.api.services.HelperServices;

@Service
public class RechargeMapperImpl implements RechargeMapper{
	
	@Autowired
	private HelperServices helperServices;
	
	@Autowired
	private BankServices bankServices;
	
	@Autowired
	private BankMapper bankMapper;
	
	@Autowired
	private StatusMapper statusMapper;

	@Override
	public Recharge mappRecharge(ReqRecharge reqRecharge) {
		
		System.out.println("Req Recharge Mapper Run");
		
		if(reqRecharge != null) {
			
			Recharge recharge = new Recharge();
			
			recharge.setAmount(helperServices.stringToDouble(reqRecharge.getAmount()));
			
			if(reqRecharge.getAccountId() != null) {
				
				BankAccount bankAccount = bankServices.getBankAccountByPublicID(reqRecharge.getAccountId());
				
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
			restRecharge.setBankAccount(bankMapper.mapRechargBankAccount(recharge.getBankAccount()));
			
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
			restRecharge.setBankAccount(bankMapper.mapOnlyBankAccount(recharge.getBankAccount()));
			
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
}
