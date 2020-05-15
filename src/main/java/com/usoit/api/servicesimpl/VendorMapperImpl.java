package com.usoit.api.servicesimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.Address;
import com.usoit.api.data.model.ContactPerson;
import com.usoit.api.data.model.Country;
import com.usoit.api.data.model.PaymentInfo;
import com.usoit.api.data.model.Vendor;
import com.usoit.api.data.vo.RestAddress;
import com.usoit.api.data.vo.RestContactPerson;
import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.data.vo.RestVendorCategory;
import com.usoit.api.data.vo.RestVendorUserId;
import com.usoit.api.model.request.ReqAddress;
import com.usoit.api.model.request.ReqContactPerson;
import com.usoit.api.model.request.ReqPaymentInfo;
import com.usoit.api.model.request.ReqVendor;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.VendorCategoryServices;
import com.usoit.api.services.VendorMapper;

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
					
					reqPaymentInfo.setAccountName(payInf.getAccountName());
					reqPaymentInfo.setAccountNo(payInf.getAccountNo());
					reqPaymentInfo.setBankName(payInf.getBankName());
					reqPaymentInfo.setBranchName(payInf.getBranchName());
					reqPaymentInfo.setCity(payInf.getCity());
					reqPaymentInfo.setCountry(payInf.getCountry().getId());
					
				}
				
			}

			return reqVendor;
		}

		return null;
	}

	private void getReqContactPerson(Vendor vendor, ReqVendor reqVendor) {
		List<ReqContactPerson> reqContactPersons = new ArrayList<>();

		for (ContactPerson contactPerson : vendor.getContactPersons()) {
			ReqContactPerson person = new ReqContactPerson();

			person.setConPhoneCode(contactPerson.getConPhoneCode());
			person.setEmail(contactPerson.getEmail());
			person.setName(contactPerson.getName());
			person.setPhoneNo(contactPerson.getPhoneNo());

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
			}

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

			contactPersons.add(contactPerson);
		}

		vendor.setContactPersons(contactPersons);
	}

	private void getAddressMapping(ReqVendor reqVendor, Vendor vendor) {
		List<Address> addresses = new ArrayList<>();

		for (ReqAddress rAddress : reqVendor.getAddresses()) {

			Address address = new Address();

			address.setCity(rAddress.getCity());

			if (rAddress.getId() > 0) {

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

			System.out.println("Befor User Vendor Rest Mapping");
			if (vendor.getUser() != null) {

				restVendorUser.setUserId(vendor.getUser().getUserGemId());
				restVendorUser.setUserPublicId(vendor.getUser().getPublicId());
			}
			System.out.println("After User Vendor Rest Mapping");

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
