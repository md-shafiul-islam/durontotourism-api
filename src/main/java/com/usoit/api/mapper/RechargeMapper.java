package com.usoit.api.mapper;

import java.util.List;

import com.usoit.api.model.Recharge;
import com.usoit.api.model.request.ReqRecharge;
import com.usoit.api.model.response.RestRecharge;

public interface RechargeMapper {

	public Recharge mappRecharge(ReqRecharge reqRecharge);

	public RestRecharge mapRestRecharge(Recharge recharge);

	public List<RestRecharge> mapRestRecharges(List<Recharge> recharges);

	public RestRecharge mapRestRechargeViaAdd(Recharge recharge);

}
