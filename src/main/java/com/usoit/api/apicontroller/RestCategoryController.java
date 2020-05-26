package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.Access;
import com.usoit.api.data.model.Category;
import com.usoit.api.data.model.User;
import com.usoit.api.data.vo.RestCategory;
import com.usoit.api.model.request.ReqCategory;
import com.usoit.api.services.CategoryServices;
import com.usoit.api.services.HelperServices;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins ="http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser"})
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
		
		System.out.println("Category Add Action Run!!" );
		
		

		if (reqCategory != null) {

			if (reqCategory.getName() != null) {
				
				System.out.println("Category Name Not Null" );
				
				Category category = DozerMapper.parseObject(reqCategory, Category.class);

				if (category != null) {
					
					System.out.println("Add Cat. Category Mapping Done!!");
					
					category.setId(0);
					
					if (!categoryServices.save(category)) {
						
						System.out.println("Category Added !!!!!!!!!");
						
						return ResponseEntity.ok(DozerMapper.parseObject(category, RestCategory.class));
					} else {
						System.out.println("Save Done!!");
					}
				}
			}
		}
		
		reqCategory.setDescription(reqCategory.getDescription()+" Request");
		
		return ResponseEntity.ok(reqCategory);
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
		
		List<Category> list = new ArrayList<>();
		
		list = categoryServices.getAllCats();
		
		categories = DozerMapper.parseObjectList(list, RestCategory.class);

	}

	private void setCategory() {
		
		List<Category> cateList = new ArrayList<>();
		
		if (categories == null) {

			cateList = categoryServices.getAllCats();
		} else {

			long size = categories.size();
			long count = categoryServices.getCount();

			if (size != count) {

				cateList = categoryServices.getAllCats();
			}

		}
		System.out.println("Category Size: " + cateList.size());
		categories = DozerMapper.parseObjectList(cateList, RestCategory.class);

	}
}
