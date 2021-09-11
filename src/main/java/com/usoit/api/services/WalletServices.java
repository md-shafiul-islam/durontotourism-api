package com.usoit.api.services;

import com.usoit.api.model.Wallet;

public interface WalletServices {

	public Wallet getWalletByCustomerId(int id);

	public void addWallet(Wallet wallet);

}
