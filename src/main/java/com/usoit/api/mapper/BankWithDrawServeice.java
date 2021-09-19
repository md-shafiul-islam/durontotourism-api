package com.usoit.api.mapper;

import java.util.List;

import com.usoit.api.model.BankWithDraw;

public interface BankWithDrawServeice {

	public List<BankWithDraw> getAllBankWithDarw();

	public boolean addBankWithdarwViaWallet(BankWithDraw bankWithDraw);

}
