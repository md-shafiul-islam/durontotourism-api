package com.usoit.api.enduser.controller;

import java.io.File;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.usoit.api.services.HelperServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/enu/v1/uploadfile")
public class RestFileController {

	@Autowired
	private HelperServices helperServices;

	@RequestMapping(value = "/user/traveler/{dirName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.PUT)
	public String uploadCustomerFile(Principal principal, @RequestParam(name = "attachFile") MultipartFile file,
			@PathVariable("dirName") String dirPath) {
		log.info("File User Upload Run!! Dir Path: " + dirPath);
		return helperServices.uploadImageAndGetUrl(file, dirPath);

	}

	@RequestMapping(value = "/user/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.PUT)
	public ResponseEntity<?> uploadCustomerProfileImage(Principal principal,
			@RequestParam(name = "attachFile") MultipartFile mFile) {
		log.info("File User Profile Upload Run!! Dir Path: " + mFile);

		String pathSeparetor = File.separator;
		ClassLoader classLoder = getClass().getClassLoader();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", false);
		map.put("message", "File upload failed");
		map.put("path", null);
		if (mFile == null) {
			map.put("message", "File is null Please Add file");
		}
		String localPath = pathSeparetor + "uimages" + pathSeparetor + "profile";

		File file = new File(classLoder.getResource("./static").getFile());

		String path = file.getPath();

		String filePath = helperServices.makeDirectory(path + localPath); // uploadImageAndGetUrl(file, "profile");

		if (filePath != null) {
			String fileName = helperServices.storeFile(filePath, mFile);
			if (filePath != null) {
				map.put("message", "File is Added success file");
				map.put("path", "/uimages/profile/"+fileName);
				map.put("status", true);
			}
		}

		return ResponseEntity.ok(map);

	}
	
	@RequestMapping(value = "/agents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.PUT)
	public ResponseEntity<?> uploadAgentFiles(@RequestParam(name = "attachFile") MultipartFile mFile, @RequestParam(name="dirName", defaultValue = "")String dirName) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("message", "File is Upload ... ");
		map.put("path", null);
		map.put("status", false);
		String pathSeparetor = File.separator;
		ClassLoader classLoder = getClass().getClassLoader();
				
		if (mFile == null) {
			map.put("message", "File is null Please Add file");
		}
		String localPath = pathSeparetor + "uimages" + pathSeparetor + "agents"+dirName;
		
		log.info("Directory Location "+ localPath);

		File file = new File(classLoder.getResource("./static").getFile());

		String path = file.getPath();

		String filePath = helperServices.makeDirectory(path + localPath); // uploadImageAndGetUrl(file, "profile");

		if (filePath != null) {
			String fileName = helperServices.storeFile(filePath, mFile);
			if (filePath != null) {
				map.put("message", "File is Added success file");
				map.put("path", "/uimages/agents"+dirName+"/"+fileName);
				map.put("status", true);
			}
		}
		
		return ResponseEntity.ok(map);

	}
	
	@RequestMapping(value = "/recharges", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.PUT)
	public ResponseEntity<?> uploadRechargeFiles(@RequestParam(name = "attachFile") MultipartFile mFile, @RequestParam(name="dirName", defaultValue = "")String dirName) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Recharge File is Uploading ... ");
		map.put("path", null);
		map.put("status", false);
		String pathSeparetor = File.separator;
		ClassLoader classLoder = getClass().getClassLoader();
				
		if (mFile == null) {
			map.put("message", "File is null Please Add file");
		}
		String localPath = pathSeparetor + "uimages" + pathSeparetor + "recharges"+pathSeparetor+dirName;
		
		log.info("Directory Location "+ localPath);

		File file = new File(classLoder.getResource("./static").getFile());

		String path = file.getPath();

		String filePath = helperServices.makeDirectory(path + localPath); // uploadImageAndGetUrl(file, "profile");

		if (filePath != null) {
			String fileName = helperServices.storeFile(filePath, mFile);
			if (filePath != null) {
				map.put("message", "File is Added success file");
				map.put("path", "/uimages/recharges/"+dirName+"/"+fileName);
				map.put("status", true);
			}
		}
		
		return ResponseEntity.ok(map);

	}
}
