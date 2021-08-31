package com.usoit.api.controller.paymentinf;

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

import com.usoit.api.model.Category;
import com.usoit.api.model.Country;
import com.usoit.api.model.PaymentInfo;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.PaymentInfServices;

@Controller
@RequestMapping("/paymentinf")
@SessionAttributes(names = {"cUser"})
public class PaymentInfController {

	@Autowired
	private PaymentInfServices paymentInfServices;
	
	@Autowired
	private HelperServices helperServices;
	
	
	@Autowired
	private CountryServices countryServices;

	private List<PaymentInfo> paymentInfos;
	
	private List<Country> countries;
	

	@RequestMapping("")
	public String getPaymentInfoIndexPage(Model model, HttpSession httpSession) {

		return "redirect:/paymentinf/view?page=0";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET, params = { "page" })
	public String getPaymentInfoViewPage(Model model, HttpSession httpSession, @RequestParam(defaultValue = "0", value = "page") int page) {

		setPaymentInfo();

		if (paymentInfos == null) {

			countries = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > paymentInfos.size()) {

			totalPage = (paymentInfos.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), paymentInfos.size());

		model.addAttribute("payInfs", paymentInfos.subList(fIndex, tIndex));
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("cPage", page);

		return "/pages/pay-info/view";
	}

	@RequestMapping("/add")
	public String getPaymentInfoAddPage(Model model, HttpSession httpSession) {

		model.addAttribute("fPayInf", new Category());

		return "/pages/pay-info/add";
	}

	@RequestMapping(value = "/add-data", method = RequestMethod.POST)
	public String getPaymentInfoAddAction(Model model, @ModelAttribute("payInf") Country country) {

		if (country != null) {

			if (country.getName() != null) {

				if (!countryServices.save(country)) {

					return "redirect:/failure/ns";
				} else {
					System.out.println("Save Done!!");
				}
			}
		}

		return "redirect:/paymentinf/view?page=0";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = { "id" })
	public String getPaymentInfoEditOrUpdatePage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "id") int id) {

		System.out.println("paymentInfos Update fnc Run!! & ID: " + id);
		
		
		PaymentInfo paymentInfo = null;

		if (id > 0) {

			paymentInfo = paymentInfServices.getPayInfById(id);
		}

		if (paymentInfo == null) {
			return "redirect:/failure/nf?id=" + id;
		}

		model.addAttribute("preSetPayInf", paymentInfo);
		model.addAttribute("fPayInf", new PaymentInfo());

		return "/pages/pay-info/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String getPaymentInfoUpdateAction(Model model, HttpSession httpSession,
			@ModelAttribute("payInf") PaymentInfo paymentInfo) {

		if (paymentInfo != null) {

			if (paymentInfo.getId() > 0) {

				if (!paymentInfServices.update(paymentInfo)) {

					return "redirect:/failure/ud";
				} else {
					refreshPaymentInfo();
				}
			}
		}

		return "redirect:/paymentinf/view?page=0";
	}

	private void refreshPaymentInfo() {

		paymentInfos = paymentInfServices.getAllPayInf();

	}

	private void setPaymentInfo() {

		if (paymentInfos == null) {

			paymentInfos = paymentInfServices.getAllPayInf();
		} else {

			long size = paymentInfos.size();
			long count = paymentInfServices.getCount();

			if (size != count) {

				paymentInfos = paymentInfServices.getAllPayInf();
			}

		}

	}
	
}
