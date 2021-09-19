package com.usoit.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_withdraw")
public class BankWithDraw {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="public_id")
	private String publicId;
	
	@Column(name="gen_id")
	private String genId; //
	
	@Column(name="bank_withdraw_note")
	private String bankWithDrawNote;
	
	@ManyToOne
	@JoinColumn(name = "bank_account", referencedColumnName = "id")
	private BankAccount account;
	
	@ManyToOne
	@JoinColumn(name="create_user", referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="approve_user", referencedColumnName = "id")
	private User approveUser;
	
	private double amount;
	
	@Column(name="cheque_number")
	private String chequeNumber;
	
	@Column(name="online_transid")
	private String onlineTransId;
	
	@Column(name="attach_url")
	private String attachUrl;
	
	@Column(name="with_draw_name")
	private String withDarwByName;
	
	@ManyToOne
	@JoinColumn(name = "payment_status", referencedColumnName = "id")
	private PaymentStatus paymentStatus;
	
	private int status; // 0, 1, 2
	
	@Column(name="approve_status")
	private int approveStatus;
	
	@Column(name="reject_status")
	private int rejectStatus;
	
	@Column(name="action_status")
	private boolean actionStatus;
	
	private Date date;
	
	@Column(name="date_group")
	private Date dateGroup;

}
