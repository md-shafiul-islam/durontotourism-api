package com.usoit.api.test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.usoit.api.services.HelperServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/tests")
public class TestActionController {

//	@Autowired
//	private Resource resource;
	
	@Autowired
	private HelperServices helperServices;
	

	@GetMapping(value = "/path")
	public ResponseEntity<?> getFilePathTestAction(Principal principal, @RequestParam(name = "attachFile") MultipartFile mFile) {

		String pathSeparetor = File.separator;
		ClassLoader classLoder = getClass().getClassLoader();
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			File file = new File(classLoder.getResource("./static").getFile());
			
			String filePath = file.getCanonicalPath();
			String parent = file.getParent();
			String path = file.getPath();
			
			String dirPath = helperServices.makeDirectory(path+pathSeparetor+"uimages"+pathSeparetor+"test2");
			log.info("New Directory path "+dirPath);

			if(dirPath != null) {
				String nFilePath = helperServices.storeFile(dirPath, mFile);
				if(nFilePath != null) {
					map.put("created file Path ", nFilePath);
					map.put("status", true);
				}else {
					map.put("status", false);
				}
				
			}
			
			map.put("filePath", filePath);
			map.put("parent", parent);
			map.put("path", path);
			
			map.put("fileAbsPath", file.getAbsolutePath());
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(map);
	}

}
