package com.usoit.api.servicesimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.Address;
import com.usoit.api.data.model.ContactPerson;
import com.usoit.api.data.model.PaymentInfo;
import com.usoit.api.data.model.Vendor;
import com.usoit.api.data.vo.RestAddress;
import com.usoit.api.data.vo.RestContactPerson;
import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.data.vo.RestVendorCategory;
import com.usoit.api.data.vo.RestVendorUserId;
import com.usoit.api.services.VendorMapper;

@Service
public class VendorMapperImpl implements VendorMapper{

	
	@Override
	public List<RestVendorUserId> getRestVendorsUID(List<Vendor> vendors) {
		
		if (vendors != null) {
			
			List<RestVendorUserId> list = new ArrayList<>();
			
			for (Vendor vendor : vendors) {
				
				RestVendorUserId restVendorUserId = getRestVendorUId(vendor);
				
				list.add(restVendorUserId);
			}
			
			return list;
		}
		
		return null;
	}

	private RestVendorUserId getRestVendorUId(Vendor vendor) {
		
		if (vendor != null) {
			
			RestVendorUserId restVendorUser = new RestVendorUserId();
			
			restVendorUser.setCompanyName(vendor.getCompanyName());
			restVendorUser.setEmail(vendor.getEmail());
			restVendorUser.setOwnerName(vendor.getOwnerName());
			restVendorUser.setPublicId(vendor.getPublicId());
			restVendorUser.setVGenId(vendor.getVGenId());
			restVendorUser.setWebsite(vendor.getWebsite());
			
			if (vendor.getAddresses() != null) {
				
				List<RestAddress> restAddresses = getVendorRestAddress(vendor);
				
				restVendorUser.setAddresses(restAddresses);
			}
			
			if (vendor.getContactPersons() != null) {
				
				List<RestContactPerson> restContactPersons = getRestContactPersons(vendor);
				
				restVendorUser.setContactPersons(restContactPersons);
				
			}
			
			System.out.println("Befor User Vendor Rest Mapping");
			if (vendor.getUser() != null) {
				
				restVendorUser.setUserId(vendor.getUser().getUserGemId());
				restVendorUser.setUserPublicId(vendor.getUser().getPublicId());
			}
			System.out.println("After User Vendor Rest Mapping");
			
			if (vendor.getVendorCategory() != null) {
				
				restVendorUser.setRestVendorCategory(DozerMapper.parseObject(vendor.getVendorCategory(), RestVendorCategory.class));
			}else {
				//restVendorUser.setRestVendorCategory(new RestVendorCategory());
			}
			
			return restVendorUser;
			
		}
		
		return null;
	}

	private List<RestContactPerson> getRestContactPersons(Vendor vendor) {
		List<RestContactPerson> restContactPersons = new ArrayList<>();
		
		for (ContactPerson contPerson : vendor.getContactPersons()) {
			
			RestContactPerson restPerson = new RestContactPerson();
			
			restPerson.setEmail(contPerson.getEmail());
			restPerson.setName(contPerson.getName());
			restPerson.setPhoneNo(contPerson.getPhoneNo());
			restPerson.setPhoneNo2(contPerson.getPhoneNo2());
			restPerson.setPublicId(contPerson.getPublicId());
			
			if (contPerson.getCountry1() != null) {
				
				restPerson.setCountry1(DozerMapper.parseObject(contPerson.getCountry1(), RestCountry.class));
			}
			
			if (contPerson.getCountry2() != null) {
				restPerson.setCountry2(DozerMapper.parseObject(contPerson.getCountry2(), RestCountry.class));
			}
			
			restContactPersons.add(restPerson);
			
		}
		return restContactPersons;
	}

	public List<RestAddress> getVendorRestAddress(Vendor vendor) {
		List<RestAddress> restAddresses = new ArrayList<RestAddress>();
		
		for (Address address : vendor.getAddresses()) {
			
			RestAddress restAddress = new RestAddress();
			
			restAddress.setCity(address.getCity());
			restAddress.setCountryCode(address.getCountryCode());
			restAddress.setHouse(address.getHouse());
			restAddress.setStreet(address.getStreet());
			restAddress.setVillage(address.getVillage());
			restAddress.setZipCode(address.getZipCode());
			
			if (address.getCountry() != null) {
				
				RestCountry country = DozerMapper.parseObject(address.getCountry(), RestCountry.class);
				
				restAddress.setCountry(country);
			}
			
			restAddresses.add(restAddress);
			
		}
		return restAddresses;
	}
}
