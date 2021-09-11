package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.vo.RestAccessUser;
import com.usoit.api.data.vo.RestCategory;
import com.usoit.api.model.Access;
import com.usoit.api.model.Category;
import com.usoit.api.model.User;
import com.usoit.api.model.request.ReqCategory;
import com.usoit.api.services.CategoryServices;
import com.usoit.api.services.HelperServices;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser" })
public class RestCategoryController {

	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private HelperServices helperServices;

	private List<RestCategory> categories;

	private User cUser;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<RestCategory>> getAllCategories(HttpSession httpSession, Principal principal) {

		if (principal != null) {

			System.out.println("Current User Name: " + principal.getName());
		}

		setCategory();

		return ResponseEntity.ok(categories);

	}

	@RequestMapping("/add")
	public ResponseEntity<?> getCategoryAddPage(HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return null;

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return null;

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return null;
				}

			}
		}
		// Access End

		return ResponseEntity.ok(new Category());
	}

	@RequestMapping(value = "/category", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCategoryAddAction(@RequestBody ReqCategory reqCategory, HttpSession httpSession) {

		Map<String, Object> returnData = new HashMap<>();

		if (reqCategory != null) {

			if (reqCategory.getName() != null) {

				Category category = DozerMapper.parseObject(reqCategory, Category.class);

				if (category != null) {

					System.out.println("Add Cat. Category Mapping Done!!");

					category.setId(0);

					if (!categoryServices.save(category)) {

						returnData.put("msg", "Category Save Failed, Try Again Or Contact System Administrator ");
						returnData.put("status", true);
						return ResponseEntity.accepted().body(returnData);

					} else {
						returnData.put("msg", "Category Save !!");
						returnData.put("status", true);
						return ResponseEntity.accepted().body(returnData);
					}
				}

				returnData.put("msg", "Category Mapping Error, Please, Contact System Adminstrator !!");
				returnData.put("status", false);
				return ResponseEntity.accepted().body(returnData);
			}

			returnData.put("msg", "Category Name Cann't Empty Failed");
			returnData.put("status", false);
			return ResponseEntity.accepted().body(returnData);
		}

		returnData.put("msg", "Please Send proper Category info");
		returnData.put("status", false);
		return ResponseEntity.accepted().body(returnData);
	}

	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCategoryEditOrUpdatePage(Principal principal, HttpSession httpSession,
			@PathVariable("id") int id) {

		Map<String, Object> returnData = new HashMap<>();
		Category category = null;
		returnData.put("category", category);
		// Access Start
		Map<String, RestAccessUser> accessAll = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser access = accessAll != null ? accessAll.get("category") : null;

		if (access == null) {
			returnData.put("msg", "Please Login and try again !!");
			returnData.put("status", false);
			return ResponseEntity.accepted().body(returnData);

		} else {
			if (access.getNoAccess() == 1) {

				returnData.put("msg", "You can't Access this option!!");
				returnData.put("status", false);
				return ResponseEntity.accepted().body(returnData);

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {

					returnData.put("msg", "You can't Access this option!!");
					returnData.put("status", false);
					return ResponseEntity.accepted().body(returnData);
				}

			}
		}
		// Access End

		System.out.println("Category Update fnc Run!! & ID: " + id);

		if (id > 0) {

			category = categoryServices.getCategoryById(id);

			if (category != null) {

				RestCategory restCategory = new RestCategory();

				restCategory.setDescription(category.getDescription());
				restCategory.setName(category.getName());
				restCategory.setId(category.getId());

				returnData.put("msg", "Get Category Success");
				returnData.put("status", false);
				returnData.put("category", restCategory);
				return ResponseEntity.ok(returnData);
			}
		}

		returnData.put("msg", "Category Not found by given Id");
		returnData.put("status", false);
		returnData.put("category", category);
		return ResponseEntity.ok(returnData);
	}

	@RequestMapping(value = "/category", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCategoryUpdateAction(Principal principal, HttpSession httpSession,
			@RequestBody ReqCategory category) {

		Map<String, Object> returnData = new HashMap<>();
		// Access Start
		Map<String, RestAccessUser> accessAll = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser access = accessAll != null ? accessAll.get("user") : null;

		if (access == null) {

			returnData.put("msg", "you cann't access. Please login and try again");
			returnData.put("status", false);

			return ResponseEntity.accepted().body(returnData);

		} else {
			if (access.getNoAccess() == 1) {

				returnData.put("msg", "you cann't access. This option Please contact Administrator");
				returnData.put("status", false);

				return ResponseEntity.accepted().body(returnData);

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					returnData.put("msg", "you cann't access. This option Please contact Administrator");
					returnData.put("status", false);

					return ResponseEntity.accepted().body(returnData);
				}

			}
		}
		// Access End

		if (category != null) {

			if (category.getId() > 0) {

				Category category2 = new Category();

				category2.setDescription(category.getDescription());
				category2.setName(category.getName());
				category2.setId(category.getId());

				if (!categoryServices.update(category2)) {

					returnData.put("msg", "Category update faild, Contact System Administrator ");
					returnData.put("status", false);

					return ResponseEntity.accepted().body(returnData);
				} else {
					returnData.put("msg", "Category update Successful");
					returnData.put("status", true);
					refreshCategory();
					return ResponseEntity.ok(returnData);
				}
			}
		}

		returnData.put("msg", "Data format mismatch");
		returnData.put("status", true);

		return ResponseEntity.ok(returnData);
	}

	private void refreshCategory() {

		List<Category> list = new ArrayList<>();

		list = categoryServices.getAllCats();

		categories = DozerMapper.parseObjectList(list, RestCategory.class);

	}

	private void setCategory() {

		List<Category> cateList = new ArrayList<>();

		cateList = categoryServices.getAllCats();

		categories = DozerMapper.parseObjectList(cateList, RestCategory.class);

	}
}