package com.usoit.api.controller.department;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.model.Access;
import com.usoit.api.data.model.Department;
import com.usoit.api.data.model.User;
import com.usoit.api.services.DepartmentServices;
import com.usoit.api.services.HelperServices;

@Controller
@RequestMapping("/department")
@SessionAttributes(names = { "currentUser" })
public class DepartmentController {

	@Autowired
	private DepartmentServices departmentServices;

	@Autowired
	private HelperServices helperServices;

	private List<Department> departments;

	private User cUser;

	@RequestMapping("")
	public String getDepaertmentIndexPage(Model model, HttpSession httpSession) {

		System.out.println("Department Index page Run ... ");

		return "redirect:/department/view?page=0";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET, params = { "page" })
	public String getDepartmentViewPage(Model model, HttpSession httpSession,
			@RequestParam(value = "page", defaultValue = "0") int page) {

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

		setDepartmentList();

		if (departments == null) {
			departments = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > departments.size()) {

			totalPage = (departments.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), departments.size());

		model.addAttribute("departments", departments.subList(fIndex, tIndex));

		model.addAttribute("cPage", page);
		model.addAttribute("totalPAge", totalPage);

		return "/pages/department/view";
	}

	@RequestMapping(value = "/add")
	public String getDepartmentAddPage(Model model, HttpSession httpSession,
			@RequestParam(value = "page", defaultValue = "0") int page) {

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

		model.addAttribute("fDepartment", new Department());

		return "/pages/department/add";
	}

	@RequestMapping(value = "/add-department", method = RequestMethod.POST)
	public String getAddDepartmentAction(Model model, @ModelAttribute("department") Department department,
			HttpSession httpSession) {

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

		if (department != null) {

			if (department.getName() != null) {

				if (!departmentServices.save(department)) {

					return "redirect:/failure/ns";
				} else {
					System.out.println("save Done!!");
				}
			}
		}

		return "redirect:/department/view?page=0";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = { "id" })
	public String getDepartmentEditPage(Model model, HttpSession httpSession, @RequestParam("id") int id) {

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

		Department department = null;

		if (id > 0) {
			department = departmentServices.getDepartmentById(id);
		}

		if (department != null) {
			model.addAttribute("pDepartment", department);
		} else {
			return "redirect:/failure/nf?id=" + id;
		}

		return "/pages/department/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String getDepartmentUpdateAction(Model model, HttpSession httpSession,
			@ModelAttribute("department") Department department) {

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

		if (department != null) {

			if (department.getId() > 0) {

				if (!departmentServices.update(department)) {
					return "redirect:/failure/ud?id=" + department.getId();
				} else {
					refreshDepartmentList();
				}
			}
		}

		return "redirect:/department/view?page=0";
	}

	private void refreshDepartmentList() {

		departments = departmentServices.getAllDepartments();

	}

	private void setDepartmentList() {

		if (departments == null) {

			departments = departmentServices.getAllDepartments();
		} else {

			long size = departments.size();
			long count = departmentServices.getCount();

			if (size != count) {
				departments = departmentServices.getAllDepartments();
			}
		}

	}

}
