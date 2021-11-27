package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.mapper.CountryMapper;
import com.usoit.api.mapper.CustomerMapper;
import com.usoit.api.model.Customer;
import com.usoit.api.model.Passport;
import com.usoit.api.model.ProfileImage;
import com.usoit.api.model.Traveler;
import com.usoit.api.model.Wallet;
import com.usoit.api.model.request.ReqCustomerSignUp;
import com.usoit.api.model.response.ResEsCustomer;
import com.usoit.api.model.response.RestEsCustomer;
import com.usoit.api.model.response.RestPrfileInfo;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.ProfileImageServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerMapperImpl implements CustomerMapper {

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private CountryServices countryServices;

	@Autowired
	private CountryMapper countryMapper;
	
	@Autowired
	private ProfileImageServices profileImageServices;

	@Override
	public RestEsCustomer mapEsRestCustomer(Customer customer) {

		if (customer != null) {

			RestEsCustomer restCustomer = new RestEsCustomer();

//			restCustomer.setEmail(customer.getPersonalEmail());
//			restCustomer.setFirstName(customer.getName());
//			restCustomer.setPhone(customer.getPersonalPhoneNumber());
			restCustomer.setPublicId(customer.getPublicId());

			if (customer.getWallet() != null) {
				restCustomer.setWalletAmount(helperServices.getDoubleToString(customer.getWallet().getTotalAmount()));
			}
			return restCustomer;
		}

		return null;
	}

	@Override
	public Customer mapCustomerSignUp(ReqCustomerSignUp customerSignUp) {

		if (customerSignUp != null) {
			Customer customer = new Customer();
			Traveler traveler = new Traveler();
			traveler.setCustomer(customer);
			traveler.setFirstName(customerSignUp.getFirstName());
			traveler.setLastName(customerSignUp.getLastName());
			traveler.setPrimary(true);
			List<Traveler> travelers = new ArrayList<>();
			travelers.add(traveler);
			customer.setTravelers(travelers);

			customer.setPrimaryEmail(customerSignUp.getEmail());
			customer.setPhoneCode(customerSignUp.getCode());
			customer.setPhoneNo(customerSignUp.getPhoneNo());
			customer.setPwd(customerSignUp.getPassword());

			return customer;
		}

		return null;
	}

	@Override
	public RestEsCustomer mapSignUpRestCustomer(Customer nCustomer) {

		if (nCustomer != null) {

			RestEsCustomer customer = new RestEsCustomer();

			customer.setEmail(nCustomer.getPrimaryEmail());
			customer.setPhone(nCustomer.getPhoneNo());
			customer.setPublicId(nCustomer.getPublicId());
			customer.setPhoneCode(nCustomer.getPhoneCode());
			customer.setFullName(nCustomer.getFullName());

			return customer;

		}

		return null;
	}

	@Override
	public ResEsCustomer getEsCustomer(Customer customer) {
		if (customer != null) {
			ResEsCustomer esCustomer = new ResEsCustomer();

			esCustomer.setEmail(customer.getPrimaryEmail());

			esCustomer.setFullName(customer.getFullName());
			esCustomer.setMailVrified(customer.isEmailVerified());
			esCustomer.setPhone(customer.getPhoneNo());
			esCustomer.setPhoneCode(customer.getPhoneCode());
			esCustomer.setPhoneVrified(customer.isPhoneVerified());
			esCustomer.setPublicId(customer.getPublicId());
			esCustomer.setUserType(customer.getClientType());
			esCustomer.setWalletAmount(getWalletAmount(customer.getWallet()));

			return esCustomer;
		}
		return null;
	}

	@Override
	public RestPrfileInfo getCustomerPersonalInfo(Customer customer, Traveler traveler, Passport passport) {

		if (customer != null) {
			RestPrfileInfo info = new RestPrfileInfo();
			info.setEmail(customer.getPrimaryEmail());
			info.setPhoneNo(customer.getPhoneCode() + " " + customer.getPhoneNo());
			info.setId(customer.getGenId());
			info.setPublicId(customer.getPublicId());
			info.setEmailVerified(customer.isEmailVerified());
			info.setPhoneVerified(customer.isPhoneVerified());
			info.setSince(customer.getDate());
			if (traveler != null) {

				info.setFirstName(traveler.getFirstName());
				info.setLastName(traveler.getLastName());
				info.setDateOfBirth(traveler.getDateOfBirth());
				info.setGender(traveler.getGender());
				info.setNationality(countryMapper.getRestCountry(countryServices.getCountryByISOCode(traveler.getNationality())));				

			}
			
			if(passport != null) {
				info.setPassportExpiry(passport.getExpiryDate());
				info.setPassportIssuingCountry(countryMapper.getRestCountry(countryServices.getCountryByISOCode(passport.getCountry())));
				info.setPassportNo(passport.getNumber());
				
			}
			
			ProfileImage profileImage = profileImageServices.getProfileImageByCustomer(customer);
			
			if(profileImage != null) {
				log.info("Profile Imabe URL, "+ profileImage.getUrl());
				info.setImageUrl(profileImage.getUrl());
			}
			
			log.info("ProfileImage, "+ profileImage);
			return info;
		}
		return null;
	}

	private String getWalletAmount(Wallet wallet) {
		if (wallet != null) {

			if (wallet.getTotalAmount() > 0) {
				return Double.toString(wallet.getTotalAmount());
			}
		}
		return "0.0";
	}

}
