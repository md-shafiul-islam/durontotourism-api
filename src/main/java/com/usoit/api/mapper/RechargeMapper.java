package com.usoit.api.mapper;

import com.usoit.api.model.Recharge;
import com.usoit.api.model.request.ReqRecharge;
import com.usoit.api.model.response.RestRecharge;

public interface RechargeMapper {

	public Recharge mappRecharge(ReqRecharge reqRecharge);

	public RestRecharge mapRestRecharge(Recharge recharge);

}
