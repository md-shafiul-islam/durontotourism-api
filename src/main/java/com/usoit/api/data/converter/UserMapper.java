package com.usoit.api.data.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SetFactoryBean;
import org.springframework.stereotype.Component;

import com.usoit.api.data.vo.RestAccessTypeUser;
import com.usoit.api.data.vo.RestAccessUser;
import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.data.vo.RestDepartment;
import com.usoit.api.data.vo.RestDesignation;
import com.usoit.api.data.vo.RestGender;
import com.usoit.api.data.vo.RestMaritalStatus;
import com.usoit.api.data.vo.RestRoleOption;
import com.usoit.api.data.vo.RestRoleUser;
import com.usoit.api.data.vo.RestUser;
import com.usoit.api.data.vo.RestUserAddress;
import com.usoit.api.data.vo.RestUserDetails;
import com.usoit.api.data.vo.RestUserPack;
import com.usoit.api.model.Access;
import com.usoit.api.model.Country;
import com.usoit.api.model.Customer;
import com.usoit.api.model.Department;
import com.usoit.api.model.Designation;
import com.usoit.api.model.Gender;
import com.usoit.api.model.MaritalStatus;
import com.usoit.api.model.Role;
import com.usoit.api.model.User;
import com.usoit.api.model.UserAddress;
import com.usoit.api.model.UserAddressTemp;
import com.usoit.api.model.UserTemp;
import com.usoit.api.model.request.ReqAddress;
import com.usoit.api.model.request.ReqUpdateUser;
import com.usoit.api.model.request.ReqUser;
import com.usoit.api.model.response.RestEsCustomer;
import com.usoit.api.model.response.RestEsUser;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.DepartmentServices;
import com.usoit.api.services.DesignationServices;
import com.usoit.api.services.GenderServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.MaritalStatusServices;
import com.usoit.api.services.RoleServices;

@Component
public class UserMapper {

	@Autowired
	private MaritalStatusServices maritalStatusServices;

	@Autowired
	private DesignationServices designationServices;

	@Autowired
	private DepartmentServices departmentServices;

	@Autowired
	private RoleServices roleServices;

	@Autowired
	private CountryServices countryServices;

	@Autowired
	private GenderServices genderServices;

	@Autowired
	private HelperServices helperServices;

	public User getUser(ReqUser reqUser) {

		User user = new User();
		
		if (reqUser == null) {
			return user;
		}
		
		if (reqUser.isAuthenticationStatus()) {
			
			System.out.println("User Auth Ststus: "+ reqUser.isAuthenticationStatus());
			
			user.setAuthenticationStatus(1);
			System.out.println("After Set Auth Value User " + user.getAuthenticationStatus());
		}else {
			System.out.println("User Auth Ststus: "+ reqUser.isAuthenticationStatus());
			user.setAuthenticationStatus(0);
			
			System.out.println("After Set Auth Value User " + user.getAuthenticationStatus());
		}
		
		user.setAccountName(reqUser.getAccountName());
		user.setAccountNo(reqUser.getAccountNo());
		user.setAniversaryDate(reqUser.getAniversaryDate());
		user.setApplicationForJobUrl(reqUser.getApplicationForJobUrl());
		user.setAppointmentLetterUrl(reqUser.getAppointmentLetterUrl());
		user.setApprovalStatus(0);

		user.setBachelorHonoursUrl(reqUser.getBachelorHonoursUrl());
		user.setBankName(reqUser.getBankName());
		user.setBirthCertificateUrl(reqUser.getBirthCertificateUrl());
		user.setBranchName(reqUser.getBranchName());

		user.setCaFcaCmaUrl(reqUser.getCaFcaCmaUrl());
		user.setContactName1(reqUser.getContactName1());
		user.setContactName2(reqUser.getContactName2());

		user.setDateOfBirth(reqUser.getDateOfBirth());

		user.setDepartment(departmentServices.getDepartmentById(reqUser.getDepartment()));
		user.setDesignation(designationServices.getDesignationById(reqUser.getDesignation()));

		user.setDiplomaUrl(reqUser.getDiplomaUrl());
		user.setEmergencyContactNo1(reqUser.getEmergencyContactNo1());
		user.setEmergencyContactNo2(reqUser.getEmergencyContactNo2());
		user.setEmploymentUrl(reqUser.getEmploymentUrl());
		user.setFatherName(reqUser.getFatherName());
		user.setFieldVerificationUrl(reqUser.getFieldVerificationUrl());

		user.setGender(genderServices.getGenderById(reqUser.getGender()));

		user.setHscEquivalentUrl(reqUser.getHscEquivalentUrl());
		user.setHusbandName(reqUser.getHusbandName());

		user.setJobAgreementUrl(reqUser.getJobAgreementUrl());
		user.setJoiningDate(reqUser.getJoiningDate());

		user.setMaritalStatus(maritalStatusServices.getMaritalStatusById(reqUser.getMaritalStatus()));

		user.setMastersUrl(reqUser.getMastersUrl());
		user.setMobileAllowance(reqUser.getMobileAllowance());
		user.setMotherName(reqUser.getMotherName());

		user.setName(reqUser.getName());
		user.setNationalIdNo(reqUser.getNationalIdNo());
		user.setNationalityCertificateUrl(reqUser.getNationalityCertificateUrl());
		user.setNidUrl(reqUser.getNidUrl());

		user.setOfficeLocation(reqUser.getOfficeLocation());
		user.setOfficialEmail(reqUser.getOfficialEmail());
		user.setOfficialPhoneNumber(reqUser.getOfficialPhoneNumber());

		user.setPassportNo(reqUser.getPassportNo());
		user.setPersonalEmail(reqUser.getPassportNo());
		user.setPersonalPhoneNumber(reqUser.getPersonalPhoneNumber());
		user.setPfCaFcaCmaUrl(reqUser.getPfCaFcaCmaUrl());
		user.setPledgeUrl(reqUser.getPledgeUrl());
		user.setProfileIimage(reqUser.getProfileIimage());

		user.setResumeUrl(reqUser.getResumeUrl());

		user.setRole(roleServices.getRoleById(reqUser.getRole()));

		user.setSalary(reqUser.getSalary());
		user.setSecurityDeedUrl(reqUser.getSecurityDeedUrl());
		user.setSscEquivalentUrl(reqUser.getSscEquivalentUrl());
		user.setStatus(0);

		user.setTinNno(reqUser.getTinNno());
		user.setUserSignaturesUrl(reqUser.getUserSignaturesUrl());

		for (ReqAddress address : reqUser.getUserAddresses()) {

			UserAddress userAddress = new UserAddress();
			userAddress.setCity(address.getCity());
			userAddress.setCountry(countryServices.getCountryById(address.getCountry()));
			userAddress.setCountryCode(address.getCountryCode());
			userAddress.setHouse(address.getHouse());
			userAddress.setStreet(address.getStreet());
			userAddress.setTitle(address.getTitle());
			userAddress.setUser(user);
			userAddress.setVillage(address.getVillage());
			userAddress.setZipCode(address.getZipCode());

			user.getUserAddresses().add(userAddress);

			System.out.println("User Address Size: " + user.getUserAddresses().size());
		}

		return user;
	}

	public List<RestUser> getRestUsers(List<User> users) {

		List<RestUser> restUsers = new ArrayList<>();

		if (users != null) {
			
			for (User user : users) {
				restUsers.add(getRestUser(user));
			}
		}

		return restUsers;
	}

	public RestUser getRestUser(User user) {

		RestUser reUser = new RestUser();

		if (user != null) {

			reUser.setPublicId(user.getPublicId());
			reUser.setUserGemId(user.getUserGemId());

			reUser.setAccountName(user.getAccountName());
			reUser.setAccountNo(user.getAccountNo());
			reUser.setAniversaryDate(user.getAniversaryDate());
			reUser.setApplicationForJobUrl(user.getApplicationForJobUrl());
			reUser.setAppointmentLetterUrl(user.getAppointmentLetterUrl());
			reUser.setApprovalStatus(user.getApprovalStatus());

			reUser.setBachelorHonoursUrl(user.getBachelorHonoursUrl());
			reUser.setBankName(user.getBankName());
			reUser.setBirthCertificateUrl(user.getBirthCertificateUrl());
			reUser.setBranchName(user.getBranchName());

			reUser.setCaFcaCmaUrl(user.getCaFcaCmaUrl());
			reUser.setContactName1(user.getContactName1());
			reUser.setContactName2(user.getContactName2());

			reUser.setDateOfBirth(user.getDateOfBirth());
			
			if(user.getDepartment() != null) {
				reUser.setDepartment(DozerMapper.parseObject(user.getDepartment(), RestDepartment.class));
			}
			
			if(user.getDesignation() != null) {
				reUser.setDesignation(DozerMapper.parseObject(user.getDesignation(), RestDesignation.class));
			}

			reUser.setDiplomaUrl(user.getDiplomaUrl());
			reUser.setEmergencyContactNo1(user.getEmergencyContactNo1());
			reUser.setEmergencyContactNo2(user.getEmergencyContactNo2());
			reUser.setEmploymentUrl(user.getEmploymentUrl());
			reUser.setFatherName(user.getFatherName());
			reUser.setFieldVerificationUrl(user.getFieldVerificationUrl());
			
			if(user.getGender() != null) {
				reUser.setGender(DozerMapper.parseObject(user.getGender(), RestGender.class));
			}
			

			reUser.setHscEquivalentUrl(user.getHscEquivalentUrl());
			reUser.setHusbandName(user.getHusbandName());

			reUser.setJobAgreementUrl(user.getJobAgreementUrl());
			reUser.setJoiningDate(user.getJoiningDate());
			
			if(user.getMaritalStatus() != null) {
				reUser.setMaritalStatus(DozerMapper.parseObject(user.getMaritalStatus(), RestMaritalStatus.class));
			}
			

			reUser.setMastersUrl(user.getMastersUrl());
			reUser.setMobileAllowance(user.getMobileAllowance());
			reUser.setMotherName(user.getMotherName());

			reUser.setName(user.getName());
			reUser.setNationalIdNo(user.getNationalIdNo());
			reUser.setNationalityCertificateUrl(user.getNationalityCertificateUrl());
			reUser.setNidUrl(user.getNidUrl());

			reUser.setOfficeLocation(user.getOfficeLocation());
			reUser.setOfficialEmail(user.getOfficialEmail());
			reUser.setOfficialPhoneNumber(user.getOfficialPhoneNumber());

			reUser.setPassportNo(user.getPassportNo());
			reUser.setPersonalEmail(user.getPassportNo());
			reUser.setPersonalPhoneNumber(user.getPersonalPhoneNumber());
			reUser.setPfCaFcaCmaUrl(user.getPfCaFcaCmaUrl());
			reUser.setPledgeUrl(user.getPledgeUrl());
			reUser.setProfileIimage(user.getProfileIimage());

			reUser.setResumeUrl(user.getResumeUrl());

			reUser.setSalary(user.getSalary());
			reUser.setSecurityDeedUrl(user.getSecurityDeedUrl());
			reUser.setSscEquivalentUrl(user.getSscEquivalentUrl());
			reUser.setStatus(user.getStatus());

			reUser.setTinNno(user.getTinNno());
			reUser.setUserSignaturesUrl(user.getUserSignaturesUrl());

			reUser.setUserAddresses(new ArrayList<>());

			/** Set User Role To Rest Role Start */
			RestRoleUser restRoleUser = new RestRoleUser();

			if (user.getRole() != null) {

				restRoleUser.setId(user.getId());
				restRoleUser.setDate(user.getRole().getDate());
				restRoleUser.setDateGroupe(user.getRole().getDate());
				restRoleUser.setName(user.getRole().getName());
				restRoleUser.setDescription(user.getRole().getDescription());

				if (user.getRole().getAccesses() != null) {

					restRoleUser.setAccesses(new ArrayList<>());

					for (Access access : user.getRole().getAccesses()) {

						RestAccessUser accessUser = new RestAccessUser();

						accessUser.setAdd(access.getAdd());
						accessUser.setAll(access.getAdd());
						accessUser.setApprove(access.getApprove());
						accessUser.setDescription(access.getDescription());
						accessUser.setEdit(access.getEdit());
						accessUser.setId(access.getId());
						accessUser.setName(access.getName());
						accessUser.setNoAccess(access.getNoAccess());
						accessUser.setUpdateApproval(access.getUpdateApproval());
						accessUser.setView(access.getView());

						if (access.getAccessType() != null) {

							RestAccessTypeUser accessTypeUser = new RestAccessTypeUser();
							accessTypeUser.setId(access.getAccessType().getId());
							accessTypeUser.setName(access.getAccessType().getName());
							accessTypeUser.setNumValue(access.getAccessType().getNumValue());
							accessTypeUser.setValue(access.getAccessType().getValue());

							accessUser.setAccessType(accessTypeUser);
						}

						restRoleUser.getAccesses().add(accessUser);

					}
				}

			}

			reUser.setRole(restRoleUser);

			/** Set User Role To Rest Role End */

			for (UserAddress address : user.getUserAddresses()) {

				RestUserAddress reUserAddress = new RestUserAddress();

				reUserAddress.setCity(address.getCity());
				
				if(address.getCountry() != null) {
					reUserAddress.setCountry(DozerMapper.parseObject(address.getCountry(), RestCountry.class));
				}
				
				reUserAddress.setCountryCode(address.getCountryCode());
				reUserAddress.setHouse(address.getHouse());
				reUserAddress.setStreet(address.getStreet());
				reUserAddress.setTitle(address.getTitle());

				reUserAddress.setVillage(address.getVillage());
				reUserAddress.setZipCode(address.getZipCode());

				reUser.getUserAddresses().add(reUserAddress);

				System.out.println("reUser Address Size: " + reUser.getUserAddresses().size());
			}

		}
		return reUser;
	}

	public RestUserPack getRestUserPack(User user) {
		
		RestUserPack restUserPack = new RestUserPack();
		
		if (user != null) {
			
			restUserPack.setApprovalStatus(user.getApprovalStatus());
			
			if(user.getDepartment() != null) {
				restUserPack.setDepartment(DozerMapper.parseObject(user.getDepartment(), RestDepartment.class));
			}
			
			restUserPack.setName(user.getName());
			restUserPack.setOfficialPhoneNumber(user.getOfficialPhoneNumber());
			restUserPack.setOfficialEmail(user.getOfficialEmail());
			restUserPack.setPersonalEmail(user.getPersonalEmail());
			restUserPack.setPersonalPhoneNumber(user.getPersonalPhoneNumber());
			restUserPack.setProfileIimage(user.getProfileIimage());
			restUserPack.setPublicId(user.getPublicId());
			restUserPack.setStatus(user.getStatus());
			restUserPack.setUserGemId(user.getUserGemId());
		}
		
		
		return restUserPack;
	}

	public ReqUpdateUser getReqUser(User user) {
		
		ReqUpdateUser reqUpdateUser = null;
		
		if (user != null) {
			
			reqUpdateUser = new ReqUpdateUser();
			
			reqUpdateUser.setAccountName(user.getAccountName());
			reqUpdateUser.setAccountNo(user.getAccountNo());
			reqUpdateUser.setAniversaryDate(user.getAniversaryDate());
			reqUpdateUser.setApplicationForJobUrl(user.getApplicationForJobUrl());
			reqUpdateUser.setAppointmentLetterUrl(user.getAppointmentLetterUrl());
			
			reqUpdateUser.setBachelorHonoursUrl(user.getBachelorHonoursUrl());
			reqUpdateUser.setBankName(user.getBankName());
			reqUpdateUser.setBirthCertificateUrl(user.getBirthCertificateUrl());
			reqUpdateUser.setBranchName(user.getBranchName());
			
			reqUpdateUser.setCaFcaCmaUrl(user.getCaFcaCmaUrl());
			reqUpdateUser.setContactName1(user.getContactName1());
			reqUpdateUser.setContactName2(user.getContactName2());
			
			reqUpdateUser.setDateOfBirth(user.getDateOfBirth());
			
			if (user.getDepartment() != null) {
				reqUpdateUser.setDepartment(user.getDepartment().getId());
			}
			
			if (user.getDesignation() != null) {
				reqUpdateUser.setDesignation(user.getDesignation().getId());
			}
			
			reqUpdateUser.setDiplomaUrl(user.getDiplomaUrl());
			
			reqUpdateUser.setEmergencyContactNo1(user.getEmergencyContactNo1());
			reqUpdateUser.setEmergencyContactNo2(user.getEmergencyContactNo2());
			reqUpdateUser.setEmploymentUrl(user.getEmploymentUrl());
			
			reqUpdateUser.setFatherName(user.getFatherName());
			reqUpdateUser.setFieldVerificationUrl(user.getFieldVerificationUrl());
			
			if (user.getGender() != null) {
				
				reqUpdateUser.setGender(user.getGender().getId());
			}
			
			reqUpdateUser.setHscEquivalentUrl(user.getHscEquivalentUrl());
			reqUpdateUser.setHusbandName(user.getHusbandName());
			
			reqUpdateUser.setJobAgreementUrl(user.getJobAgreementUrl());
			reqUpdateUser.setJoiningDate(user.getJoiningDate());
			
			if (user.getMaritalStatus() != null) {
				
				reqUpdateUser.setMaritalStatus(user.getMaritalStatus().getId());
			}
			
			
			reqUpdateUser.setMastersUrl(user.getMastersUrl());
			reqUpdateUser.setMobileAllowance(user.getMobileAllowance());
			reqUpdateUser.setMotherName(user.getMotherName());
			
			reqUpdateUser.setName(user.getName());
			reqUpdateUser.setNationalIdNo(user.getNationalIdNo());
			reqUpdateUser.setNationalityCertificateUrl(user.getNationalityCertificateUrl());
			reqUpdateUser.setNidUrl(user.getNidUrl());
			reqUpdateUser.setOfficeLocation(user.getOfficeLocation());
			reqUpdateUser.setOfficialEmail(user.getOfficialEmail());
			reqUpdateUser.setOfficialPhoneNumber(user.getOfficialPhoneNumber());
			
			reqUpdateUser.setPassportNo(user.getPassportNo());
			reqUpdateUser.setPersonalEmail(user.getPersonalEmail());
			reqUpdateUser.setPersonalPhoneNumber(user.getPersonalPhoneNumber());
			reqUpdateUser.setPfCaFcaCmaUrl(user.getPfCaFcaCmaUrl());
			reqUpdateUser.setPledgeUrl(user.getPledgeUrl());
			reqUpdateUser.setPublicId(user.getPublicId());
			
			reqUpdateUser.setResumeUrl(user.getResumeUrl());
			
			if (user.getRole() != null) {
				
				reqUpdateUser.setRole(user.getRole().getId());
			}
			reqUpdateUser.setSalary(user.getSalary());
			reqUpdateUser.setSecurityDeedUrl(user.getSecurityDeedUrl());
			reqUpdateUser.setSscEquivalentUrl(user.getSscEquivalentUrl());
			reqUpdateUser.setTinNno(user.getTinNno());
			reqUpdateUser.setUserSignaturesUrl(user.getUserSignaturesUrl());
			
			if (user.getUserAddresses() != null) {
				
				List<ReqAddress> reqAddresses = new ArrayList<>();
				
				if (user.getUserAddresses().size() > 0) {
					
					for (UserAddress address : user.getUserAddresses()) {
						
						ReqAddress reqAddress = new ReqAddress();
						
						reqAddress.setCity(address.getCity());
						reqAddress.setCountryCode(address.getCountryCode());
						
						if (address.getCountry() != null) {
							reqAddress.setCountry(address.getCountry().getId());
						}
						
						reqAddress.setHouse(address.getHouse());
						reqAddress.setId(address.getId());
						reqAddress.setStreet(address.getStreet());
						reqAddress.setTitle(address.getTitle());
						reqAddress.setVillage(address.getVillage());
						reqAddress.setZipCode(address.getZipCode());
						
						reqAddresses.add(reqAddress);
					}
				}else {
					
					ReqAddress adress1 = new ReqAddress();
					adress1.setTitle("Present Address");
					reqAddresses.add(adress1);
					
					ReqAddress adress2 = new ReqAddress();
					adress2.setTitle("Permanent Address Address");
					
					reqAddresses.add(adress1);
					reqAddresses.add(adress2);
					
				}
				
				reqUpdateUser.setUserAddresses(reqAddresses);
			}else {
				List<ReqAddress> reqAddresses = new ArrayList<>();
				
				ReqAddress adress1 = new ReqAddress();
				adress1.setTitle("Present Address");
				reqAddresses.add(adress1);
				
				ReqAddress adress2 = new ReqAddress();
				adress2.setTitle("Permanent Address Address");
				
				reqAddresses.add(adress1);
				reqAddresses.add(adress2);
				
				reqUpdateUser.setUserAddresses(reqAddresses);
				
			}
			
			
			
		}
		
		return reqUpdateUser;
	}

	public RestUserDetails getRestUserDetails(User user) {
		
		if (user != null) {
			
			RestUserDetails userDetails = new RestUserDetails();
			
			userDetails.setAccountName(user.getAccountName());
			userDetails.setAccountNo(user.getAccountNo());
			userDetails.setAniversaryDate(user.getAniversaryDate());
			userDetails.setApplicationForJobUrl(user.getApplicationForJobUrl());
			userDetails.setAppointmentLetterUrl(user.getAppointmentLetterUrl());
			userDetails.setAuthenticationStatus(user.getAuthenticationStatus());
			userDetails.setApprovalStatus(user.getApprovalStatus());
			
			userDetails.setBachelorHonoursUrl(user.getBachelorHonoursUrl());
			userDetails.setBankName(user.getBankName());
			userDetails.setBirthCertificateUrl(user.getBirthCertificateUrl());
			userDetails.setBranchName(user.getBranchName());
			
			userDetails.setCaFcaCmaUrl(user.getCaFcaCmaUrl());
			userDetails.setContactName1(user.getContactName1());
			userDetails.setContactName2(user.getContactName2());
			
			userDetails.setDateOfBirth(user.getDateOfBirth());
			
			System.out.println("Befor User Department !!");
			
			if (user.getDepartment() != null) {
				
				userDetails.setDepartment(DozerMapper.parseObject(user.getDepartment(), RestDepartment.class));
			}
			
			System.out.println("After User Department !!");
			
			System.out.println("Befor User Designation !!");
			
			if(user.getDesignation() != null) {
				
				userDetails.setDesignation(DozerMapper.parseObject(user.getDesignation(), RestDesignation.class));
			}
			System.out.println("After User Designation !!");
			
			userDetails.setDiplomaUrl(user.getDiplomaUrl());
			
			userDetails.setEmergencyContactNo1(user.getEmergencyContactNo1());
			userDetails.setEmergencyContactNo2(user.getEmergencyContactNo2());
			userDetails.setEmploymentUrl(user.getEmploymentUrl());
			
			userDetails.setFatherName(user.getFatherName());
			userDetails.setFieldVerificationUrl(user.getFieldVerificationUrl());
			
			System.out.println("Befor User Gender !!");
			if (user.getGender() != null) {
				
				userDetails.setGender(DozerMapper.parseObject(user.getGender(), RestGender.class));
			}
			
			System.out.println("After  User Gender !!");
			
			userDetails.setHscEquivalentUrl(user.getHscEquivalentUrl());
			userDetails.setHusbandName(user.getHusbandName());
			
			userDetails.setJobAgreementUrl(user.getJobAgreementUrl());
			userDetails.setJoiningDate(user.getJoiningDate());
			
			System.out.println("Befor User Marital Status !!");
			if (user.getMaritalStatus() != null) {
				
				userDetails.setMaritalStatus(DozerMapper.parseObject(user.getMaritalStatus(), RestMaritalStatus.class));
			}
			
			System.out.println("Aftre User Marital Status !!");
			
			userDetails.setMastersUrl(user.getMastersUrl());
			userDetails.setMobileAllowance(user.getMobileAllowance());
			userDetails.setMotherName(user.getMotherName());
			
			userDetails.setName(user.getName());
			userDetails.setNationalIdNo(user.getNationalIdNo());
			userDetails.setNationalityCertificateUrl(user.getNationalityCertificateUrl());
			userDetails.setNidUrl(user.getNidUrl());
			
			userDetails.setOfficeLocation(user.getOfficeLocation());
			userDetails.setOfficialEmail(user.getOfficialEmail());
			userDetails.setOfficialPhoneNumber(user.getOfficialPhoneNumber());
			
			userDetails.setPassportNo(user.getPassportNo());
			userDetails.setPersonalEmail(user.getPersonalEmail());
			userDetails.setPersonalPhoneNumber(user.getPersonalPhoneNumber());
			userDetails.setPfCaFcaCmaUrl(user.getPfCaFcaCmaUrl());
			userDetails.setPledgeUrl(user.getPledgeUrl());
			userDetails.setProfileIimage(user.getProfileIimage());
			userDetails.setPublicId(user.getPublicId());
			
			userDetails.setResumeUrl(user.getResumeUrl());
			
			
			if (user.getRole() != null) {
				
				userDetails.setRole(DozerMapper.parseObject(user.getRole(), RestRoleOption.class));
			}
			
			
			userDetails.setSalary(user.getSalary());
			userDetails.setSecurityDeedUrl(user.getSecurityDeedUrl());
			userDetails.setSscEquivalentUrl(user.getSscEquivalentUrl());
			userDetails.setStatus(user.getStatus());
			
			userDetails.setTinNno(user.getTinNno());
			
			userDetails.setUpdateApproveStatus(user.getUpdateApproveStatus());
			userDetails.setUserGemId(user.getUserGemId());
			userDetails.setUserSignaturesUrl(user.getUserSignaturesUrl());
			
			if (user.getUserAddresses() != null) {
				
				List<RestUserAddress> restUserAddresses = new ArrayList<>();
				
				for (UserAddress uAddress : user.getUserAddresses()) {
					
					RestUserAddress address = new RestUserAddress();
					
					address.setCity(uAddress.getCity());
					address.setCountryCode(uAddress.getCountryCode());
					address.setHouse(uAddress.getHouse());
					address.setId(uAddress.getId());
					address.setStreet(uAddress.getStreet());
					address.setTitle(uAddress.getTitle());
					address.setVillage(uAddress.getVillage());
					address.setZipCode(uAddress.getZipCode());
					
					if(uAddress.getCountry() != null) {
						address.setCountry(DozerMapper.parseObject(uAddress.getCountry(), RestCountry.class));
					}
					
					restUserAddresses.add(address);
				}
				
				userDetails.setUserAddresses(restUserAddresses);
				
			}
			
			return userDetails;
		}
		
		
		return null;
	}

	public RestUserDetails getRestUserByTempUser(UserTemp tempUser) {
		
		if (tempUser != null) {
			
			RestUserDetails userDetails = new RestUserDetails();
			
			userDetails.setAccountName(tempUser.getAccountName());
			userDetails.setAccountNo(tempUser.getAccountNo());
			userDetails.setAniversaryDate(tempUser.getAniversaryDate());
			userDetails.setApplicationForJobUrl(tempUser.getApplicationForJobUrl());
			userDetails.setAppointmentLetterUrl(tempUser.getAppointmentLetterUrl());
			
			userDetails.setBachelorHonoursUrl(tempUser.getBachelorHonoursUrl());
			userDetails.setBankName(tempUser.getBankName());
			userDetails.setBirthCertificateUrl(tempUser.getBirthCertificateUrl());
			userDetails.setBranchName(tempUser.getBranchName());
			
			userDetails.setCaFcaCmaUrl(tempUser.getCaFcaCmaUrl());
			userDetails.setContactName1(tempUser.getContactName1());
			userDetails.setContactName2(tempUser.getContactName2());
			
			userDetails.setDateOfBirth(tempUser.getDateOfBirth());
			
			if (tempUser.getDepartment() > 0) {
				
				Department department = departmentServices.getDepartmentById(tempUser.getDepartment());
				
				userDetails.setDepartment(DozerMapper.parseObject(department, RestDepartment.class));
			}
			
			if (tempUser.getDesignation() > 0) {
				
				Designation designation = designationServices.getDesignationById(tempUser.getDesignation());
				
				userDetails.setDesignation(DozerMapper.parseObject(designation, RestDesignation.class));
			}
			
			userDetails.setDiplomaUrl(tempUser.getDiplomaUrl());
			
			userDetails.setEmergencyContactNo1(tempUser.getEmergencyContactNo1());
			userDetails.setEmergencyContactNo2(tempUser.getEmergencyContactNo2());
			userDetails.setEmploymentUrl(tempUser.getEmployment_url());
			
			userDetails.setFatherName(tempUser.getFatherName());
			userDetails.setFieldVerificationUrl(tempUser.getFieldVerificationUrl());
			
			if (tempUser.getGender() > 0) {
				
				Gender gender = genderServices.getGenderById(tempUser.getGender());
				
				userDetails.setGender(DozerMapper.parseObject(gender, RestGender.class));
			}
			
			userDetails.setHscEquivalentUrl(tempUser.getHscEquivalentUrl());
			userDetails.setHusbandName(tempUser.getHusbandName());
			
			userDetails.setJobAgreementUrl(tempUser.getJobAgreementUrl());
			userDetails.setJoiningDate(tempUser.getJoiningDate());
			
			userDetails.setMastersUrl(tempUser.getMastersUrl());
			userDetails.setMobileAllowance(tempUser.getMobileAllowance());
			userDetails.setMotherName(tempUser.getMotherName());
			
			if (tempUser.getMaritalStatus() > 0) {
				
				MaritalStatus maritalStatus = maritalStatusServices.getMaritalStatusById(tempUser.getMaritalStatus());
				
				userDetails.setMaritalStatus(DozerMapper.parseObject(maritalStatus, RestMaritalStatus.class));
			}
			
			userDetails.setName(tempUser.getName());
			userDetails.setNationalIdNo(tempUser.getNationalIdNo());
			userDetails.setNationalityCertificateUrl(tempUser.getNationalityCertificateUrl());
			userDetails.setNidUrl(tempUser.getNidUrl());
			
			userDetails.setOfficeLocation(tempUser.getOfficeLocation());
			userDetails.setOfficialEmail(tempUser.getOfficialEmail());
			userDetails.setOfficialPhoneNumber(tempUser.getOfficialPhoneNumber());
			
			userDetails.setPassportNo(tempUser.getPassportNo());
			userDetails.setPersonalEmail(tempUser.getPersonalEmail());
			userDetails.setPersonalPhoneNumber(tempUser.getPersonalPhoneNumber());
			userDetails.setPfCaFcaCmaUrl(tempUser.getPfCaFcaCmaUrl());
			userDetails.setPledgeUrl(tempUser.getPledgeUrl());
			userDetails.setProfileIimage(tempUser.getProfileIimage());
			userDetails.setPublicId(tempUser.getPublicId());
			
			userDetails.setResumeUrl(tempUser.getResumeUrl());
			
			if (tempUser.getRole() > 0) {
				
				Role role = roleServices.getRoleById(tempUser.getRole());
				
				userDetails.setRole(DozerMapper.parseObject(role, RestRoleOption.class));
				
			}
			
			userDetails.setSalary(tempUser.getSalary());
			userDetails.setSecurityDeedUrl(tempUser.getSecurityDeedUrl());
			userDetails.setSscEquivalentUrl(tempUser.getSscEquivalentUrl());
			
			userDetails.setTinNno(tempUser.getTinNno());
			
			userDetails.setUserSignaturesUrl(tempUser.getUserSignaturesUrl());
			
			if (tempUser.getUserAddresses() != null) {
				
				List<RestUserAddress> restUserAddresses = new ArrayList<>();
				
				for (UserAddressTemp userTempAddress : tempUser.getUserAddresses()) {
					
					RestUserAddress address = new RestUserAddress();
					
					address.setCity(userTempAddress.getCity());
					address.setCountryCode(userTempAddress.getCountryCode());
					address.setHouse(userTempAddress.getHouse());
					address.setStreet(userTempAddress.getStreet());
					address.setTitle(userTempAddress.getTitle());
					address.setVillage(userTempAddress.getVillage());
					address.setZipCode(userTempAddress.getZipCode());
					
					if (userTempAddress.getCountry() > 0) {
						
						Country country = countryServices.getCountryById(userTempAddress.getCountry());
						
						address.setCountry(DozerMapper.parseObject(country, RestCountry.class));
					}
					
					restUserAddresses.add(address);
				}
				
				userDetails.setUserAddresses(restUserAddresses);
				
			}
			
			return userDetails;
		}
		
		return null;
	}

	public User getUserByTempUser(UserTemp tempUser) {
		
		User user = new User();
		user.setAccountName(tempUser.getAccountName());
		user.setAccountNo(tempUser.getAccountNo());
		user.setAniversaryDate(tempUser.getAniversaryDate());
		user.setApplicationForJobUrl(tempUser.getApplicationForJobUrl());
		user.setAppointmentLetterUrl(tempUser.getAppointmentLetterUrl());
		
		
		user.setBachelorHonoursUrl(tempUser.getBachelorHonoursUrl());
		user.setBankName(tempUser.getBankName());
		user.setBranchName(tempUser.getBranchName());
		user.setBirthCertificateUrl(tempUser.getBirthCertificateUrl());
		
		user.setCaFcaCmaUrl(tempUser.getCaFcaCmaUrl());
		user.setContactName1(tempUser.getContactName1());
		user.setContactName2(tempUser.getContactName2());
		
		user.setDateOfBirth(tempUser.getDateOfBirth());
		user.setDiplomaUrl(tempUser.getDiplomaUrl());
		
		if (tempUser.getDepartment() > 0) {
			
			Department department = departmentServices.getDepartmentById(tempUser.getDepartment());
			user.setDepartment(department);
			 
		}
		
		if (tempUser.getDesignation() > 0) {
			
			Designation designation = designationServices.getDesignationById(tempUser.getDesignation());
			user.setDesignation(designation);
		}
		
		user.setEmergencyContactNo1(tempUser.getEmergencyContactNo1());
		user.setEmergencyContactNo2(tempUser.getEmergencyContactNo2());
		user.setEmploymentUrl(tempUser.getEmployment_url());
		
		
		user.setFatherName(tempUser.getFatherName());
		user.setFieldVerificationUrl(tempUser.getFieldVerificationUrl());
		
		if (tempUser.getGender() > 0) {
			
			Gender gender = genderServices.getGenderById(tempUser.getGender());
			user.setGender(gender);
		}
		
		user.setHscEquivalentUrl(tempUser.getHscEquivalentUrl());
		user.setHusbandName(tempUser.getHusbandName());
		
		user.setJobAgreementUrl(tempUser.getJobAgreementUrl());
		user.setJoiningDate(tempUser.getJoiningDate());
		
		user.setMastersUrl(tempUser.getMastersUrl());
		user.setMobileAllowance(tempUser.getMobileAllowance());
		user.setMotherName(tempUser.getMotherName());
		
		
		if (tempUser.getMaritalStatus() > 0) {
			
			MaritalStatus maritalStatus = maritalStatusServices.getMaritalStatusById(tempUser.getMaritalStatus());
			
			user.setMaritalStatus(maritalStatus);
		}
		
		user.setName(tempUser.getName());
		user.setNationalIdNo(tempUser.getNationalIdNo());
		user.setNationalityCertificateUrl(tempUser.getNationalityCertificateUrl());
		user.setNidUrl(tempUser.getNidUrl());
		
		user.setOfficeLocation(tempUser.getOfficeLocation());
		user.setOfficialEmail(tempUser.getOfficialEmail());
		user.setOfficialPhoneNumber(tempUser.getOfficialPhoneNumber());
		
		user.setPassportNo(tempUser.getPassportNo());
		user.setPersonalEmail(tempUser.getPersonalEmail());
		user.setPersonalPhoneNumber(tempUser.getPersonalPhoneNumber());
		user.setPfCaFcaCmaUrl(tempUser.getPfCaFcaCmaUrl());
		user.setPledgeUrl(tempUser.getPledgeUrl());
		user.setProfileIimage(tempUser.getProfileIimage());
		user.setPublicId(tempUser.getPublicId());
		
		user.setResumeUrl(tempUser.getResumeUrl());
		
		if (tempUser.getRole() > 0) {
			
			Role role = roleServices.getRoleById(tempUser.getRole());
			
			user.setRole(role);
		}
		
		
		user.setSalary(tempUser.getSalary());
		user.setSecurityDeedUrl(tempUser.getSecurityDeedUrl());
		user.setSscEquivalentUrl(tempUser.getSscEquivalentUrl());
		
		user.setTinNno(tempUser.getTinNno());
		user.setUserSignaturesUrl(tempUser.getUserSignaturesUrl());
		
		if (tempUser.getUserAddresses() != null) {
			
			List<UserAddress> userAddress = new ArrayList<>();
			
			for (UserAddressTemp  address: tempUser.getUserAddresses()) {
				
				UserAddress userAddr = new UserAddress();
				
				userAddr.setCity(address.getCity());
				userAddr.setCountryCode(address.getCountryCode());
				userAddr.setHouse(address.getHouse());
				userAddr.setStreet(address.getStreet());
				userAddr.setTitle(address.getTitle());
				userAddr.setVillage(address.getVillage());
				userAddr.setZipCode(address.getZipCode());
				
				if (address.getCountry() > 0) {
					
					Country country = countryServices.getCountryById(address.getCountry());
					userAddr.setCountry(country);
				}
				
				userAddress.add(userAddr);
				
			}
			
			user.setUserAddresses(userAddress);
		}
		
		
		return user;
		
	}

	public RestEsUser mapEsUser(User createdUser) {
		
		if(createdUser != null) {
			
			RestEsUser esUser = new RestEsUser();
			
			esUser.setName(createdUser.getName());
			esUser.setOfficialEmail(createdUser.getOfficialEmail());
			esUser.setOfficialPhoneNumber(createdUser.getOfficialPhoneNumber());
			esUser.setPersonalEmail(createdUser.getPersonalEmail());
			esUser.setPersonalPhoneNumber(createdUser.getPersonalPhoneNumber());
			esUser.setPublicId(createdUser.getPublicId());
			
			
			return esUser;
		}
		
		return null;
	}

	



}
