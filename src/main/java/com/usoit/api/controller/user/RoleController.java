package com.usoit.api.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.dom4j.io.STAXEventReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.model.Access;
import com.usoit.api.model.AccessType;
import com.usoit.api.model.Role;
import com.usoit.api.model.User;
import com.usoit.api.services.AccessServices;
import com.usoit.api.services.AccessTypeServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.RoleServices;

@Controller
@RequestMapping("/role")
@SessionAttributes(names = { "curentUser" })
public class RoleController {

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private RoleServices roleServices;

	@Autowired
	private AccessServices accessServices;

	@Autowired
	private AccessTypeServices accessTypeServices;

	private List<Role> roles;

	private List<Access> accesses;

	private List<AccessType> accessTypes;

	private User cUser;

	@RequestMapping("")
	public String getRoleIndexPage(Model model, HttpSession httpSession) {

		return "redirect:/role/view?page=0";

	}

	@RequestMapping(value = "/view", method = RequestMethod.GET, params = { "page" })
	public String getRoleViewPage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "page") int page) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "role", 4);
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

				if (access.getAdd() == 1 || access.getAll() == 1 || access.getView() == 1
						|| access.getUpdateApproval() == 1 || access.getEdit() == 1 || access.getApprove() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/";
				}

			}
		}
		// Access Start

		setRoleList();

		if (roles == null) {
			roles = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > roles.size()) {

			totalPage = (roles.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), roles.size());

		model.addAttribute("roles", roles.subList(fIndex, tIndex));

		model.addAttribute("cPage", page);
		model.addAttribute("totalPAge", totalPage);

		return "/pages/role/view";
	}

	@RequestMapping("/add")
	public String getRoleAddPage(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "role", 4);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/";

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
					return "redirect:/";
				}

			}
		}
		// Access Start

		setAccessType();

		Role role = new Role();

		role.setAccesses(new ArrayList<>());

		for (AccessType accessType : accessTypes) {

			Access access2 = new Access();
			access.setAccessType(accessType);
			role.getAccesses().add(access2);

			// role.getAccessTypes().add(accessType);
		}

		if (accessTypes == null) {
			accessTypes = new ArrayList<>();
		}

		model.addAttribute("fRole", role);
		model.addAttribute("accessTypes", accessTypes);

		return "/pages/role/add";
	}

	@RequestMapping(value = "/add-data", method = RequestMethod.POST)
	public String getRoleAddAction(Model model, HttpSession httpSession, @ModelAttribute("role") Role role) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "role", 4);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + " Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/";
				}

			}
		}
		// Access Start

		if (role != null) {

			if (role.getName() != null) {

				System.out.println("Role Name: " + role.getName());

				role.setDate(new Date());
				role.setDateGroupe(new Date());

				if (role.getAccesses() != null) {

					for (Access access2 : role.getAccesses()) {

						if (access2 != null) {

							access2.setRole(role);

							System.out.println("Access Type: " + access.getAccessType().getName());

							System.out.println("************ ****************** *******************");
							System.out.println("No Access: " + access.getNoAccess() + " View: " + access.getView()
									+ " Add & view: " + access.getAdd() + " Add Approv: " + access.getApprove()
									+ " Edit: " + access.getEdit() + " Updaet Approve: " + access.getUpdateApproval()
									+ "Full Access: " + access.getAll());
							System.out.println("************ ****************** *******************");
						}
					}
				}

				if (!roleServices.save(role)) {

					return "redirect:/failure/ns";
				}
			}
		}

		return "redirect:/role/view?page=0";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = { "id" })
	public String getRoleUpdatePage(Model model, HttpSession httpSession, @RequestParam("id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "role", 4);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + " Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/";
				}

			}
		}
		// Access Start

		setAccesses();
		setAccessType();

		Role role = null;

		if (accessTypes == null) {
			accessTypes = new ArrayList<>();
		}

		if (id > 0) {

			role = roleServices.getRoleById(id);

		}

		if (role == null) {
			return "redirect:/failure/nf";
		}

		if (role != null) {

			List<Access> tmpAccess = new ArrayList<Access>();

			System.out.println("Access Types Size: " + accessTypes.size());
			System.out.println("Role Access Size : " + role.getAccesses().size());

			for (AccessType acType : accessTypes) {

				Access tAccess = new Access();
				tAccess.setNoAccess(1);
				tAccess.setAccessType(acType);

				System.out.println("Set TEMP AccessType ID: " + acType.getId() + " Name: " + acType.getName());

				tmpAccess.add(tAccess);
			}

			for (Access tmpAcs : tmpAccess) {

				System.out.println("Temp Access Type ID: " + tmpAcs.getAccessType().getId());

				for (Access roleAccess : role.getAccesses()) {

					if (roleAccess.getAccessType() != null && tmpAcs.getAccessType() != null) {

						System.out.println("Role Type ID: " + roleAccess.getAccessType().getId() + " & temp Type ID: "
								+ tmpAcs.getAccessType().getId());

						if (roleAccess.getAccessType().getId() == tmpAcs.getAccessType().getId()) {

							System.out.println("ID Pass!!");

							tmpAcs.setAdd(roleAccess.getAdd());
							tmpAcs.setAccessType(roleAccess.getAccessType());
							tmpAcs.setAll(roleAccess.getAdd());
							tmpAcs.setApprove(roleAccess.getApprove());
							tmpAcs.setEdit(roleAccess.getEdit());
							tmpAcs.setNoAccess(roleAccess.getNoAccess());

							tmpAcs.setUpdateApproval(roleAccess.getUpdateApproval());
							tmpAcs.setView(roleAccess.getView());

							tmpAcs.setId(roleAccess.getId());

							tmpAcs.setRole(role);

							if (tmpAcs.getId() > 0) {
								System.out.println("After Set Temp Access ID: " + tmpAcs.getId() + " Role ID: "
										+ tmpAcs.getRole().getId());
							}
						}
					}
				}

			}

			role.setAccesses(null);
			role.setAccesses(tmpAccess);

			for (Access access2 : tmpAccess) {
				System.out.println("TMP Access ID: " + access2.getId());
			}

			for (Access rAc : role.getAccesses()) {

				if (rAc != null) {

					if (rAc.getAccessType() != null) {
						System.out.println("Role Access After Set Temp: " + rAc.getId() + " Type:"
								+ rAc.getAccessType().getName());
					}
				}
			}

		}

		model.addAttribute("pRole", role);
		model.addAttribute("fRole", new Role());
		model.addAttribute("accesses", accesses);
		model.addAttribute("accessTypes", accessTypes);

		return "/pages/role/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String getRoleUpdateAction(Model model, HttpSession httpSession, @ModelAttribute("role") Role role) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "role", 4);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + " Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/";
				}

			}
		}
		// Access Start

		if (role != null) {

			for (Access accessL : role.getAccesses()) {
				System.out.println("Role & Access : " + accessL.getRole().getId() + " :: N Access: "
						+ accessL.getNoAccess() + " Access ID: " + accessL.getId());
			}

			if (role.getId() > 0) {

				if (!roleServices.update(role)) {
					return "redirect:/failure/ud?id=" + role.getId();
				} else {
					refreshRole();
				}
			}
		}

		return "redirect:/role/view?page=0";
	}

	private void setAccessType() {

		if (accessTypes == null) {

			accessTypes = accessTypeServices.getAllAccessType();
		} else {

			long size = accessTypes.size();
			long count = accessTypeServices.getCount();

			if (size != count) {

				accessTypes = accessTypeServices.getAllAccessType();
			}
		}

	}

	private void refreshRole() {

		roles = roleServices.getAllRoles();

	}

	private void setAccesses() {

		if (accesses == null) {

			accesses = accessServices.getAllAccess();
		} else {

			long size = accesses.size();
			long count = accessServices.getCount();

			if (size != count) {

				accesses = accessServices.getAllAccess();
			}
		}

	}

	private void setRoleList() {

		if (roles == null) {

			roles = roleServices.getAllRoles();
		} else {

			long size = roles.size();
			long count = roleServices.getCount();

			if (size != count) {

				roles = roleServices.getAllRoles();
			}

		}

	}

}
