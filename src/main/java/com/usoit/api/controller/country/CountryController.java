package com.usoit.api.controller.country;

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

import com.usoit.api.data.model.Category;
import com.usoit.api.data.model.Country;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.HelperServices;

@Controller
@RequestMapping("/country")
@SessionAttributes(names = {"cUser"})
public class CountryController {

	@Autowired
	private CountryServices countryServices;
	
	@Autowired
	private HelperServices helperServices;

	private List<Country> countries;

	@RequestMapping("")
	public String getCountryIndexPage(Model model, HttpSession httpSession) {

		return "redirect:/country/view?page=0";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET, params = { "page" })
	public String getCountryViewPage(Model model, HttpSession httpSession, @RequestParam(defaultValue = "0", value = "page") int page) {

		setCountry();

		if (countries == null) {

			countries = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > countries.size()) {

			totalPage = (countries.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), countries.size());

		model.addAttribute("countries", countries.subList(fIndex, tIndex));
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("cPage", page);

		return "/pages/country/view";
	}

	@RequestMapping("/add")
	public String getCountryAddPage(Model model, HttpSession httpSession) {

		model.addAttribute("fCountry", new Category());

		return "/pages/country/add";
	}

	@RequestMapping(value = "/add-data", method = RequestMethod.POST)
	public String getCountryAddAction(Model model, @ModelAttribute("country") Country country) {

		if (country != null) {

			if (country.getName() != null) {

				if (!countryServices.save(country)) {

					return "redirect:/failure/ns";
				} else {
					System.out.println("Save Done!!");
				}
			}
		}

		return "redirect:/country/view?page=0";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = { "id" })
	public String getCountryEditOrUpdatePage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "id") int id) {

		System.out.println("country Update fnc Run!! & ID: " + id);
		
		
		Country country = null;

		if (id > 0) {

			country = countryServices.getCountryById(id);
		}

		if (country == null) {
			return "redirect:/failure/nf?id=" + id;
		}

		model.addAttribute("preSetCountry", country);
		model.addAttribute("fCountry", new Country());

		return "/pages/country/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String getCountryUpdateAction(Model model, HttpSession httpSession,
			@ModelAttribute("country") Country country) {

		if (country != null) {

			if (country.getId() > 0) {

				if (!countryServices.update(country)) {

					return "redirect:/failure/ud";
				} else {
					refreshCountry();
				}
			}
		}

		return "redirect:/country/view?page=0";
	}

	private void refreshCountry() {

		countries = countryServices.getAllCountries();

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
