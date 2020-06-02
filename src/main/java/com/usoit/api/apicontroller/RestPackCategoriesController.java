package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.PackageCat;
import com.usoit.api.data.vo.RestPackageCat;
import com.usoit.api.model.request.ReqPackageCat;
import com.usoit.api.services.PackageCatServices;


@RestController
@RequestMapping("/api/package-categories")
public class RestPackCategoriesController {

	
	@Autowired
	private PackageCatServices packageCatServices;

	private List<PackageCat> packageCats;
	
	private List<RestPackageCat> packCategories;
	
	@GetMapping("")
	public ResponseEntity<List<?>> getAllPackCateogries(HttpServletRequest httpServletRequest, Principal principal) {
		
		System.out.println("pack Categories Run !!");
		setPackageCategory();
		
		
		return ResponseEntity.ok(packCategories);
		
	}
	
	@RequestMapping(value = "/package-category", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPackCatAction(Principal principal, HttpServletRequest request, @RequestBody ReqPackageCat packageCat) {
		
		if (packageCat != null) {
			
			if (packageCat.getName() != null) {
				
				PackageCat cat = new PackageCat();
				cat.setName(packageCat.getName());
				cat.setDescription(packageCat.getDescription());
				
				if (packageCatServices.save(cat)) {
					
					return ResponseEntity.ok("package Category Save Success !!");
				}
			}
		}
		
		return ResponseEntity.ok(packageCat);
	}
	
	@RequestMapping(value = "/package-category", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPackCatUpdateAction(Principal principal, HttpServletRequest request, @RequestBody ReqPackageCat packageCat) {
		
		if (packageCat != null) {
			
			if (packageCat.getName() != null && packageCat.getId() > 0) {
				
				PackageCat cat = new PackageCat();
				cat.setName(packageCat.getName());
				cat.setDescription(packageCat.getDescription());
				cat.setId(packageCat.getId());
				
				if (packageCatServices.update(cat)) {
					
					return ResponseEntity.ok("package Category Update Success !!");
				}
			}
		}
		
		return ResponseEntity.ok(packageCat);
	}
	
	@RequestMapping(value = "/package-category/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPackageCategoryById(Principal principal, @PathVariable("id") int id, HttpServletRequest request) {
		
		if (id > 0) {
			
			PackageCat cat = packageCatServices.getPackCatById(id);
			
			if (cat != null) {
				
				ReqPackageCat reqPackageCat = new ReqPackageCat();
				reqPackageCat.setDescription(cat.getDescription());
				reqPackageCat.setId(cat.getId());
				reqPackageCat.setName(cat.getName());
				
				return ResponseEntity.ok(reqPackageCat);
			} 
		}
		
		return ResponseEntity.notFound().build();
	}

	private void setPackageCategory() {
		
		setPackageCats();
		
		packCategories = DozerMapper.parseObjectList(packageCats, RestPackageCat.class);
		
	}

	private void setPackageCats() {
		
		if (packageCats == null) {
			
			packageCats = packageCatServices.getAllPackCats();
		}else {
			
			long packSize = packageCats.size();
			long packCount = packageCatServices.getCount();
			
			if (packCount != packSize) {
				packageCats = packageCatServices.getAllPackCats();
			}
		}
		
		System.out.println("Pack Cat Size: " + packageCats.size());
		
	}
}
