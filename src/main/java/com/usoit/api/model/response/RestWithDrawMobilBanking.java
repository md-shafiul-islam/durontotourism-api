package com.usoit.api.model.response;

import com.usoit.api.model.WalletWithDraw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestWithDrawMobilBanking {

	private int id;

	private WalletWithDraw walletWithDarw;

	private String mobileBankName;

	private String mobilBankPhoneNo;
}
