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

import com.usoit.api.model.Access;
import com.usoit.api.model.Category;
import com.usoit.api.model.User;
import com.usoit.api.services.CategoryServices;
import com.usoit.api.services.HelperServices;

import javassist.expr.NewArray;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private HelperServices helperServices;

	private List<Category> categories;

	private User cUser;

	@RequestMapping("")
	public String getCategoryIndexPage(Model model, HttpSession httpSession) {

		return "redirect:/category/view?page=0";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET, params = { "page" })
	public String getCategoryViewPage(Model model, HttpSession httpSession,
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

		setCategory();

		if (categories == null) {

			categories = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > categories.size()) {

			totalPage = (categories.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), categories.size());

		model.addAttribute("categories", categories.subList(fIndex, tIndex));
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("cPage", page);

		return "/pages/category/view";
	}

	@RequestMapping("/add")
	public String getCategoryAddPage(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
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

				if (access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access End

		model.addAttribute("fCat", new Category());

		return "/pages/category/add";
	}

	@RequestMapping(value = "/add-data", method = RequestMethod.POST)
	public String getCategoryAddAction(Model model, @ModelAttribute("cat") Category category, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
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

				if (access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access End

		if (category != null) {

			if (category.getName() != null) {

				if (!categoryServices.save(category)) {

					return "redirect:/failure/ns";
				} else {
					System.out.println("Save Done!!");
				}
			}
		}

		return "redirect:/category/view?page=0";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = { "id" })
	public String getCategoryEditOrUpdatePage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
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

				if (access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access End

		System.out.println("Category Update fnc Run!! & ID: " + id);

		Category category = null;

		if (id > 0) {

			category = categoryServices.getCategoryById(id);
		}

		if (category == null) {
			return "redirect:/failure/nf?id=" + id;
		}

		model.addAttribute("preSetCat", category);
		model.addAttribute("fCat", new Category());

		return "/pages/category/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String getCategoryUpdateAction(Model model, HttpSession httpSession,
			@ModelAttribute("category") Category category) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
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

				if (access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access End

		if (category != null) {

			if (category.getId() > 0) {

				if (!categoryServices.update(category)) {

					return "redirect:/failure/ud";
				} else {
					refreshCategory();
				}
			}
		}

		return "redirect:/category/view?page=0";
	}

	private void refreshCategory() {

		categories = categoryServices.getAllCats();

	}

	private void setCategory() {

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
