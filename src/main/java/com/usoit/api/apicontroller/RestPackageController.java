package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.PackageMapper;
import com.usoit.api.data.converter.UserMapper;
import com.usoit.api.data.model.AjaxId;
import com.usoit.api.data.model.Category;
import com.usoit.api.data.model.Country;
import com.usoit.api.data.model.ImageGallery;
import com.usoit.api.data.model.Itarnary;
import com.usoit.api.data.model.PackageCat;
import com.usoit.api.data.model.Packages;
import com.usoit.api.data.model.User;
import com.usoit.api.data.model.Vendor;
import com.usoit.api.data.shared.dto.DtoItarnary;
import com.usoit.api.data.shared.dto.DtoUpdatePackage;
import com.usoit.api.data.vo.RestAccessUser;
import com.usoit.api.data.vo.RestPackDetails;
import com.usoit.api.data.vo.RestPackage;
import com.usoit.api.data.vo.RestUser;
import com.usoit.api.data.vo.RestViewPackages;
import com.usoit.api.model.request.ReceiveStringId;
import com.usoit.api.model.request.ReqItarnaryOnPack;
import com.usoit.api.model.request.ReqPackage;
import com.usoit.api.services.CategoryServices;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.PackageCatServices;
import com.usoit.api.services.PackageServices;
import com.usoit.api.services.UserServices;
import com.usoit.api.services.VendorServices;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin(origins = "http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser", "currentPackage", "selectedPack" })
public class RestPackageController {

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private PackageServices packageServices;

	@Autowired
	private VendorServices vendorServices;

	@Autowired
	private CountryServices countryServices;

	@Autowired
	private PackageCatServices packageCatServices;

	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private UserServices userServices;

	private List<PackageCat> packageCats;

	private List<Packages> pandinPackages;

	private List<RestPackage> restPandingPackages = new ArrayList<>();

	private List<Packages> confPackages;

	private List<Packages> upPandingPackages;

	private List<Packages> rejPackages;

	private List<RestPackage> restConfPackages;

	private List<RestPackage> restUpdatePandingPackages;

	private List<RestPackage> restRejPackages;

	private List<Vendor> vendors;

	private List<Country> countries;

	private List<Category> categories;

	private List<RestViewPackages> restViewPacks;

	@Autowired
	private PackageMapper packageMapper;

	private List<User> users;

	private List<RestUser> restUsers;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserServices useraServices;

	private User cUser;

	private List<RestViewPackages> restViewConfPacks;

	private List<RestViewPackages> restViewRejectPacks;

	private List<RestViewPackages> restUpdateApprovalPandingPacks;

	@RequestMapping(value = "/package/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPackageDetails(Principal principal, HttpServletRequest httpServletRequest,
			@PathVariable("id") String id) {

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				return ResponseEntity.ok("Login And try Again ");
			} else {

				if (accessPack.getAdd() == 1 || accessPack.getAll() == 1 || accessPack.getView() == 1
						|| accessPack.getApprove() == 1 || accessPack.getEdit() == 1
						|| accessPack.getUpdateApproval() == 1) {

				} else {
					return ResponseEntity.ok("You cann't Access this options Please contact Administartor ");
				}
			}
		}

		// Set Access controls End

		if (id != null) {

			if (id.length() == 55) {

				RestPackDetails restPackageDetails = null;

				Packages packages = packageServices.getPackageByPID(id);

				if (packages != null) {

					restPackageDetails = packageMapper.getRestPackageDetails(packages);

					if (restPackageDetails != null) {

						System.out.println("Controller Countries Size: " + restPackageDetails.getCountries().size());

						return ResponseEntity.ok(restPackageDetails);
					}

				} else {
					System.out.println("Package Not Found Line RestPackController: 122");
				}
			} else {
				System.out.println("Package Not ID Lenght Not Match RestPackController: 126");
			}
		}

		return null;
	}

	@RequestMapping(value = "/package", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNewPackage(Principal principal, HttpServletRequest httpServletRequest,
			@RequestBody ReqPackage reqPackage) {

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				return ResponseEntity.ok("Login And try Again ");
			} else {

				if (accessPack.getAdd() == 1 || accessPack.getAll() == 1) {

				} else {
					return ResponseEntity.ok("You cann't Access this options Please contact Administartor ");
				}
			}
		}

		// Set Access controls End

		System.out.println("Add Package Run");

		return ResponseEntity.ok(reqPackage);
	}

	@RequestMapping(value = "/package/edit/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPackageById(Principal principal, HttpServletRequest request,
			@PathVariable("id") String pId) {

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				return ResponseEntity.ok("Login And try Again ");
			} else {

				if (accessPack.getEdit() == 1 || accessPack.getAll() == 1) {

				} else {
					return ResponseEntity.ok("You cann't Access this options Please contact Administartor ");
				}
			}
		}

		// Set Access controls End

		DtoUpdatePackage reqPackage = new DtoUpdatePackage();

		if (pId != null) {

			System.out.println("Pack ID lenght: " + pId.length());
			if (pId.length() == 55) {

				reqPackage = packageServices.getRePackageUpdateByPublicId(pId);
			}
		}

		return ResponseEntity.ok(reqPackage);
	}

	@RequestMapping(value = "/confrim", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllConfrimPackages(Principal principal, HttpServletRequest request) {

		// Set Access controls Start
		List<String> acMgs = new ArrayList<>();
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				acMgs.add(new String("Login And try Again "));
				return ResponseEntity.ok(acMgs);
			} else {

				if (accessPack.getEdit() == 1 || accessPack.getAll() == 1 || accessPack.getView() == 1
						|| accessPack.getAdd() == 1 || accessPack.getApprove() == 1
						|| accessPack.getUpdateApproval() == 1) {

				} else {
					acMgs.add(new String("You cann't Access this options Please contact Administartor "));

					return ResponseEntity.ok(acMgs);
				}
			}
		}

		// Set Access controls End

		List<String> data = new ArrayList<String>();

		if (setRestConfrimPackages()) {

			return ResponseEntity.ok(restViewConfPacks);
		} else {

			data.add(new String("Else Error !!"));
			return ResponseEntity.ok(data);
		}

	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllRejectedPackages(Principal principal, HttpServletRequest request) {

		// Set Access controls Start
		List<String> acMgs = new ArrayList<>();
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				acMgs.add(new String("Login And try Again "));
				return ResponseEntity.ok(acMgs);
			} else {

				if (accessPack.getAdd() == 1 || accessPack.getAll() == 1) {

				} else {
					acMgs.add(new String("You cann't Access this options Please contact Administartor "));

					return ResponseEntity.ok(acMgs);
				}
			}
		}

		// Set Access controls End

		List<String> data = new ArrayList<String>();

		if (setRestRejectPackages()) {

			return ResponseEntity.ok(restViewRejectPacks);
		} else {

			data.add(new String("Else Error !! Rejected Pack Not Found"));
			return ResponseEntity.ok(data);
		}

	}

	@RequestMapping(value = "/update-approval-panding", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllUpdateApprovalPandingPackages(Principal principal,
			HttpServletRequest request) {

		// Set Access controls Start
		List<String> acMgs = new ArrayList<>();
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				acMgs.add(new String("Login And try Again "));
				return ResponseEntity.ok(acMgs);
			} else {

				if (accessPack.getEdit() == 1 || accessPack.getAll() == 1 || accessPack.getUpdateApproval() == 1) {

				} else {
					acMgs.add(new String("You cann't Access this options Please contact Administartor "));

					return ResponseEntity.ok(acMgs);
				}
			}
		}

		// Set Access controls End

		List<String> data = new ArrayList<String>();

		if (setRestUpdateAppPandingPackages()) {

			return ResponseEntity.ok(restUpdateApprovalPandingPacks);
		} else {

			data.add(new String("Else Error !! Update Pannding Pack Not found!!"));
			return ResponseEntity.ok(data);
		}

	}

	@RequestMapping(value = "/panding", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllPandingPackages(Principal principal, HttpServletRequest request) {

		// Set Access controls Start
		List<String> acMgs = new ArrayList<>();
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				acMgs.add(new String("Login And try Again "));
				return ResponseEntity.ok(acMgs);
			} else {

				if (accessPack.getAll() == 1 || accessPack.getAdd() == 1 || accessPack.getApprove() == 1) {

				} else {
					acMgs.add(new String("You cann't Access this options Please contact Administartor "));

					return ResponseEntity.ok(acMgs);
				}
			}
		}

		// Set Access controls End

		List<String> data = new ArrayList<String>();
		if (setRestPandingPackages()) {
			return ResponseEntity.ok(restPandingPackages);
		} else {
			data.add(new String("Else Error !!"));
			return ResponseEntity.ok(data);
		}

	}

	@RequestMapping(value = "/package", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPackageUpdateAction(Principal principal, @RequestBody DtoUpdatePackage packages,
			HttpServletRequest request) {

		// Set Access controls Start
		List<String> acMgs = new ArrayList<>();
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				acMgs.add(new String("Login And try Again "));
				return ResponseEntity.ok(acMgs);
			} else {

				if (accessPack.getEdit() == 1 || accessPack.getAll() == 1) {

				} else {
					acMgs.add(new String("You cann't Access this options Please contact Administartor "));

					return ResponseEntity.ok(acMgs);
				}
			}
		}

		// Set Access controls End

		cUser = helperServices.getUserByPrincipal(principal);

		if (packages != null) {

			System.out.println("Update Pack Not Null !!");

			System.out.println("ID: " + packages.getPublicId());

			if (packages.getPublicId().length() > 30) {

				System.out.println("Update Pack ID PASS!!");

				if (packageServices.updatePackDto(packages)) {

					System.out.println("Package Updated Done!!");
					return ResponseEntity.ok(packages);
				}
				;

			}
		}

		return ResponseEntity.accepted().body(packages);
	}

	@RequestMapping(value = "/package/reject", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRejectPackRequest(Principal principal, @RequestBody ReceiveStringId reciveData,
			HttpServletRequest request) {

		// Set Access controls Start
		List<String> acMgs = new ArrayList<>();
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				acMgs.add(new String("Login And try Again "));
				return ResponseEntity.ok(acMgs);
			} else {

				if (accessPack.getAll() == 1 || accessPack.getApprove() == 1) {

				} else {
					acMgs.add(new String("You cann't Access this options Please contact Administartor "));

					return ResponseEntity.ok(acMgs);
				}
			}
		}

		// Set Access controls End

		cUser = helperServices.getUserByPrincipal(principal);

		System.out.println("Reject Run!!");

		if (reciveData.getPublicId() != null) {

			if (reciveData.getPublicId().length() == 55) {

				System.out.println("Reject Run!! Lenght Pass");

				if (packageServices.rejactPackByPublicId(reciveData.getPublicId(), cUser)) {
					System.out.println("Reject Run!! Update");
					return ResponseEntity.ok(true);
				}
			}
		}

		return ResponseEntity.ok(false);
	}

	@RequestMapping(value = "/package/approve", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPackageApproveAction(Principal principal, @RequestBody ReceiveStringId receiveData,
			HttpServletRequest request) {

		// Set Access controls Start
		List<String> acMgs = new ArrayList<>();
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				acMgs.add(new String("Login And try Again "));
				return ResponseEntity.ok(acMgs);
			} else {

				if (accessPack.getUpdateApproval() == 1 || accessPack.getAll() == 1) {

				} else {
					acMgs.add(new String("You cann't Access this options Please contact Administartor "));

					return ResponseEntity.ok(acMgs);
				}
			}
		}

		// Set Access controls End

		cUser = helperServices.getUserByPrincipal(principal);

		System.out.println("Approve Run!!");

		if (receiveData != null) {

			if (receiveData.getPublicId() != null) {

				System.out.println("Approve Run!! Not Null");

				if (receiveData.getPublicId().length() == 55) {

					System.out.println("Approve Run!! lenght pass!!");
					if (packageServices.approvePackByPublicId(receiveData.getPublicId(), cUser)) {

						System.out.println("Approve Run!!Done");

						return ResponseEntity.ok(true);
					}
				}
			}
		}

		return ResponseEntity.ok(false);
	}

	@RequestMapping(value = "/package/update-reject", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUpdatePackRejectAction(Principal principal, @RequestBody ReceiveStringId receiveData) {

		// Set Access controls Start
		List<String> acMgs = new ArrayList<>();
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				acMgs.add(new String("Login And try Again "));
				return ResponseEntity.ok(acMgs);
			} else {

				if (accessPack.getUpdateApproval() == 1 || accessPack.getAll() == 1) {

				} else {
					acMgs.add(new String("You cann't Access this options Please contact Administartor "));

					return ResponseEntity.ok(acMgs);
				}
			}
		}

		// Set Access controls End

		cUser = helperServices.getUserByPrincipal(principal);

		if (receiveData != null) {
			System.out.println("Update reject Data Not Null!");
			if (receiveData.getPublicId() != null) {

				System.out.println("Update reject ID   Pass!!");
				if (receiveData.getPublicId().length() == 55) {

					System.out.println("Update reject ID Not lenght Pass!!");
					if (packageServices.updatePackRejectByPbID(receiveData.getPublicId(), cUser)) {
						System.out.println("Update reject Done");
						return ResponseEntity.ok(true);
					}

				}
			}
		}

		return ResponseEntity.ok(false);
	}

	@RequestMapping(value = "/package/update-approve", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUpdatePackApproveAction(Principal principal, @RequestBody ReceiveStringId receiveData) {

		// Set Access controls Start
		List<String> acMgs = new ArrayList<>();
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		if (accessMap != null) {

			RestAccessUser accessPack = accessMap.get("package_ac");
			if (accessPack == null) {
				acMgs.add(new String("Login And try Again "));
				return ResponseEntity.ok(acMgs);
			} else {

				if (accessPack.getAll() == 1 || accessPack.getUpdateApproval() == 1) {

				} else {
					acMgs.add(new String("You cann't Access this options Please contact Administartor "));

					return ResponseEntity.ok(acMgs);
				}
			}
		}

		// Set Access controls End

		cUser = helperServices.getUserByPrincipal(principal);

		if (receiveData != null) {

			if (receiveData.getPublicId() != null) {

				System.out.println(" Update Approve PUb ID Not Null");

				if (receiveData.getPublicId().length() == 55) {

					System.out.println("Update Approve ID Not lenght Pass!!");

					if (packageServices.updatePackApproveByPbID(receiveData.getPublicId(), cUser)) {
						System.out.println("Update Approve Done!!");
						return ResponseEntity.ok(true);
					}

				}
			}
		}

		return ResponseEntity.ok(false);
	}

	private boolean setRestConfrimPackages() {

		setPakagesConfirm();

		if (confPackages != null) {

			restViewConfPacks = packageMapper.getPackageListView(confPackages);

			if (restViewConfPacks != null) {

				return true;
			}
		}

		return false;
	}

	private boolean setRestPandingPackages() {

		setPackagePendinList();
		System.out.println("rest panding Pack & Pandig Pack Size Start" + pandinPackages.size());

		if (pandinPackages != null) {

			System.out.println("PP Not Null");
			restPandingPackages = packageMapper.getRestPackages(pandinPackages);

			if (restPandingPackages.size() > 0) {
				return true;
			} else {
				return false;
			}

		}

		System.out.println("End rest panding Pack & Pandig Pack Size " + pandinPackages.size());

		return false;

	}

	private boolean setRestUpdateAppPandingPackages() {

		setUpdateApprovalPack();

		if (upPandingPackages != null) {

			restUpdateApprovalPandingPacks = packageMapper.getPackageListView(upPandingPackages);

			if (restUpdateApprovalPandingPacks != null) {

				return true;
			}
		}

		return false;
	}

	private void setUpdateApprovalPack() {

		if (upPandingPackages == null) {
			upPandingPackages = packageServices.getAllUpdatePandingApprovPackage();
		} else {

			long size = upPandingPackages.size();
			long count = packageServices.getCount();

			if (size != count) {
				upPandingPackages = packageServices.getAllUpdatePandingApprovPackage();
			}
		}

	}

	private boolean setRestRejectPackages() {

		setRejectPackages();

		if (rejPackages != null) {

			restViewRejectPacks = packageMapper.getPackageListView(rejPackages);

			if (restViewRejectPacks != null) {

				return true;
			}

		}

		return false;
	}

	private void setRejectPackages() {

		if (rejPackages == null) {

			rejPackages = packageServices.getAllRejectPackages();
		} else {

			long size = rejPackages.size();
			long count = packageServices.getCount();

			if (size != count) {
				rejPackages = packageServices.getAllRejectPackages();
			}
		}

	}

	private void refreshAllPacks() {

		pandinPackages = packageServices.getAllPandingPackages();
		confPackages = packageServices.getAllPandingPackages();

	}

	private void setPackagePendinList() {

		if (pandinPackages == null) {

			pandinPackages = packageServices.getAllPandingPackages();
		} else {

			long size = pandinPackages.size();
			long count = packageServices.getCount();

			if (size != count) {
				pandinPackages = packageServices.getAllPandingPackages();
			}
		}

	}

	private void initVariables() {

		setCategories();
		setPackCats();
		setCountries();
		setVendors();

	}

	private void setVendors() {

		if (vendors == null) {

			vendors = vendorServices.getAllVendors();
		} else {

			long size = vendors.size();
			long count = vendorServices.getCount();

			if (size != count) {

				vendors = vendorServices.getAllVendors();
			}
		}
	}

	private void setCountries() {

		if (countries == null) {

			countries = countryServices.getAllCountries();
		} else {

			long size = countries.size();
			long count = countryServices.getCount();

			if (size != count) {

				countries = countryServices.getAllCountries();
			}
		}

	}

	private void setPackCats() {

		if (packageCats == null) {

			packageCats = packageCatServices.getAllPackCats();
		} else {

			long size = packageCats.size();
			long count = packageCatServices.getCount();

			if (size != count) {

				packageCats = packageCatServices.getAllPackCats();
			}
		}

	}

	private void setPakagesConfirm() {

		if (confPackages == null) {

			confPackages = packageServices.getAllConfrimPackages();
		} else {

			long size = confPackages.size();
			long count = packageServices.getCount();

			if (size != count) {

				confPackages = packageServices.getAllConfrimPackages();
			}
		}
	}

	private void setCategories() {

		if (categories == null) {

			categories = categoryServices.getAllCats();
		} else {

			long size = categories.size();
			long count = categoryServices.getCount();

			if (size != count) {

				categories = categoryServices.getAllCats();
			}
		}

	}

}
