package com.usoit.api.mapper;

import java.util.List;

import com.usoit.api.model.WalletWithDraw;
import com.usoit.api.model.request.ReqWalletWithdraw;
import com.usoit.api.model.request.RestWalletWithDraw;

public interface WalletWithDrawMapper {

	public List<RestWalletWithDraw> mapRestWalletWithDraws(List<WalletWithDraw> allWalletWithDraws);
	

	public RestWalletWithDraw mapRestWalletWithDarw(WalletWithDraw withDraw);

	public WalletWithDraw mapReqWalletWithDraw(ReqWalletWithdraw withDraw);


	public RestWalletWithDraw mapRestWalletWithDarwOnly(WalletWithDraw withDraw);

}
