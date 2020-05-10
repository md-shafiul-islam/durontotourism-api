package com.usoit.api.apicontroller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.usoit.api.services.HelperServices;

@RestController
@RequestMapping("/api/uploadfile")
public class RestUploadFileController {

	@Autowired
	private HelperServices helperServices;
	
	@RequestMapping(value = "/source-one", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.PUT)
	public String uploadGivaenFileScOne(Principal principal, @RequestParam(name = "scFileOne") MultipartFile file) {
		System.out.println("File Upload Run");
		return helperServices.uploadImageAndGetUrl(file, "source-one");
		
	}
	
	@RequestMapping(value = "/source-two", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.PUT)
	public String uploadGivaenFileScTwo(Principal principal, @RequestParam(name = "scFileTwo") MultipartFile file) {
		System.out.println("File Upload Run");
		return helperServices.uploadImageAndGetUrl(file, "source-two");
		
	}
	
	@RequestMapping(value = "/image-gallery", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.PUT)
	public String uploadGivaenFileImageGallery(Principal principal, @RequestParam(name = "imageFile") MultipartFile file) {
		System.out.println("File Upload Run");
		return helperServices.uploadImageAndGetUrl(file, "image-gallery");
		
	}
	
	@RequestMapping(value = "/user-file/{dir}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.PUT)
	public String uploadUserAttachFileImage(Principal principal, @RequestParam(name = "attachFile") MultipartFile file, @PathVariable("dir") String dirPath) {
		System.out.println("File User Upload Run!! Dir Path: " + dirPath);
		return helperServices.uploadImageAndGetUrl(file, dirPath);
		
	}
	
	
	
}
