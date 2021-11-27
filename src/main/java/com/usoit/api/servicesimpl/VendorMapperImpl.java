package com.usoit.api.servicesimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.vo.RestAddress;
import com.usoit.api.data.vo.RestContactPerson;
import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.data.vo.RestPaymentInfo;
import com.usoit.api.data.vo.RestVendorCategory;
import com.usoit.api.data.vo.RestVendorDetails;
import com.usoit.api.data.vo.RestVendorUserId;
import com.usoit.api.model.Address;
import com.usoit.api.model.ContactPerson;
import com.usoit.api.model.Country;
import com.usoit.api.model.Department;
import com.usoit.api.model.PaymentInfo;
import com.usoit.api.model.TempContactPerson;
import com.usoit.api.model.TempPaymentInfo;
import com.usoit.api.model.TempVAddress;
import com.usoit.api.model.TempVendor;
import com.usoit.api.model.Vendor;
import com.usoit.api.model.VendorCategory;
import com.usoit.api.model.request.ReqAddress;
import com.usoit.api.model.request.ReqContactPerson;
import com.usoit.api.model.request.ReqPaymentInfo;
import com.usoit.api.model.request.ReqVendor;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.VendorCategoryServices;
import com.usoit.api.services.VendorMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VendorMapperImpl implements VendorMapper {

	@Autowired
	private CountryServices countryServices;

	@Autowired
	private VendorCategoryServices vendorCategoryServices;
		

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

	@Override
	public Vendor getVendor(ReqVendor reqVendor) {

		if (reqVendor != null) {

			Vendor vendor = new Vendor();

			// Address Start
			if (reqVendor.getAddresses() != null) {

				getAddressMapping(reqVendor, vendor);

			} // Address end

			vendor.setCompanyName(reqVendor.getCompanyName());
			vendor.setComPhoneNo(reqVendor.getComPhoneNo());
			vendor.setEmail(reqVendor.getEmail());
			vendor.setOwnerName(reqVendor.getOwnerName());
			vendor.setPhoneCode(reqVendor.getPhoneCode());

			vendor.setWebsite(reqVendor.getWebsite());

			if (reqVendor.getVendorCategory() > 0) {

				vendor.setVendorCategory(vendorCategoryServices.getVendorCatById(reqVendor.getVendorCategory()));
			}

			if (reqVendor.getContactPersons() != null) {

				getContactPersonMapping(reqVendor, vendor);
			}

			if (reqVendor.getPaymentInfos() != null) {

				getPayInfMapping(reqVendor, vendor);
			}

			return vendor;

		}

		return null;
	}

	@Override
	public ReqVendor getReqVendor(Vendor vendor) {

		if (vendor != null) {

			ReqVendor reqVendor = new ReqVendor();

			if (vendor.getAddresses() != null) {

				getAddressToReqAddress(vendor, reqVendor);

			}
			
			reqVendor.setPublicId(vendor.getPublicId());
			reqVendor.setCompanyName(vendor.getCompanyName());
			reqVendor.setComPhoneNo(vendor.getComPhoneNo());
			reqVendor.setEmail(vendor.getEmail());
			reqVendor.setOwnerName(vendor.getOwnerName());
			reqVendor.setPhoneCode(vendor.getPhoneCode());

			if (vendor.getVendorCategory() != null) {
				reqVendor.setVendorCategory(vendor.getVendorCategory().getId());
			}

			reqVendor.setWebsite(vendor.getWebsite());

			if (vendor.getContactPersons() != null) {

				getReqContactPerson(vendor, reqVendor);
			}
			
			if (vendor.getPaymentInfos() != null) {
				
				List<ReqPaymentInfo> infos = new ArrayList<>();
				
				for (PaymentInfo payInf : vendor.getPaymentInfos()) {
					
					ReqPaymentInfo reqPaymentInfo = new ReqPaymentInfo();
					
					reqPaymentInfo.setId(payInf.getId());
					reqPaymentInfo.setAccountName(payInf.getAccountName());
					reqPaymentInfo.setAccountNo(payInf.getAccountNo());
					reqPaymentInfo.setBankName(payInf.getBankName());
					reqPaymentInfo.setBranchName(payInf.getBranchName());
					reqPaymentInfo.setCity(payInf.getCity());
					
					if (payInf.getCountry() != null) {
						reqPaymentInfo.setCountry(payInf.getCountry().getId());
						reqPaymentInfo.setCountryName(payInf.getCountry().getName());
					}else {
						reqPaymentInfo.setCountry(0);
						reqPaymentInfo.setCountryName("None");
					}
					
					infos.add(reqPaymentInfo);
		
				}
				
				reqVendor.setPaymentInfos(infos);
				
			}

			return reqVendor;
		}

		return null;
	}

	@Override
	public TempVendor getTempVendor(ReqVendor reqVendor) {

		TempVendor tempVendor = new TempVendor();
		
		
		if (reqVendor != null) {
			
			if (reqVendor.getAddresses() != null) {
				
				getTempAddresses(reqVendor, tempVendor);
			}
			
			tempVendor.setCompanyName(reqVendor.getCompanyName());
			tempVendor.setComPhoneNo(reqVendor.getComPhoneNo());
			tempVendor.setEmail(reqVendor.getEmail());
			tempVendor.setOwnerName(reqVendor.getOwnerName());
			tempVendor.setPhoneCode(reqVendor.getPhoneCode());
			tempVendor.setPublicId(reqVendor.getPublicId());
			tempVendor.setVendorCategory(reqVendor.getVendorCategory());
			tempVendor.setWebsite(reqVendor.getWebsite());
			
			if (reqVendor.getContactPersons() != null) {
				
				getTempContactPerson(reqVendor, tempVendor);
			}
			
			if (reqVendor.getPaymentInfos() != null) {
				
				
				getTempPaymentInfos(reqVendor, tempVendor);
			}
			
			return tempVendor;
		}
		
		return null;
	}
	
	@Override
	public Vendor getTempVendorToVendor(TempVendor tempVendor) {
		
		if (tempVendor != null) {
			
			Vendor vendor = new Vendor();
			
			vendor.setPublicId(tempVendor.getPublicId());
			
			if (tempVendor.getAddresses() != null) {
				
				getTempAddressToAddress(tempVendor, vendor);
			}
			
			vendor.setCompanyName(tempVendor.getCompanyName());
			vendor.setComPhoneNo(tempVendor.getComPhoneNo());
			
			if (tempVendor.getContactPersons() != null) {
				
				getTempContactToContactPerson(tempVendor, vendor);
			}
			
			vendor.setEmail(tempVendor.getEmail());
			vendor.setOwnerName(tempVendor.getOwnerName());
			vendor.setPhoneCode(tempVendor.getPhoneCode());
			vendor.setWebsite(tempVendor.getWebsite());
			
			if (tempVendor.getVendorCategory() > 0) {
				
				VendorCategory vendorCategory = vendorCategoryServices.getVendorCatById(tempVendor.getVendorCategory());
				
				vendor.setVendorCategory(vendorCategory);
			}
			
			if (tempVendor.getPaymentInfos() != null) {
				
				getTempPayToPay(tempVendor, vendor);
			}
			
				
			return vendor;
		}
		
		return null;
	}
	
	@Override
	public RestVendorDetails getRestVendorDetails(Vendor vendor) {
		
		if (vendor != null) {
			
			RestVendorDetails details = new RestVendorDetails();
			
			if (vendor.getAddresses() != null) {
				
				details.setAddresses(getVendorRestAddress(vendor));
			}
			
			if (vendor.getContactPersons() != null) {
				
				details.setContactPersons(getRestContactPersons(vendor));
			}
			
			if (vendor.getPaymentInfos() != null) {
				
				details.setPaymentInfos(getRestPaymentInfo(vendor));
			}
			
			
			details.setCompanyName(vendor.getCompanyName());
			details.setEmail(vendor.getEmail());
			details.setOwnerName(vendor.getOwnerName());
			details.setPublicId(vendor.getPublicId());
			details.setVGenId(vendor.getVGenId());
			details.setWebsite(vendor.getWebsite());
			details.setPhoneCode(vendor.getPhoneCode());
			details.setComPhoneNo(vendor.getComPhoneNo());
			
			
			return details;
		}
		
		return null;
	}

	

	private List<RestPaymentInfo> getRestPaymentInfo(Vendor vendor) {
		
		List<RestPaymentInfo> infos = new ArrayList<>();

		for (PaymentInfo vPaymentInfo : vendor.getPaymentInfos()) {

			RestPaymentInfo info = new RestPaymentInfo();

			info.setAccountName(vPaymentInfo.getAccountName());
			info.setAccountNo(vPaymentInfo.getAccountNo());
			info.setBankName(vPaymentInfo.getBankName());
			info.setBranchName(vPaymentInfo.getBranchName());
			info.setCity(vPaymentInfo.getCity());
			
			if (vPaymentInfo.getCountry() != null) {
				
				RestCountry country = DozerMapper.parseObject(vPaymentInfo.getCountry(), RestCountry.class);
				info.setCountry(country);
			}

			infos.add(info);
		}

		
		
		return infos;
	}

	private void getTempPayToPay(TempVendor tempVendor, Vendor vendor) {
		List<PaymentInfo> infos = new ArrayList<>();
		
		for (TempPaymentInfo tempInf : tempVendor.getPaymentInfos()) {
			
			PaymentInfo info = new PaymentInfo();
			
			info.setAccountName(tempInf.getAccountName());
			info.setAccountNo(tempInf.getAccountNo());
			info.setBankName(tempInf.getBankName());
			info.setBranchName(tempInf.getBranchName());
			info.setCity(tempInf.getCity());
			info.setVendor(vendor);
			
			if (tempInf.getCountry() > 0) {
				
				Country country = countryServices.getCountryById(tempInf.getCountry());
				info.setCountry(country);
			}
			
			if (tempInf.getPrevId() > 0) {
				
				info.setId(tempInf.getPrevId());
			}
			
			infos.add(info);
		}
		
		vendor.setPaymentInfos(infos);
	}

	private void getTempContactToContactPerson(TempVendor tempVendor, Vendor vendor) {
		List<ContactPerson> contactPersons = new ArrayList<>();
		
		for (TempContactPerson person : tempVendor.getContactPersons()) {
			
			ContactPerson conPerson = new ContactPerson();
			
			conPerson.setConPhoneCode(person.getConPhoneCode());
			conPerson.setEmail(person.getEmail());
			conPerson.setName(person.getName());
			conPerson.setPhoneNo(person.getPhoneNo());
			conPerson.setDesignation(person.getDesignation());
			conPerson.setVendor(vendor);
			
			if (person.getPrevId() > 0) {
				
				conPerson.setId(person.getPrevId());
			
			}
			
			contactPersons.add(conPerson);
			
		}
		
		vendor.setContactPersons(contactPersons);
	}

	private void getTempAddressToAddress(TempVendor tempVendor, Vendor vendor) {
		List<Address> addresses = new ArrayList<>();
		
		for (TempVAddress temAddress : tempVendor.getAddresses()) {
			
			Address address = new Address();
			
			address.setCity(temAddress.getCity());
			
			if (temAddress.getCountry() > 0) {
				Country country = countryServices.getCountryById(temAddress.getCountry());
				address.setCountry(country);
			}
			
			address.setCountryCode(temAddress.getCountryCode());
			address.setHouse(temAddress.getHouse());
			address.setStreet(temAddress.getStreet());
			
			address.setVillage(temAddress.getVillage());
			address.setZipCode(temAddress.getZipCode());
			address.setVendor(vendor);
			
			if (temAddress.getPrevId() > 0) {
				
				address.setId(temAddress.getPrevId());
			}
			
			addresses.add(address);
		}
		
		vendor.setAddresses(addresses);
	}

	private void getTempPaymentInfos(ReqVendor reqVendor, TempVendor tempVendor) {
		List<TempPaymentInfo> infos = new ArrayList<>();
		
		for (ReqPaymentInfo reqPayment : reqVendor.getPaymentInfos()) {
			
			TempPaymentInfo info = new TempPaymentInfo();
			
			if (reqPayment.getId() > 0) {
				info.setPrevId(reqPayment.getId());
			}
			
			
			info.setAccountName(reqPayment.getAccountName());
			info.setAccountNo(reqPayment.getAccountNo());
			info.setBankName(reqPayment.getBankName());
			info.setBranchName(reqPayment.getBranchName());
			info.setCity(reqPayment.getCity());
			info.setCountry(reqPayment.getCountry());
			info.setTempVendor(tempVendor);
			
			infos.add(info);
		}
		
		tempVendor.setPaymentInfos(infos);
	}

	private void getTempContactPerson(ReqVendor reqVendor, TempVendor tempVendor) {
		List<TempContactPerson> tempContactPersons = new ArrayList<>();
		
		for (ReqContactPerson reqContactPerson : reqVendor.getContactPersons()) {
			
			TempContactPerson contactPerson = new TempContactPerson();
			
			if (reqContactPerson.getId() > 0) {
				contactPerson.setPrevId(reqContactPerson.getId());
			}
			
			contactPerson.setConPhoneCode(reqContactPerson.getConPhoneCode());
			contactPerson.setEmail(reqContactPerson.getEmail());
			contactPerson.setName(reqContactPerson.getName());
			contactPerson.setPhoneNo(reqContactPerson.getPhoneNo());
			contactPerson.setTempVendor(tempVendor);
			contactPerson.setDesignation(reqContactPerson.getDesignation());
			
			tempContactPersons.add(contactPerson);
		}
		
		tempVendor.setContactPersons(tempContactPersons);
	}

	private void getTempAddresses(ReqVendor reqVendor, TempVendor tempVendor) {
		List<TempVAddress> tempVAddresses = new ArrayList<>();
		
		for (ReqAddress reqAddress : reqVendor.getAddresses()) {
			
			TempVAddress tempVAddress = new TempVAddress();
			
			if (reqAddress.getId() > 0) {
				tempVAddress.setPrevId(reqAddress.getId());
			}
			
			tempVAddress.setCity(reqAddress.getCity());
			tempVAddress.setCountry(reqAddress.getCountry());
			tempVAddress.setHouse(reqAddress.getHouse());
			tempVAddress.setStreet(reqAddress.getStreet());
			tempVAddress.setTempVendor(tempVendor);
			tempVAddress.setTitle(reqAddress.getTitle());
			tempVAddress.setVillage(reqAddress.getVillage());
			tempVAddress.setZipCode(reqAddress.getZipCode());
			
			tempVAddresses.add(tempVAddress);
		}
		
		tempVendor.setAddresses(tempVAddresses);
	}
	
	private void getReqContactPerson(Vendor vendor, ReqVendor reqVendor) {
		List<ReqContactPerson> reqContactPersons = new ArrayList<>();

		for (ContactPerson contactPerson : vendor.getContactPersons()) {
			ReqContactPerson person = new ReqContactPerson();
			
			person.setId(contactPerson.getId());
			person.setConPhoneCode(contactPerson.getConPhoneCode());
			person.setEmail(contactPerson.getEmail());
			person.setName(contactPerson.getName());
			person.setPhoneNo(contactPerson.getPhoneNo());
			person.setDesignation(contactPerson.getDesignation());

			reqContactPersons.add(person);

		}

		reqVendor.setContactPersons(reqContactPersons);
	}

	private void getAddressToReqAddress(Vendor vendor, ReqVendor reqVendor) {
		List<ReqAddress> reqAddresses = new ArrayList<>();

		for (Address vAddress : vendor.getAddresses()) {

			ReqAddress reqAddress = new ReqAddress();

			reqAddress.setCity(vAddress.getCity());

			if (vAddress.getCountry() != null) {

				reqAddress.setCountry(vAddress.getCountry().getId());
				reqAddress.setCountryName(vAddress.getCountry().getName());
			}else {
				reqAddress.setCountryName("None");
			}
			
			reqAddress.setId(vAddress.getId());
			reqAddress.setCountryCode(vAddress.getCountryCode());
			reqAddress.setHouse(vAddress.getHouse());
			reqAddress.setStreet(vAddress.getStreet());
			reqAddress.setVillage(vAddress.getVillage());
			reqAddress.setZipCode(vAddress.getZipCode());

			reqAddresses.add(reqAddress);
		}

		reqVendor.setAddresses(reqAddresses);
	}

	private void getPayInfMapping(ReqVendor reqVendor, Vendor vendor) {
		List<PaymentInfo> infos = new ArrayList<>();

		for (ReqPaymentInfo reqPaymentInfo : reqVendor.getPaymentInfos()) {

			PaymentInfo info = new PaymentInfo();

			info.setAccountName(reqPaymentInfo.getAccountName());
			info.setAccountNo(reqPaymentInfo.getAccountNo());
			info.setBankName(reqPaymentInfo.getBankName());
			info.setBranchName(reqPaymentInfo.getBranchName());
			info.setCity(reqPaymentInfo.getCity());

			if (reqPaymentInfo.getCountry() > 0) {

				info.setCountry(countryServices.getCountryById(reqPaymentInfo.getCountry()));
			}

			info.setVendor(vendor);

			infos.add(info);
		}

		vendor.setPaymentInfos(infos);
	}

	private void getContactPersonMapping(ReqVendor reqVendor, Vendor vendor) {
		List<ContactPerson> contactPersons = new ArrayList<>();

		for (ReqContactPerson reqContPerson : reqVendor.getContactPersons()) {

			ContactPerson contactPerson = new ContactPerson();

			contactPerson.setConPhoneCode(reqContPerson.getConPhoneCode());
			contactPerson.setEmail(reqContPerson.getEmail());
			contactPerson.setName(reqContPerson.getName());
			contactPerson.setPhoneNo(reqContPerson.getPhoneNo());
			contactPerson.setVendor(vendor);
			contactPerson.setDesignation(reqContPerson.getDesignation());

			contactPersons.add(contactPerson);
		}

		vendor.setContactPersons(contactPersons);
	}

	private void getAddressMapping(ReqVendor reqVendor, Vendor vendor) {
		List<Address> addresses = new ArrayList<>();

		for (ReqAddress rAddress : reqVendor.getAddresses()) {

			Address address = new Address();

			address.setCity(rAddress.getCity());

			if (rAddress.getCountry() > 0) {

				Country country = countryServices.getCountryById(rAddress.getCountry());

				address.setCountry(country);
			}

			address.setCountryCode(rAddress.getCountryCode());
			address.setHouse(rAddress.getHouse());
			address.setStreet(rAddress.getStreet());
			address.setVillage(rAddress.getVillage());
			address.setZipCode(rAddress.getZipCode());
			address.setVendor(vendor);

			addresses.add(address);
		}

		vendor.setAddresses(addresses);
	}

	private RestVendorUserId getRestVendorUId(Vendor vendor) {

		if (vendor != null) {

			RestVendorUserId restVendorUser = new RestVendorUserId();

			restVendorUser.setApproveStatus(vendor.getApproveStatus());
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

			log.info("Befor User Vendor Rest Mapping");
			if (vendor.getUser() != null) {

				restVendorUser.setUserId(vendor.getUser().getUserGemId());
				restVendorUser.setUserPublicId(vendor.getUser().getPublicId());
			}
			log.info("After User Vendor Rest Mapping");

			if (vendor.getVendorCategory() != null) {

				restVendorUser.setRestVendorCategory(
						DozerMapper.parseObject(vendor.getVendorCategory(), RestVendorCategory.class));
			} else {
				// restVendorUser.setRestVendorCategory(new RestVendorCategory());
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
			restPerson.setPublicId(contPerson.getPublicId());
			restPerson.setConPhoneCode(contPerson.getConPhoneCode());
			restPerson.setDesignation(contPerson.getDesignation());

			restContactPersons.add(restPerson);

		}
		return restContactPersons;
	}

	public List<RestAddress> getVendorRestAddress(Vendor vendor) {
		List<RestAddress> restAddresses = new ArrayList<RestAddress>();
		
		log.info("RestAddress Mapping Run !!");

		for (Address address : vendor.getAddresses()) {

			RestAddress restAddress = new RestAddress();

			restAddress.setCity(address.getCity());
			restAddress.setCountryCode(address.getCountryCode());
			restAddress.setHouse(address.getHouse());
			restAddress.setStreet(address.getStreet());
			restAddress.setVillage(address.getVillage());
			restAddress.setZipCode(address.getZipCode());
			
			log.info("RestAddress Mapping Run After Zip Code!!");

			if (address.getCountry() != null) {
				
				log.info("Address To Rest Address: " + address.getCountry().getName());
				
				RestCountry country = DozerMapper.parseObject(address.getCountry(), RestCountry.class);

				restAddress.setCountry(country);
			}
			
			log.info("RestAddress Mapping Run After Country!!");
			restAddresses.add(restAddress);

		}
		return restAddresses;
	}
}
