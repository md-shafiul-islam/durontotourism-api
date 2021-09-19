package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.mapper.WalletWithDrawMapper;
import com.usoit.api.model.ShippingAddress;
import com.usoit.api.model.WalletWithDraw;
import com.usoit.api.model.WithDrawBankDetails;
import com.usoit.api.model.WithDrawMobilBanking;
import com.usoit.api.model.WithdrawType;
import com.usoit.api.model.request.ReqWalletWithdraw;
import com.usoit.api.model.request.RestWalletWithDraw;
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
	private ReceiveOptionServices receiveOptionServices;

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
				ShippingAddress address = new ShippingAddress();

				address.setDistrict(reqWithDraw.getReqShipingAddress().getDistrict());
				address.setPoliceStation(reqWithDraw.getReqShipingAddress().getPoliceStation());
				address.setRoadNo(reqWithDraw.getReqShipingAddress().getRoadNo());
				address.setVillage(reqWithDraw.getReqShipingAddress().getVillage());
				address.setWalletWithDarw(walletWithDraw);
				walletWithDraw.setShippingAddress(address);
			}

			if (reqWithDraw.getReqWWDBankDetails() != null) {
				WithDrawBankDetails bankDetails = new WithDrawBankDetails();
				bankDetails.setAccountName(reqWithDraw.getReqWWDBankDetails().getAccountName());
				bankDetails.setBankAccountNumber(reqWithDraw.getReqWWDBankDetails().getBankAccountNumber());
				bankDetails.setBankName(reqWithDraw.getReqWWDBankDetails().getBankName());
				bankDetails.setWalletWithDarw(walletWithDraw);
				walletWithDraw.setWithDrawBankDetails(bankDetails);
			}

			if (reqWithDraw.getReqMobileAccountDetails() != null) {

				WithDrawMobilBanking mBanking = new WithDrawMobilBanking();

				mBanking.setMobilBankPhoneNo(reqWithDraw.getReqMobileAccountDetails().getMobileBankName());
				mBanking.setMobileBankName(reqWithDraw.getReqMobileAccountDetails().getPhoneNo());
				mBanking.setWalletWithDarw(walletWithDraw);
				walletWithDraw.setWithDrawMobilBanking(mBanking);
			}

			return walletWithDraw;

		}
		return null;
	}
}
