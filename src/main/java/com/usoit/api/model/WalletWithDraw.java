package com.usoit.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="wallet_withdraw")
public class WalletWithDraw {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name="punlic_id")
	private String publicId;
	
	@Column(name="gen_id")
	private String genId; //DT_WIW_BD_20210912_IB_88556677
	
	@ManyToOne
	@JoinColumn(name="with_darw_type")
	private WithdrawType withdrawType;
	
	@Column(name="bank_mobile_transaction_id")
	private String transctionId; //Bank Or Mobile Banking Transaction ID
	
	@ManyToOne
	@JoinColumn(name="approve_user", referencedColumnName = "id")
	private User approveUser;
	
	@ManyToOne
	@JoinColumn(name = "customer", referencedColumnName = "id")
	private Customer customer;
	
	private double amount;
	
	@Column(name="cheque_name")
	private String chequeName;	
	
	@OneToOne	
	@JoinColumn(name="shipping_address")
	private ShippingAddress shippingAddress;
	
	@ManyToOne
	@JoinColumn(name="receive_option", referencedColumnName = "id")
	private ReceiveOption receiveOption;
	
	@OneToOne(mappedBy = "walletWithDarw")
	private WithDrawBankDetails withDrawBankDetails;
	
	@OneToOne(mappedBy = "walletWithDarw")
	private WithDrawMobilBanking withDrawMobilBanking; 
	
	@ManyToOne
	@JoinColumn(name = "payment_status")
	private PaymentStatus paymentStatus;
	
	@Column(name="approve_note")
	private String approveNote;
	
	private int status; //0 pending, 1 approve, 2 reject
	
	@Column(name="wallet_status")
	private boolean walletStaus;
	
	@Column(name="trans_status")
	private boolean transStatus; // 0/1
		
	private Date date;
	
	@Column(name="date_group")
	private Date dateGroup;

}
