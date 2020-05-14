package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.TermsAndConditions;
import com.usoit.api.data.vo.RestTermsAndConds;
import com.usoit.api.data.vo.RestTransport;
import com.usoit.api.model.request.ReqTermsAndConds;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.TermsAndConditionsServices;

@RestController
@RequestMapping("/api/terms")
public class RestTermsAndCondController {

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private TermsAndConditionsServices conditionsServices;

	private List<TermsAndConditions> andConditions;

	private List<RestTermsAndConds> restTermsAndConds;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllTermsAndConds(Principal principal, HttpServletRequest request) {

		List<String> msg = new ArrayList<>();

		setRestTermsConds(msg);

		if (restTermsAndConds != null) {

			System.out.println("Rest Term Size: " + restTermsAndConds.size());

			return ResponseEntity.ok(restTermsAndConds);

		} else {

			System.out.println("Terms Mapping Failed !!");
		}

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/term", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTermAddAction(Principal principal, HttpServletRequest request,
			@RequestBody ReqTermsAndConds reqTermAndConditions) {

		String msg = "Terms Data not Set Or not match requerment ";

		if (reqTermAndConditions != null) {

			if (reqTermAndConditions.getName() != null && reqTermAndConditions.getDiscription() != null) {

				TermsAndConditions termAndConditions = new TermsAndConditions();

				termAndConditions.setName(reqTermAndConditions.getName());
				;
				termAndConditions.setDiscription(reqTermAndConditions.getDiscription());
				termAndConditions.setPublicId(helperServices.getRandomString(105));

				if (conditionsServices.save(termAndConditions)) {

					return ResponseEntity.ok("T&C Save ");
				}
			}
		}

		return ResponseEntity.accepted().body(msg);
	}

	@RequestMapping(value = "/term/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTermByPublicId(Principal principal, HttpServletRequest request,
			@PathVariable("id") String pubId) {

		String msg = "";

		if (helperServices.isValidAndLenghtCheck(pubId, 105)) {

			TermsAndConditions termsAndConditions = conditionsServices.getTermAndCondsByPubId(pubId);

			if (termsAndConditions != null) {

				ReqTermsAndConds reqTermsAndConds = DozerMapper.parseObject(termsAndConditions, ReqTermsAndConds.class);

				if (reqTermsAndConds != null) {

					return ResponseEntity.ok(reqTermsAndConds);
				}
			}
		}

		return ResponseEntity.accepted().body(msg);
	}

	@RequestMapping(value = "/term", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTermUpdateAction(Principal principal, HttpServletRequest request,
			@RequestBody ReqTermsAndConds reqTermAndConditions) {

		String msg = "Terms Data not Set Or not match requerment ";

		if (reqTermAndConditions != null) {

			if (reqTermAndConditions.getName() != null && reqTermAndConditions.getDiscription() != null
					&& reqTermAndConditions.getPublicId() != null) {

				TermsAndConditions termAndConditions = new TermsAndConditions();

				termAndConditions.setName(reqTermAndConditions.getName());
				;
				termAndConditions.setDiscription(reqTermAndConditions.getDiscription());
				termAndConditions.setPublicId(reqTermAndConditions.getPublicId());

				if (conditionsServices.update(termAndConditions)) {

					System.out.println("Terms Updated !!");

					return ResponseEntity.ok("T&C Update ");
				}
			}
		}

		return ResponseEntity.accepted().body(msg);
	}

	private void setRestTermsConds(List<String> msg) {

		setTermsConds();

		if (andConditions != null) {

			System.out.println("terms Size: " + andConditions.size());

			restTermsAndConds = DozerMapper.parseObjectList(andConditions, RestTermsAndConds.class);

		} else {
			msg.add("Terms Mapping Failed !!");
			System.out.println("Terms Null !!");
		}

	}

	private void setTermsConds() {

		andConditions = conditionsServices.getAllTermAndConds();

	}
}
