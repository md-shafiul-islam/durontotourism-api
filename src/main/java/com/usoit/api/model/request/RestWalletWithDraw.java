package com.usoit.api.model.request;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.usoit.api.model.Customer;
import com.usoit.api.model.PaymentStatus;
import com.usoit.api.model.ReceiveOption;
import com.usoit.api.model.ShippingAddress;
import com.usoit.api.model.User;
import com.usoit.api.model.WithDrawBankDetails;
import com.usoit.api.model.WithDrawMobilBanking;
import com.usoit.api.model.WithdrawType;
import com.usoit.api.model.response.RestEsCustomer;
import com.usoit.api.model.response.RestEsUser;
import com.usoit.api.model.response.RestPaymentStatus;
import com.usoit.api.model.response.RestReceiveOption;
import com.usoit.api.model.response.RestShippingAddress;
import com.usoit.api.model.response.RestWithDrawBankDetails;
import com.usoit.api.model.response.RestWithDrawMobilBanking;
import com.usoit.api.model.response.RestWithdrawType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestWalletWithDraw {
	

	private String publicId;
	
	private String genId; //DT_WIW_BD_20210912_IB_88556677
	
	private RestWithdrawType withdrawType;
	
	private String transctionId; //Bank Or Mobile Banking Transaction ID
	
	private RestEsUser approveUser;
	
	private RestEsCustomer customer;
	
	private double amount;
	
	private String chequeName;	
	
	private RestShippingAddress shippingAddress;
	
	private RestReceiveOption receiveOption;
	
	private RestWithDrawBankDetails withDrawBankDetails;
	
	private RestWithDrawMobilBanking withDrawMobilBanking; 
	
	private RestPaymentStatus paymentStatus;
	
	private String approveNote;
	
	private int status; //0 pending, 1 approve, 2 reject
	
	private boolean walletStaus;
	
	private boolean transStatus; // 0/1
		
	private Date date;
	
	private Date approveDate;
	
	private Date dateGroup;
	
}
