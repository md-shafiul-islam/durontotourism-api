package com.usoit.api.model.response;

import java.util.List;

import com.usoit.api.model.WalletWithDraw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestReceiveOption {

	private int id;

	private List<WalletWithDraw> walletWithDraws;

	private String name;

	private String value;
}
