package com.usoit.api.mapper;

import java.util.List;

import com.usoit.api.model.BankWithDraw;
import com.usoit.api.model.request.ReqBankWithdraw;
import com.usoit.api.model.response.RestBankWithDraw;

public interface BankWithDrawMapper {

	public RestBankWithDraw mapRestBankWithDarw(BankWithDraw bankWithDarw);

	public List<RestBankWithDraw> mapRestBankWithDarws(List<BankWithDraw> allBankWithDarw);

	public BankWithDraw mapBankWithDraw(ReqBankWithdraw bankWithDraw);

}
