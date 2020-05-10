package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.Duration;
import com.usoit.api.data.vo.RestDuration;
import com.usoit.api.services.DurationServices;
import com.usoit.api.services.HelperServices;

@RestController
@RequestMapping("/api/durations")
@CrossOrigin(origins ="*", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser"})
public class RestDurationController {

	@Autowired
	private DurationServices durationServices;
	
	@Autowired
	private HelperServices helperServices;
	
	private List<Duration> durations;
	
	private List<RestDuration> restDurations;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllDurations(Principal principal, HttpServletRequest httpServletRequest) {
		
		setRestDuration();
		
		return ResponseEntity.ok(restDurations);
	}

	
	private void setRestDuration() {
		
		setDuration();
		
		restDurations = DozerMapper.parseObjectList(durations, RestDuration.class);
		System.out.println("Duration Size: " + durations.size());
		
	}

	private void setDuration() {
		
		if (durations == null) {
			
			durations = durationServices.getAllDurations();
		}else {
			
			long size = durations.size();
			long count = durationServices.getCount();
			
			
			if (size != count) {
				durations = durationServices.getAllDurations();
			}
		}
		
	}
	
}
