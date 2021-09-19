package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.User;
import com.usoit.api.model.WalletWithDraw;
import com.usoit.api.model.request.ReqWalletApprove;

public interface WalletWithDrawServeice {

	public List<WalletWithDraw> getAllWalletWithDraws();	

	public WalletWithDraw getWalletWithByPublicId(String publicId);

	public WalletWithDraw approveWalletWithDraw(ReqWalletApprove withDrawApprove, User user);

	public boolean addWalletWithdarwViaClient(WalletWithDraw walletWithDraw, User user);

}
