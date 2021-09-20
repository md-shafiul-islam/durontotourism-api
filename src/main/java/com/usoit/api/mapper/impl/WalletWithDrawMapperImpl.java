package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.data.converter.UserMapper;
import com.usoit.api.mapper.CustomerMapper;
import com.usoit.api.mapper.PaymentStatusMapper;
import com.usoit.api.mapper.WalletWithDrawMapper;
import com.usoit.api.model.ReceiveOption;
import com.usoit.api.model.ShippingAddress;
import com.usoit.api.model.WalletWithDraw;
import com.usoit.api.model.WithDrawBankDetails;
import com.usoit.api.model.WithDrawMobilBanking;
import com.usoit.api.model.WithdrawType;
import com.usoit.api.model.request.ReqWalletWithdraw;
import com.usoit.api.model.request.RestWalletWithDraw;
import com.usoit.api.model.response.RestEsCustomer;
import com.usoit.api.model.response.RestPaymentStatus;
import com.usoit.api.model.response.RestReceiveOption;
import com.usoit.api.model.response.RestShippingAddress;
import com.usoit.api.model.response.RestWithDrawBankDetails;
import com.usoit.api.model.response.RestWithDrawMobilBanking;
import com.usoit.api.model.response.RestWithdrawType;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.ReceiveOptionServices;
import com.usoit.api.services.UserServices;
import com.usoit.api.services.WithDarawalTypeServices;

@Service
public class WalletWithDrawMapperImpl implements WalletWithDrawMapper {

	@Autowired
	private WithDarawalTypeServices withDarawalTypeServices;

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private UserServices userServices;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ReceiveOptionServices receiveOptionServices;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private PaymentStatusMapper paymentStatusMapper;

//	private 

	@Override
	public List<RestWalletWithDraw> mapRestWalletWithDraws(List<WalletWithDraw> allWalletWithDraws) {

		if (allWalletWithDraws != null) {

			List<RestWalletWithDraw> draws = new ArrayList<>();

			for (WalletWithDraw withDraw : allWalletWithDraws) {
				RestWalletWithDraw draw = mapRestWalletWithDarw(withDraw);
				draws.add(draw);
			}

			return draws;
		}

		return null;
	}

	@Override
	public RestWalletWithDraw mapRestWalletWithDarw(WalletWithDraw withDraw) {
		
		if(withDraw != null) {
			
			RestWalletWithDraw draw = new RestWalletWithDraw();
			
			draw.setAmount(withDraw.getAmount());
			draw.setApproveDate(withDraw.getApproveDate());
			draw.setApproveNote(withDraw.getApproveNote());
			draw.setApproveUser(userMapper.mapEsUser(withDraw.getApproveUser()));
			draw.setChequeName(withDraw.getChequeName());
			draw.setCustomer(customerMapper.mapEsRestCustomer(withDraw.getCustomer()));
			draw.setDate(withDraw.getDate());
			draw.setDateGroup(withDraw.getDateGroup());
			draw.setGenId(withDraw.getGenId());
			draw.setPaymentStatus(paymentStatusMapper.mapRestPaymentStatusOnly(withDraw.getPaymentStatus()));
			draw.setPublicId(withDraw.getPublicId());
			draw.setReceiveOption(mapReceiveOption(withDraw.getReceiveOption()));
			draw.setShippingAddress(mappShippingAddress(withDraw.getShippingAddress()));
			draw.setStatus(withDraw.getStatus());
			draw.setTransctionId(withDraw.getTransctionId());
			draw.setTransStatus(withDraw.isTransStatus());
			draw.setWalletStaus(withDraw.isWalletStaus());
			draw.setWithDrawBankDetails(mapWllateWithdrawBankDetailsOnly(withDraw.getWithDrawBankDetails()));
			draw.setWithDrawMobilBanking(mapWithdrawMobilBankOnly(withDraw.getWithDrawMobilBanking()));
			draw.setWithdrawType(mapWithdrawTypeOnly(withDraw.getWithdrawType()));
			
			return draw;
		}
		
		return null;
	}

	@Override
	public RestWalletWithDraw mapRestWalletWithDarwOnly(WalletWithDraw withDraw) {
		
		if(withDraw != null) {
			RestWalletWithDraw draw = new RestWalletWithDraw();
			
			draw.setAmount(withDraw.getAmount());
			draw.setApproveDate(withDraw.getApproveDate());
			draw.setApproveNote(withDraw.getApproveNote());
			draw.setApproveUser(userMapper.mapEsUser(withDraw.getApproveUser()));
			draw.setChequeName(withDraw.getChequeName());
			draw.setCustomer(customerMapper.mapEsRestCustomer(withDraw.getCustomer()));
			draw.setDate(withDraw.getDate());
			draw.setDateGroup(withDraw.getDateGroup());
			draw.setGenId(withDraw.getGenId());
			draw.setPaymentStatus(paymentStatusMapper.mapRestPaymentStatusOnly(withDraw.getPaymentStatus()));
			draw.setPublicId(withDraw.getPublicId());
			draw.setReceiveOption(mapReceiveOption(withDraw.getReceiveOption()));
			draw.setShippingAddress(mappShippingAddress(withDraw.getShippingAddress()));
			draw.setStatus(withDraw.getStatus());
			draw.setTransctionId(withDraw.getTransctionId());
			draw.setTransStatus(withDraw.isTransStatus());
			draw.setWalletStaus(withDraw.isWalletStaus());
			draw.setWithDrawBankDetails(mapWllateWithdrawBankDetailsOnly(withDraw.getWithDrawBankDetails()));
			draw.setWithDrawMobilBanking(mapWithdrawMobilBankOnly(withDraw.getWithDrawMobilBanking()));
			draw.setWithdrawType(mapWithdrawTypeOnly(withDraw.getWithdrawType()));
			
			return draw;
		}
		
		return null;
	}
	
	private RestWithdrawType mapWithdrawTypeOnly(WithdrawType withdrawType) {
		
		if(withdrawType != null) {
			RestWithdrawType type = new RestWithdrawType();
			
			type.setId(withdrawType.getId());
			type.setName(withdrawType.getName());
			type.setValue(withdrawType.getValue());
			
			return type;
		}
		
		return null;
	}

	private RestWithDrawMobilBanking mapWithdrawMobilBankOnly(WithDrawMobilBanking withDrawMobilBanking) {
		
		if(withDrawMobilBanking != null) {
			
			RestWithDrawMobilBanking banking = new RestWithDrawMobilBanking();
			
			banking.setId(withDrawMobilBanking.getId());
			banking.setMobilBankPhoneNo(withDrawMobilBanking.getMobilBankPhoneNo());
			banking.setMobileBankName(withDrawMobilBanking.getMobileBankName());
			
			return banking;
		}
		
		return null;
	}

	private RestWithDrawBankDetails mapWllateWithdrawBankDetailsOnly(WithDrawBankDetails withDrawBankDetails) {
		
		if(withDrawBankDetails != null) {
			
			RestWithDrawBankDetails bankDetails = new RestWithDrawBankDetails();
			
			bankDetails.setAccountName(withDrawBankDetails.getAccountName());
			bankDetails.setBankAccountNumber(withDrawBankDetails.getBankAccountNumber());
			bankDetails.setBankName(withDrawBankDetails.getBankName());
			bankDetails.setDate(withDrawBankDetails.getDate());
			bankDetails.setId(withDrawBankDetails.getId());
			
			return bankDetails;
		}
		return null;
	}

	private RestShippingAddress mappShippingAddress(ShippingAddress shippingAddress) {
		
		if(shippingAddress != null) {
			
			RestShippingAddress address = new RestShippingAddress();
			
			address.setDistrict(shippingAddress.getDistrict());
			address.setId(shippingAddress.getId());
			address.setPoliceStation(shippingAddress.getPoliceStation());
			address.setRoadNo(shippingAddress.getRoadNo());
			address.setVillage(shippingAddress.getVillage());
			
			return address;
		}
		
		return null;
	}

	private RestReceiveOption mapReceiveOption(ReceiveOption receiveOption) {
		if(receiveOption != null) {
			RestReceiveOption option = new RestReceiveOption();
			
			option.setId(receiveOption.getId());
			option.setName(receiveOption.getName());
			option.setValue(receiveOption.getValue());
			
			return option;
		}
		return null;
	}

	@Override
	public WalletWithDraw mapReqWalletWithDraw(ReqWalletWithdraw reqWithDraw) {

		System.out.println("Wallet With Draw Mapper Map Wallet Withdraw");
		if (reqWithDraw != null) {

			WithdrawType withdrawType = withDarawalTypeServices.getWithDrawTypeByValue(reqWithDraw.getWithdrawType());
			WalletWithDraw walletWithDraw = new WalletWithDraw();

			walletWithDraw.setAmount(helperServices.stringToDouble(reqWithDraw.getAmount()));

			walletWithDraw.setWithdrawType(withdrawType);

			walletWithDraw
					.setReceiveOption(receiveOptionServices.getReceiveOptionByValue(reqWithDraw.getReceiveOption()));
			walletWithDraw.setChequeName(reqWithDraw.getChequeName());

			if (reqWithDraw.getReqShipingAddress() != null) {
				mapReqShipingAddress(reqWithDraw, walletWithDraw);
			}

			if (reqWithDraw.getReqWWDBankDetails() != null) {
				mapReqWalletWithDrawBankDetails(reqWithDraw, walletWithDraw);
				
			}

			if (reqWithDraw.getReqMobileAccountDetails() != null) {

				mapReqWalletWithDrawMobileBankDetails(reqWithDraw, walletWithDraw);
			}

			return walletWithDraw;

		}
		return null;
	}

	private void mapReqWalletWithDrawMobileBankDetails(ReqWalletWithdraw reqWithDraw, WalletWithDraw walletWithDraw) {
		WithDrawMobilBanking mBanking = new WithDrawMobilBanking();

		mBanking.setMobilBankPhoneNo(reqWithDraw.getReqMobileAccountDetails().getMobileBankName());
		mBanking.setMobileBankName(reqWithDraw.getReqMobileAccountDetails().getPhoneNo());
		mBanking.setWalletWithDarw(walletWithDraw);
		walletWithDraw.setWithDrawMobilBanking(mBanking);
	}

	private void mapReqWalletWithDrawBankDetails(ReqWalletWithdraw reqWithDraw, WalletWithDraw walletWithDraw) {
		WithDrawBankDetails bankDetails = new WithDrawBankDetails();
		bankDetails.setAccountName(reqWithDraw.getReqWWDBankDetails().getAccountName());
		bankDetails.setBankAccountNumber(reqWithDraw.getReqWWDBankDetails().getBankAccountNumber());
		bankDetails.setBankName(reqWithDraw.getReqWWDBankDetails().getBankName());
		bankDetails.setWalletWithDarw(walletWithDraw);
		bankDetails.setBranchName(reqWithDraw.getReqWWDBankDetails().getBranchName());
		walletWithDraw.setWithDrawBankDetails(bankDetails);
	}

	private void mapReqShipingAddress(ReqWalletWithdraw reqWithDraw, WalletWithDraw walletWithDraw) {
		ShippingAddress address = new ShippingAddress();

		address.setDistrict(reqWithDraw.getReqShipingAddress().getDistrict());
		address.setPoliceStation(reqWithDraw.getReqShipingAddress().getPoliceStation());
		address.setRoadNo(reqWithDraw.getReqShipingAddress().getRoadNo());
		address.setVillage(reqWithDraw.getReqShipingAddress().getVillage());
		address.setWalletWithDarw(walletWithDraw);
		walletWithDraw.setShippingAddress(address);
	}
}
