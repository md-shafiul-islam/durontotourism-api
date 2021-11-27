package com.usoit.api.enduser.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.usoit.api.model.Customer;
import com.usoit.api.model.MailVerifiedToken;
import com.usoit.api.model.Passport;
import com.usoit.api.model.PhoneVerificationToken;
import com.usoit.api.model.Traveler;
import com.usoit.api.model.request.ChangePhone;
import com.usoit.api.model.request.ReqMailChange;
import com.usoit.api.model.request.ReqProfileImage;
import com.usoit.api.model.response.RestPrfileInfo;


public interface CustomerServices extends UserDetailsService{

	public Customer getCustomerByUsername(String userName);

	public Customer getCustomerByPublicId(String userId);

	public Customer getCustomerByAuthUsername(String name);

	public boolean addCustomer(Customer nCustomer);

	public Customer getCustomerUsernameIsExist(String email);

	public boolean verifingCustomerPhone(int id);

	public boolean verifingCustomerMail(int id);

	public MailVerifiedToken updateCustomerVerificationMail(String publicId);

	public PhoneVerificationToken updateCustomerVerificationSMS(String publicId);

	public Traveler getCustomerInformation(int id);

	public boolean changeCustomerMail(Customer customer, ReqMailChange mailChange);

	public boolean changeCustomerPhone(Customer customer, ChangePhone changePhone);

	public Customer getCustomerPhoneIsExist(String phoneNo, String code);

	public boolean addOrUpdateProfileImage(Customer customer, ReqProfileImage image);

	

}
