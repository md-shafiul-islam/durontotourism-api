package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.WithdrawType;

public interface WithDarawalTypeServices {

	public WithdrawType getWithDrawTypeById(int id);

	public WithdrawType getWithDrawTypeByValue(String withdrawType);
	
	public List<WithdrawType> getAllWithDrawType();

}
