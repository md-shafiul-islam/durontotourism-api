package com.usoit.api.controller.vendor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.model.Access;
import com.usoit.api.data.model.Address;
import com.usoit.api.data.model.ContactPerson;
import com.usoit.api.data.model.Country;
import com.usoit.api.data.model.Designation;
import com.usoit.api.data.model.PaymentInfo;
import com.usoit.api.data.model.User;
import com.usoit.api.data.model.Vendor;
import com.usoit.api.services.AddressServices;
import com.usoit.api.services.ContactPersonServices;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.DesignationServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.PaymentInfServices;
import com.usoit.api.services.VendorServices;

@Controller
@RequestMapping("/vendor")
@SessionAttributes(names = { "cUser", "currentVendor" })
public class VendorController {

	@Autowired
	private VendorServices vendorServices;

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private AddressServices addressServices;

	@Autowired
	private PaymentInfServices paymentInfServices;

	@Autowired
	private ContactPersonServices contactPersonServices;

	@Autowired
	private CountryServices countryServices;

	@Autowired
	private DesignationServices designationServices;

	private List<Designation> designations;

	private List<Vendor> vendors;
	
	private List<Vendor> pandingVendor;

	private List<Country> countries;

	private List<PaymentInfo> paymanetInfs;

	private List<ContactPerson> contactPersons;

	private User cUser;

	@RequestMapping("")
	public String getVendorIndexPage(Model model, HttpSession httpSession) {

		return "redirect:/vendor/view?page=0";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET, params = { "page" })
	public String getVendorViewPage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "page") int page) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user/login";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1 || access.getApprove() == 1 || access.getView() == 1
						|| access.getEdit() == 1 || access.getUpdateApproval() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		setVendor();

		if (vendors == null) {

			vendors = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > vendors.size()) {

			totalPage = (vendors.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), vendors.size());

		model.addAttribute("vendors", vendors.subList(fIndex, tIndex));
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("cPage", page);

		return "/pages/vendor/view";
	}

	@RequestMapping(value = "/approval", method = RequestMethod.GET, params = {"page"})
	public String getApprovalPendingPage(Model model, HttpSession httSession, @RequestParam("page") int page) {

		// Access Start
		cUser = helperServices.checkUserAccess(httSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user/login";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1 || access.getApprove() == 1 || access.getView() == 1
						|| access.getEdit() == 1 || access.getUpdateApproval() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		setPandingVendor();

		if (pandingVendor == null) {

			pandingVendor = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > vendors.size()) {

			totalPage = (pandingVendor.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), pandingVendor.size());

		model.addAttribute("pVendors", pandingVendor.subList(fIndex, tIndex));
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("cPage", page);

		return "/pages/vendor/view";

	}

	@RequestMapping("/add")
	public String getVendorAddPage(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		setVendor();
		setCountry();

		if (designations == null) {

			designations = new ArrayList<>();
		}

		Vendor vendor = new Vendor();

		Vendor sessionVendor = (Vendor) model.getAttribute("currentVendor");

		vendor.setContactPersons(new ArrayList<>());
		vendor.setAddresses(new ArrayList<>());
		vendor.getContactPersons().add(new ContactPerson());
		vendor.getAddresses().add(new Address());
		vendor.setPaymentInfos(new ArrayList<>());
		vendor.getPaymentInfos().add(new PaymentInfo());

		if (sessionVendor == null) {

			model.addAttribute("currentVendor", vendor);
		} else {
			model.addAttribute("currentVendor", sessionVendor);
		}

		model.addAttribute("fVendor", vendor);

		model.addAttribute("countries", countries);

		return "/pages/vendor/add";
	}

	@RequestMapping(value = "/add-person", method = RequestMethod.GET)
	public String getAddPersonAction(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		Vendor vendor = (Vendor) model.getAttribute("currentVendor");

		vendor.getContactPersons().add(new ContactPerson());

		model.addAttribute("currentVendor", vendor);

		return "redirect:/vendor/add";
	}

	@RequestMapping(value = "/add-paymentinf", method = RequestMethod.GET)
	public String getAddPaymentInf(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		Vendor vendor = (Vendor) model.getAttribute("currentVendor");

		vendor.getPaymentInfos().add(new PaymentInfo());

		model.addAttribute("currentVendor", vendor);

		return "redirect:/vendor/add";
	}

	@RequestMapping(value = "/add-address", method = RequestMethod.GET)
	public String getAddAddress(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		Vendor vendor = (Vendor) model.getAttribute("currentVendor");

		vendor.getAddresses().add(new Address());

		model.addAttribute("currentVendor", vendor);

		return "redirect:/vendor/add";
	}

	@RequestMapping(value = "/add-data", method = RequestMethod.POST)
	public String getVendorAddAction(Model model, @ModelAttribute("vendor") Vendor vendor, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		if (vendor != null) {

			if (vendor.getCompanyName() != null) {

				if (!vendorServices.save(vendor)) {

					return "redirect:/failure/ns";
				} else {
					System.out.println("Save Done!!");
				}
			}
		}

		return "redirect:/vendor/view?page=0";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = { "id" })
	public String getVendorEditOrUpdatePage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAll() == 1 || access.getEdit() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access End

		System.out.println("Vendor Update fnc Run!! & ID: " + id);

		Vendor vendor = null;

		if (id > 0) {

			vendor = vendorServices.getVendorById(id);
		}

		if (vendor == null) {
			return "redirect:/failure/nf?id=" + id;
		}

		model.addAttribute("preSetVendor", vendor);
		model.addAttribute("fVendor", new Vendor());

		return "/pages/vendor/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String getVendorUpdateAction(Model model, HttpSession httpSession, @ModelAttribute("vendor") Vendor vendor) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAll() == 1 || access.getEdit() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		if (vendor != null) {

			if (vendor.getId() > 0) {

				if (!vendorServices.update(vendor)) {

					return "redirect:/failure/ud";
				} else {
					refreshVendor();
				}
			}
		}

		return "redirect:/vendor/view?page=0";
	}

	@RequestMapping(value = "/add-payinf", method = RequestMethod.GET)
	public String getAddPayInfAction(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1 || access.getEdit() == 1
						|| access.getUpdateApproval() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		Vendor vendor = (Vendor) httpSession.getAttribute("currentVendor");

		if (vendor == null) {
			vendor = (Vendor) model.getAttribute("currentVendor");
		}

		if (vendor != null) {
			vendor.getPaymentInfos().add(new PaymentInfo());
		}
		return "redirect:/vendor/add";
	}

	@RequestMapping(value = "/add-addres", method = RequestMethod.GET)
	public String getAddAddressAction(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "vendor", 5);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1 || access.getEdit() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		Vendor vendor = (Vendor) httpSession.getAttribute("currentVendor");

		if (vendor == null) {
			vendor = (Vendor) model.getAttribute("currentVendor");
		}

		if (vendor != null) {
			vendor.getAddresses().add(new Address());
		}
		return "redirect:/vendor/add";
	}

	private void refreshVendor() {

		vendors = vendorServices.getAllVendors();

	}

	private void setVendor() {

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
	
	public void setPandingVendor() {
		
		if (pandingVendor == null) {
			
			pandingVendor = vendorServices.getAllPanding();
		}else {
			
			long vendorSize = pandingVendor.size();
			long vendorCount = vendorServices.getCount();
			
			if (vendorSize != vendorCount) {
				pandingVendor = vendorServices.getAllPanding();
			}
		}
	}
	
	private void setCountry() {

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

}
