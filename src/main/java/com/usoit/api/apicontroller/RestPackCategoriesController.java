package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
		
		return ResponseEntity.ok(packageCat);
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
