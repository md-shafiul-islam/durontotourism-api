package com.usoit.api.controller.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.dom4j.io.STAXEventReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.usoit.api.data.vo.AjVendor;
import com.usoit.api.model.Access;
import com.usoit.api.model.AccessType;
import com.usoit.api.model.Address;
import com.usoit.api.model.AjaxId;
import com.usoit.api.model.Category;
import com.usoit.api.model.ContactPerson;
import com.usoit.api.model.Country;
import com.usoit.api.model.ImageGallery;
import com.usoit.api.model.Itarnary;
import com.usoit.api.model.ItarnaryDescription;
import com.usoit.api.model.PackageCat;
import com.usoit.api.model.Packages;
import com.usoit.api.model.Source;
import com.usoit.api.model.User;
import com.usoit.api.model.Vendor;
import com.usoit.api.services.CategoryServices;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.ImageGalleryServices;
import com.usoit.api.services.PackageCatServices;
import com.usoit.api.services.PackageServices;
import com.usoit.api.services.UserServices;
import com.usoit.api.services.VendorServices;

@Controller
@RequestMapping("/product")
@SessionAttributes(names = { "currentUser", "currentPackage", "selectedPack" })
public class ProductController {

	@Autowired
	private PackageServices packageServices;

	@Autowired
	private PackageCatServices packageCatServices;

	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private CountryServices countryServices;

	@Autowired
	private VendorServices vendorServices;

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private ImageGalleryServices imageGalleryServices;

	@Autowired
	private UserServices userServices;

	private List<Vendor> vendors;

	private List<Country> countries;

	private List<PackageCat> packCats;

	private List<Category> categories;

	private List<Packages> pandinPackages;

	private List<Packages> conPackages;

	private List<Packages> rejPackages;

	private User cUser;

	private List<Packages> updateAprovalPackList;

	@RequestMapping("")
	public String getProductIndexPage(Model model, HttpSession httpSession) {

		return "redirect:/product/tour-package/view?page=0";
	}

	@RequestMapping("/tour-package")
	public String getTourPackIndexPage(Model model, HttpSession httpSession) {

		System.out.println("Pakage Index Run");
		return "redirect:/product/tour-package/view?page=0";
	}

	@RequestMapping("/tour-package/")
	public String getTourPackIndexPage2(Model model, HttpSession httpSession) {
		System.out.println("Pakage Index Run 2");
		return "redirect:/product/tour-package/view?page=0";
	}

	@RequestMapping(value = "/tour-package/view", method = RequestMethod.GET, params = { "page" })
	public String getTourPackViewPage(Model model, HttpSession httpSession) {

		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();
		if (access != null) {

			System.out.println("No Access: " + access.getNoAccess());

			if (0 >= access.getNoAccess()) {

				if (access.getView() == 1 || access.getAdd() == 1 || access.getApprove() == 1
						|| access.getUpdateApproval() == 1 || access.getEdit() == 1) {

					setPakagesConfirm();

					if (conPackages == null) {

						conPackages = new ArrayList<>();
					}

					model.addAttribute("packages", conPackages);

					return "/pages/tourpackage/view";

				} else {
					return "redirect:/";
				}

			}
		}

		return "redirect:/user/login";

	}

	@RequestMapping(value = "/tour-package/add")
	public String getTourPackAddPage(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		initVariables();

		Packages packages = null;

		packages = (Packages) httpSession.getAttribute("currentPackage");

		model.addAttribute("fPack", new Packages());

		if (packages == null) {

			packages = new Packages();
			packages.setItarnarys(new ArrayList<>());

			Itarnary itarnary = new Itarnary();


			packages.getItarnarys().add(itarnary);

			packages.setImageGalleries(new ArrayList<>());
			packages.getImageGalleries().add(new ImageGallery());

			packages.getImageGalleries().add(new ImageGallery());
			packages.getImageGalleries().add(new ImageGallery());
			packages.getImageGalleries().add(new ImageGallery());
			packages.getImageGalleries().add(new ImageGallery());

			packages.setCountries(new ArrayList<>());
			packages.getCountries().add(new Country());

			model.addAttribute("currentPackage", packages);
		} else {
			model.addAttribute("currentPackage", packages);

		}

		model.addAttribute("vendors", vendors);
		model.addAttribute("categoryes", categories);
		model.addAttribute("pacCats", packCats);
		model.addAttribute("countryList", countries);

		return "/pages/tourpackage/add";
	}

	@RequestMapping(value = "/tour-package/add-data", method = RequestMethod.POST)
	public String getAddPackageAction(Model model, @ModelAttribute("turPack") Packages packages,
			HttpSession httpSession) {

		System.out.println("Pakage Add action Run !!");

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		if (packages != null) {

			System.out.println("Pack Not Null");

			if (packages.getItarnarys() != null) {

				System.out.println("Itarnary Not Null");

				if (packages.getItarnarys().size() > 0) {

					for (Itarnary item : packages.getItarnarys()) {

						if (item.getFile() != null) {

							System.out.println("File One is not null");

							item.setSourceUrl(helperServices.uploadImageAndGetUrl(item.getFile(), "source-one"));
						}

						if (item.getFil2() != null) {

							System.out.println("File two is not null");

							item.setSourceUrl2(helperServices.uploadImageAndGetUrl(item.getFil2(), "source-two"));
						}
					}

					if (packages.getImageGalleries() != null) {

						for (ImageGallery imageItem : packages.getImageGalleries()) {
							imageItem.setPackages(packages);
							imageItem.setDate(new Date());

							imageItem.setSrcUrl(
									helperServices.uploadImageAndGetUrl(imageItem.getFile(), "image-gallery"));
						}
					}

					packages.setUser(cUser);

					if (!packageServices.save(packages)) {

						return "redirect:/failure/ns";
					}

					System.out.println("Vendo Not Null");

					for (Itarnary item : packages.getItarnarys()) {

						if (item.getVendor() != null) {
							System.out.println("Vendor Name: " + item.getVendor().getCompanyName() + "Package ID: "
									+ packages.getId());
						}

					}
				}
			}
		}

		return "redirect:/product/tour-package/view?page=0";
	}

	@RequestMapping(value = "/tour-package/add-item", method = RequestMethod.POST)
	public String getAddItemAction(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages packages = (Packages) model.getAttribute("currentPackage");

		if (packages != null) {

			if (packages.getItarnarys() != null) {

				int count = 0;

				for (Itarnary itarnary : packages.getItarnarys()) {

					System.out.println("Item count: " + count + " Name: " + itarnary.getHeading());
					count++;
				}

				packages.getItarnarys().add(new Itarnary());
			} else {

				packages.setItarnarys(new ArrayList<>());
			}
		}

		model.addAttribute("currentPackage", packages);

		return "redirect:/product/tour-package/add";
	}

	@RequestMapping(value = "/tour-package/add-itinerary")
	public String getItemAction(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages packages = (Packages) model.getAttribute("currentPackage");

		if (packages == null) {
			System.out.println("package null");
		} else {

			if (packages.getItarnarys() != null) {

				packages.getItarnarys().add(new Itarnary());

				model.addAttribute("currentPackage", packages);
			}
		}

		return "redirect:/product/tour-package/add";
	}

	@RequestMapping(value = "/vendor-detail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getVendorAjaxAction(@Valid @RequestBody AjaxId aID, Errors errors,
			HttpSession httpSession) {

		System.out.println("Vendor Run Via Ajax");

		AjVendor result = new AjVendor();

		// If error, just return a 400 bad request, along with the error message
		if (errors.hasErrors()) {

			result.setMsg(
					errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
			return ResponseEntity.badRequest().body(result);

		}

		Vendor vendor = null;

		if (0 > aID.getId()) {
			result.setMsg("Vendor not found!");
		} else {

			vendor = vendorServices.getVendorById(aID.getId());
			result.setMsg("success");
		}

		return ResponseEntity.ok(vendor);

	}

	@RequestMapping(value = "/tour-package/add-image")
	private String addImageToPackageAction(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages packages = (Packages) model.getAttribute("currentPackage");

		if (packages == null) {
			System.out.println("package null");
		} else {

			if (packages.getImageGalleries() != null) {

				packages.getImageGalleries().add(new ImageGallery());

				model.addAttribute("currentPackage", packages);
			}
		}

		return "redirect:/product/tour-package/add";
	}

	@RequestMapping(value = "/tour-package/add-country")
	private String addCountryToPackageAction(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages packages = (Packages) model.getAttribute("currentPackage");

		if (packages == null) {
			System.out.println("package null");
		} else {

			if (packages.getCountries() != null) {

				packages.getCountries().add(new Country());

				model.addAttribute("currentPackage", packages);
			}
		}

		return "redirect:/product/tour-package/add";
	}

	@RequestMapping(value = "/tour-package/remove-country", method = RequestMethod.GET)
	public String getCountryRemoveAction(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages packages = (Packages) model.getAttribute("currentPackage");

		if (packages == null) {
			System.out.println("package null");
		} else {

			if (packages.getCountries() != null) {

				int index = packages.getCountries().size() - 1;

				if (index >= 0) {
					packages.getCountries().remove(index);
				}

				model.addAttribute("currentPackage", packages);
			}
		}

		return "redirect:/product/tour-package/add";
	}

	@RequestMapping(value = "/tour-package/remove-itinerary", method = RequestMethod.GET)
	public String getItarnaryyRemoveAction(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages packages = (Packages) model.getAttribute("currentPackage");

		if (packages == null) {
			System.out.println("package null");
		} else {

			if (packages.getCountries() != null) {

				int index = packages.getItarnarys().size() - 1;

				if (index >= 0) {
					packages.getItarnarys().remove(index);
				}

				model.addAttribute("currentPackage", packages);
			}
		}

		return "redirect:/product/tour-package/add";
	}

	@RequestMapping(value = "/tour-package/remove-image", method = RequestMethod.GET)
	public String getImageRemoveAction(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages packages = (Packages) model.getAttribute("currentPackage");

		if (packages == null) {
			System.out.println("package null");
		} else {

			if (packages.getImageGalleries() != null) {

				int index = packages.getImageGalleries().size() - 1;

				if (index >= 0) {
					packages.getImageGalleries().remove(index);
				}

				model.addAttribute("currentPackage", packages);
			}
		}

		return "redirect:/product/tour-package/add";
	}

	@RequestMapping(value = "/tour-package/approval")
	public String getPandingPackageList(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 ||access.getApprove() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		setPackagePendinList();

		if (pandinPackages == null) {
			pandinPackages = new ArrayList<>();
		}

		for (Packages pack : pandinPackages) {
			System.out.println("Name:" + pack.getName() + "Code: " + pack.getCode());
		}

		model.addAttribute("packages", pandinPackages);

		return "/pages/tourpackage/approval-pending";
	}

	@RequestMapping(value = "/tour-package/detail", method = RequestMethod.GET, params = { "id" })
	public String getPackDetailPage(Model model, HttpSession httpSession, @RequestParam("id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			}
		}
		// Access Start

		Packages packages = null;

		if (id > 0) {
			packages = packageServices.getPackageById(id);
		}

		model.addAttribute("pack", packages);

		return "/pages/tourpackage/package-detail";
	}

	@RequestMapping(value = "/tour-package/approve", method = RequestMethod.GET, params = { "id" })
	public String getPackApproveAction(Model model, HttpSession httpSession, @RequestParam("id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getApprove() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		System.out.println("Package ID: " + id);

		if (id > 0) {

			if (!packageServices.approvePackageById(id, cUser)) {

				return "redirect:/failure/apf";
			} else {

				refreshAllPacks();
			}
		}

		return "redirect:/product/tour-package";

	}

	@RequestMapping(value = "/tour-package/reject", method = RequestMethod.GET, params = { "id" })
	public String getPackageRejectAction(Model model, HttpSession httpSession, @RequestParam("id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getApprove() == 1 || access.getAll() == 1 || access.getAdd() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		System.out.println("Package ID: " + id);

		if (id > 0) {

			if (!packageServices.rejectPackageById(id, cUser)) {

				return "redirect:/failure/rjf";
			} else {

				refreshAllPacks();
			}
		}

		return "redirect:/product/tour-package";
	}

	@RequestMapping(value = "/tour-package/rej-view", method = RequestMethod.GET, params = { "page" })
	public String getRejectViewPage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "page") int page) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getApprove() == 1 || access.getAll() == 1 || access.getAdd() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		setRejectPackages();

		model.addAttribute("packages", rejPackages);

		return "/pages/tourpackage/rej-view";

	}

	@RequestMapping(value = "/tour-package/edit", method = RequestMethod.GET, params = { "id" })
	public String getPackageEditPage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		setCountries();
		setPackCats();
		setVendors();
		setCategories();

		Packages packages = null;

		Packages packSession = (Packages) model.getAttribute("selectedPack");

		if (id > 0) {
			packages = packageServices.getPackageById(id);

		}

		if (packages == null) {
			return "redirect:/failure/nf?id=" + id;
		}

		if (packSession != null) {

			if (packSession.getCountries().size() > 0 && packSession.getItarnarys().size() > 0) {

				model.addAttribute("selectedPack", packSession);
			}
		} else {
			model.addAttribute("selectedPack", packages);
		}

		if (packSession != null) {

			if (id != packSession.getId()) {
				model.addAttribute("selectedPack", packages);
			} else {
				model.addAttribute("selectedPack", packSession);
			}
		}

		model.addAttribute("fPack", new Packages());

		model.addAttribute("countryList", countries);
		model.addAttribute("pacCats", packCats);
		model.addAttribute("vendors", vendors);
		model.addAttribute("categoryes", categories);

		return "/pages/tourpackage/edit";
	}

	@RequestMapping(value = "/tour-package/update", method = RequestMethod.POST)
	public String getEditOrUpdateAction(Model model, HttpSession httpSession,
			@ModelAttribute("turPack") Packages packages) {

		System.out.println("Update !!!");

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		if (packages != null) {

			System.out.println("Update Pack Not Null !!");

			System.out.println("ID: " + packages.getId());

			if (packages.getId() > 0) {

				System.out.println("Update Pack ID PASS!!");

				System.out.println("***************** **************** **************");
				System.out.println("***************** **************** **************");

				System.out.println("");

				System.out.println("pack ID: " + packages.getId());
				System.out.println("pack Code: " + packages.getCode());
				System.out.println("pack Name: " + packages.getName());
				System.out.println("pack Price: " + packages.getPrice());

				PackageCat pCat = packages != null ? packages.getPackageCat() : null;

				String PCatName = pCat != null ? pCat.getName() : "Not Set";

				System.out.println("package Category: " + PCatName);

				System.out.println("");

				System.out.println("***************** **************** **************");
				System.out.println("***************** **************** **************");

				if (packages.getItarnarys() != null) {

					for (Itarnary itnItem : packages.getItarnarys()) {

						System.out.println("ID: " + itnItem.getId());
						System.out.println("Heading : " + itnItem.getHeading());
						System.out.println("HightLight : " + itnItem.getHightLightText());
						System.out.println("Description : " + itnItem.getDescription());
						System.out.println("Included : " + itnItem.getIncludedText());
						System.out.println("Excluded : " + itnItem.getExcludedText());
						System.out.println("Hotel Category : " + itnItem.getCategory().getName());
						System.out.println("Hotel Name: : " + itnItem.getHotelText());
						System.out.println("Country: : " + itnItem.getCountry().getName());
						System.out.println("City: : " + itnItem.getCity());
						System.out.println("Source Info: Vendor " + itnItem.getVendor().getId());

					}
				}

				Packages packages2 = (Packages) model.getAttribute("selectedPack");

				packages.setApproveUser(packages2.getApproveUser());
				packages.setUser(packages2.getUser());
				packages.setDate(packages2.getDate());
				packages.setDateGroup(packages2.getDateGroup());

				if (packages.getItarnarys() != null) {

					for (Itarnary itarnary : packages.getItarnarys()) {
						
						if (itarnary.getFile() != null) {
							itarnary.setSourceUrl(helperServices.uploadImageAndGetUrl(itarnary.getFile(), "source-one"));
						}
						
						if (itarnary.getFil2() != null) {
							
							itarnary.setSourceUrl2(helperServices.uploadImageAndGetUrl(itarnary.getFil2(), "source-two"));
						}
						
						for (Itarnary itarnary2 : packages2.getItarnarys()) {

							if (itarnary.getId() == itarnary2.getId()) {
								
								if (itarnary.getSourceUrl() == null) {
									
									itarnary.setSourceUrl(itarnary2.getSourceUrl());
								}
								
								if (itarnary.getSourceUrl2() == null) {
									
									itarnary.setSourceUrl2(itarnary2.getSourceUrl2());
								}

							}
						}

					}
				}
				
				if (packages2.getImageGalleries() != null ) {
					
					for (ImageGallery gallery : packages.getImageGalleries()) {
						
						gallery.setPackages(packages);
						gallery.setDate(new Date());
						
						if (gallery.getFile() != null) {
							
							gallery.setSrcUrl(helperServices.uploadImageAndGetUrl(gallery.getFile(), "image-gallery"));
						}
						
						for (ImageGallery gallery2 : packages2.getImageGalleries()) {
							
							if (gallery.getId() == gallery2.getId()) {
								
								if (gallery.getSrcUrl() == null) {
									gallery.setSrcUrl(gallery2.getSrcUrl());
								}
								
								
							}
						}
						
					}
				}

				if (!packageServices.updatePack(packages)) {
					return "redirect:/failure/nud?id=" + packages.getId();
				} else {
					model.addAttribute("selectedPack", null);
				}
			}
		}

		return "redirect:/product/tour-package/view?page=0";
	}

	@RequestMapping(value = "/tour-package/update-add-country", method = RequestMethod.GET)
	public String getUpdateAddCountry(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages sPack = (Packages) model.getAttribute("selectedPack");

		if (sPack != null) {

			if (sPack.getCountries() != null) {
				sPack.getCountries().add(new Country());
			} else {
				sPack.setCountries(new ArrayList<>());
				sPack.getCountries().add(new Country());
			}
		}

		model.addAttribute("selectedPack", sPack);
		return "redirect:/product/tour-package/edit?id=" + sPack.getId();
	}

	@RequestMapping(value = "/tour-package/update-remove-country", method = RequestMethod.GET)
	public String getEditRemoveCountry(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages sPack = (Packages) model.getAttribute("selectedPack");

		if (sPack != null) {
			int size = sPack.getCountries().size();
			if (size > 0) {

				sPack.getCountries().remove(size - 1);

			}
		}

		model.addAttribute("selectedPack", sPack);
		return "redirect:/product/tour-package/edit?id=" + sPack.getId();
	}

	@RequestMapping(value = "/tour-package/edit-add-itinerary", method = RequestMethod.GET)
	public String getUpdateAddItarnary(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages sPack = (Packages) model.getAttribute("selectedPack");

		if (sPack != null) {

			if (sPack.getImageGalleries() != null) {
				sPack.getItarnarys().add(new Itarnary());
			} else {
				sPack.setItarnarys(new ArrayList<>());
				sPack.getItarnarys().add(new Itarnary());
			}
		}

		model.addAttribute("selectedPack", sPack);
		return "redirect:/product/tour-package/edit?id=" + sPack.getId();
	}

	@RequestMapping(value = "/tour-package/remove-edit-itinerary", method = RequestMethod.GET)
	public String getEditRemoveItarnary(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages sPack = (Packages) model.getAttribute("selectedPack");

		if (sPack != null) {
			int size = sPack.getItarnarys().size();
			if (size > 0) {

				sPack.getItarnarys().remove(size - 1);
			}
		}

		model.addAttribute("selectedPack", sPack);
		return "redirect:/product/tour-package/edit?id=" + sPack.getId();
	}

	@RequestMapping(value = "/tour-package/edit-add-image", method = RequestMethod.GET)
	public String getUpdateAddImage(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages sPack = (Packages) model.getAttribute("selectedPack");

		if (sPack != null) {

			if (sPack.getImageGalleries() != null) {
				sPack.getImageGalleries().add(new ImageGallery());
			} else {
				sPack.setImageGalleries(new ArrayList<>());
				sPack.getImageGalleries().add(new ImageGallery());
			}
		}

		model.addAttribute("selectedPack", sPack);
		return "redirect:/product/tour-package/edit?id=" + sPack.getId();
	}

	@RequestMapping(value = "/tour-package/remove-edit-image", method = RequestMethod.GET)
	public String getEditRemoveImage(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		Packages sPack = (Packages) model.getAttribute("selectedPack");

		if (sPack != null) {
			int size = sPack.getImageGalleries().size();
			if (size > 0) {

				sPack.getImageGalleries().remove(size - 1);
			}
		}

		model.addAttribute("selectedPack", sPack);
		return "redirect:/product/tour-package/edit?id=" + sPack.getId();
	}

	@RequestMapping(value = "/tour-package/update-approval")
	public String getUpdateApprovalViewPage(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1 || access.getUpdateApproval() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		setUpdateApprovalPack();

		if (updateAprovalPackList == null) {
			updateAprovalPackList = new ArrayList<>();
		}

		model.addAttribute("packages", updateAprovalPackList);

		return "/pages/tourpackage/update-approval-view";
	}

	@RequestMapping(value = "/tour-package/update-approve", method = RequestMethod.GET, params = { "id" })
	public String getPackUpdateApproveAction(Model model, HttpSession httpSession, @RequestParam("id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/product/tour-package/view?page=0";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/product/tour-package/view?page=0";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getUpdateApproval() == 1 || access.getAll() == 1) {

					System.out.println("Package Update access Pass !!");

				} else {
					return "redirect:/product/tour-package/view?page=0";
				}

			}
		}
		// Access Start

		System.out.println("Package ID: " + id);

		if (id > 0) {

			if (!packageServices.approveUpdatePackageById(id, cUser)) {

				return "redirect:/failure/apf";
			} else {

				refreshAllPacks();
			}
		}

		return "redirect:/product/tour-package";

	}

	private void setUpdateApprovalPack() {

		if (updateAprovalPackList == null) {
			updateAprovalPackList = packageServices.getAllUpdatePandingApprovPackage();
		} else {

			long size = updateAprovalPackList.size();
			long count = packageServices.getCount();

			if (size != count) {
				updateAprovalPackList = packageServices.getAllUpdatePandingApprovPackage();
			}
		}

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
		conPackages = packageServices.getAllPandingPackages();

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

		if (packCats == null) {

			packCats = packageCatServices.getAllPackCats();
		} else {

			long size = packCats.size();
			long count = packageCatServices.getCount();

			if (size != count) {

				packCats = packageCatServices.getAllPackCats();
			}
		}

	}

	private void setPakagesConfirm() {

		if (conPackages == null) {

			conPackages = packageServices.getAllConfrimPackages();
		} else {

			long size = conPackages.size();
			long count = countryServices.getCount();

			if (size != count) {

				conPackages = packageServices.getAllConfrimPackages();
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
