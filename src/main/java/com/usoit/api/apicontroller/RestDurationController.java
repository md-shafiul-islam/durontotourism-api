package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.usoit.api.data.model.Category;
import com.usoit.api.data.model.Duration;
import com.usoit.api.data.vo.RestAccessUser;
import com.usoit.api.data.vo.RestDuration;
import com.usoit.api.model.request.ReqDuration;
import com.usoit.api.services.DurationServices;
import com.usoit.api.services.HelperServices;

@RestController
@RequestMapping("/api/durations")
@CrossOrigin(origins = "*", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser" })
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
	
	@RequestMapping(value = "/duration/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getDurationById(Principal principal, HttpServletRequest request, @PathVariable("id") int id){
		
		Map<String, Object> returnData = new HashMap<>();
		RestDuration restDuration = null;
		// Access Start
		Map<String, RestAccessUser> accessAll = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser access = accessAll.get("duration");

		if (access == null) {

			returnData.put("msg", "you cann't access. Please login and try again");
			returnData.put("status", false);
			returnData.put("duration", restDuration);
			return ResponseEntity.accepted().body(returnData);

		} else {
			if (access.getNoAccess() == 1) {

				returnData.put("msg", "you cann't access. This option Please contact Administrator");
				returnData.put("status", false);
				returnData.put("duration", restDuration);
				return ResponseEntity.accepted().body(returnData);

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");
					
				} else {
					returnData.put("msg", "you cann't access. This option Please contact Administrator");
					returnData.put("status", false);
					returnData.put("duration", restDuration);
					return ResponseEntity.accepted().body(returnData);
				}

			}
		}
		
		if (id > 0) {
			
			Duration duration = durationServices.getDurationById(id);
			
			if (duration != null) {
				
				restDuration = DozerMapper.parseObject(duration, RestDuration.class);
				
				if (restDuration != null) {
					returnData.put("msg", "Get Duration By given ID ");
					returnData.put("status", false);
					returnData.put("duration", restDuration);
					
					return ResponseEntity.ok(returnData);
				}
						
				returnData.put("msg", "Mapping Error Conatct System Admnistartor !! ");
				returnData.put("status", false);

				return ResponseEntity.accepted().body(returnData);
			}
		}
		
		returnData.put("msg", "Given Id Mismatch ");
		returnData.put("status", false);

		return ResponseEntity.accepted().body(returnData);
		
		
	}

	@RequestMapping(value = "/duration", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDurationUpdateAction(Principal principal, HttpSession httpSession,
			@RequestBody ReqDuration duration) {

		Map<String, Object> returnData = new HashMap<>();
		// Access Start
		Map<String, RestAccessUser> accessAll = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser access = accessAll.get("duration");

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

		if (duration != null) {

			if (duration.getId() > 0) {
				
				Duration prepDuration = new Duration();
				
				prepDuration.setDay(duration.getDay());
				prepDuration.setName(duration.getName());
				prepDuration.setNight(duration.getNight());
				prepDuration.setId(duration.getId());
				

				if (!durationServices.update(prepDuration)) {

					returnData.put("msg", "Duration update faild, Contact System Administrator ");
					returnData.put("status", false);

					return ResponseEntity.accepted().body(returnData);
				} else {
					returnData.put("msg", "Duretion update Successful");
					returnData.put("status", true);

					return ResponseEntity.ok(returnData);
				}
			}
		}

		returnData.put("msg", "Data format mismatch");
		returnData.put("status", true);

		return ResponseEntity.ok(returnData);
	}

	@RequestMapping(value = "/duration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDurationAddAction(Principal principal, HttpSession httpSession,
			@RequestBody ReqDuration duration) {

		Map<String, Object> returnData = new HashMap<>();
		// Access Start
		Map<String, RestAccessUser> accessAll = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser access = accessAll.get("duration");

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

		if (duration != null) {
			
			Duration prepDuration = new Duration();
			
			prepDuration.setDay(duration.getDay());
			prepDuration.setName(duration.getName());
			prepDuration.setNight(duration.getNight());
			
			if (duration.getName() == null) {
				returnData.put("msg", "Duration Name cann't is Empty, Contact System Administrator ");
				returnData.put("status", false);

				return ResponseEntity.accepted().body(returnData);
			}

			if (!durationServices.save(prepDuration)) {

				returnData.put("msg", "Duration update faild, Contact System Administrator ");
				returnData.put("status", false);

				return ResponseEntity.accepted().body(returnData);
			} else {
				returnData.put("msg", "Duration Save Successful");
				returnData.put("status", true);

				return ResponseEntity.ok(returnData);
			}
		}

		returnData.put("msg", "Data format mismatch");
		returnData.put("status", true);

		return ResponseEntity.ok(returnData);
	}

	private void setRestDuration() {

		setDuration();

		restDurations = DozerMapper.parseObjectList(durations, RestDuration.class);
		System.out.println("Duration Size: " + durations.size());

	}

	private void setDuration() {

		if (durations == null) {

			durations = durationServices.getAllDurations();
		} else {

			long size = durations.size();
			long count = durationServices.getCount();

			if (size != count) {
				durations = durationServices.getAllDurations();
			}
		}

	}

}