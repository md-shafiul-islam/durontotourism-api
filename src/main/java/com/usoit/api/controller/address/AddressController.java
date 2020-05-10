package com.usoit.api.controller.address;

import java.util.ArrayList;
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

import com.usoit.api.data.model.Address;
import com.usoit.api.data.model.Category;
import com.usoit.api.data.model.Country;
import com.usoit.api.data.model.Vendor;
import com.usoit.api.services.AddressServices;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.HelperServices;

@Controller
@RequestMapping("/address")
@SessionAttributes(names = {"cUser"})
public class AddressController {

	
	@Autowired
	private HelperServices helperServices;
	
	@Autowired
	private AddressServices addressServices;
	
	private List<Address> addresses;
	


	@RequestMapping("")
	public String getCountryIndexPage(Model model, HttpSession httpSession) {

		return "redirect:/address/view?page=0";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET, params = { "page" })
	public String getAddressViewPage(Model model, HttpSession httpSession, @RequestParam(defaultValue = "0", value = "page") int page) {

		setAddress();

		if (addresses == null) {

			addresses = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > addresses.size()) {

			totalPage = (addresses.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), addresses.size());

		model.addAttribute("addresses", addresses.subList(fIndex, tIndex));
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("cPage", page);

		return "/pages/address/view";
	}

	@RequestMapping("/add")
	public String getAddressAddPage(Model model, HttpSession httpSession) {

		model.addAttribute("fAddress", new Address());

		return "/pages/address/add";
	}

	@RequestMapping(value = "/add-data", method = RequestMethod.POST)
	public String getAddressAddAction(Model model, @ModelAttribute("address") Address address) {

		if (address != null) {

			if (address.getHouse() != null) {

				if (!addressServices.save(address)) {

					return "redirect:/failure/ns";
				} else {
					System.out.println("Save Done!!");
				}
			}
		}

		return "redirect:/address/view?page=0";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = { "id" })
	public String getAddressEditOrUpdatePage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "id") int id) {

		System.out.println("Address Update fnc Run!! & ID: " + id);
		
		
		Address address = null;

		if (id > 0) {

			address = addressServices.getAddressById(id);
		}

		if (address == null) {
			return "redirect:/failure/nf?id=" + id;
		}

		model.addAttribute("preSetAddress", address);
		model.addAttribute("fAddress", new Address());

		return "/pages/address/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String getAddressUpdateAction(Model model, HttpSession httpSession,
			@ModelAttribute("address") Address address) {

		if (address != null) {

			if (address.getId() > 0) {

				if (!addressServices.update(address)) {

					return "redirect:/failure/ud";
				} else {
					refreshAddess();
				}
			}
		}

		return "redirect:/address/view?page=0";
	}

	private void refreshAddess() {

		addresses = addressServices.getAllAddress();

	}

	private void setAddress() {

		if (addresses == null) {

			addresses = addressServices.getAllAddress();
		} else {

			long size = addresses.size();
			long count = addressServices.getCount();

			if (size != count) {

				addresses = addressServices.getAllAddress();
			}

		}

	}
	
}
