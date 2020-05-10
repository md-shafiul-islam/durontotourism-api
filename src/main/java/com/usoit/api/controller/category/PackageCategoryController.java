package com.usoit.api.controller.category;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.usoit.api.data.model.Access;
import com.usoit.api.data.model.Category;
import com.usoit.api.data.model.PackageCat;
import com.usoit.api.data.model.User;
import com.usoit.api.services.CategoryServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.PackageCatServices;

import javassist.expr.NewArray;

@Controller
@RequestMapping("/pack-cat")
public class PackageCategoryController {

	@Autowired
	private PackageCatServices packCategoryServices;

	@Autowired
	private HelperServices helperServices;

	private List<PackageCat> packCategories;

	private User cUser;

	@RequestMapping("")
	public String getPackCategoryIndexPage(Model model, HttpSession httpSession) {

		return "redirect:/pack-cat/view?page=0";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET, params = { "page" })
	public String getPackCategoryViewPage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "page") int page) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
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

				if (access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/";
				}

			}
		}
		// Access End

		setPackCategory();

		if (packCategories == null) {

			packCategories = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > packCategories.size()) {

			totalPage = (packCategories.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), packCategories.size());

		model.addAttribute("packCategories", packCategories.subList(fIndex, tIndex));
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("cPage", page);

		return "/pages/pack-cat/view";
	}

	@RequestMapping("/add")
	public String getPackCategoryAddPage(Model model, HttpSession httpSession) {

		model.addAttribute("fPackCat", new PackageCat());

		return "/pages/pack-cat/add";
	}

	@RequestMapping(value = "/add-data", method = RequestMethod.POST)
	public String getPackCategoryAddAction(Model model, @ModelAttribute("packCat") PackageCat packageCat) {

		if (packageCat != null) {

			if (packageCat.getName() != null) {

				if (!packCategoryServices.save(packageCat)) {

					return "redirect:/failure/ns";
				} else {
					System.out.println("Save Done!!");
				}
			}
		}

		return "redirect:/pack-cat/view?page=0";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = { "id" })
	public String getPackCategoryEditOrUpdatePage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "id") int id) {

		System.out.println("Category Update fnc Run!! & ID: " + id);

		PackageCat category = null;

		if (id > 0) {

			category = packCategoryServices.getPackCatById(id);
		}

		if (category == null) {
			return "redirect:/failure/nf?id=" + id;
		}

		model.addAttribute("preSetPackCat", category);
		model.addAttribute("fPackCat", new PackageCat());

		return "/pages/pack-cat/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String getPackCategoryUpdateAction(Model model, HttpSession httpSession,
			@ModelAttribute("packCat") PackageCat packageCat) {

		if (packageCat != null) {

			if (packageCat.getId() > 0) {

				if (!packCategoryServices.update(packageCat)) {

					return "redirect:/failure/ud";
				} else {
					refreshPackCategory();
				}
			}
		}

		return "redirect:/pack-cat/view?page=0";
	}

	private void refreshPackCategory() {

		packCategories = packCategoryServices.getAllPackCats();

	}

	private void setPackCategory() {

		if (packCategories == null) {

			packCategories = packCategoryServices.getAllPackCats();
		} else {

			long size = packCategories.size();
			long count = packCategoryServices.getCount();

			if (size != count) {

				packCategories = packCategoryServices.getAllPackCats();
			}

		}

	}

}
