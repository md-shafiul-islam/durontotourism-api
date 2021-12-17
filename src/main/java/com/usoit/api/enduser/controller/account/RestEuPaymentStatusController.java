package com.usoit.api.enduser.controller.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.mapper.PaymentStatusMapper;
import com.usoit.api.model.response.RestPaymentStatus;
import com.usoit.api.model.response.SelectOption;
import com.usoit.api.services.PaymentStatusServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/enu/v1/paymet-status")
public class RestEuPaymentStatusController {

	@Autowired
	private PaymentStatusServices paymentStatusServices;

	@Autowired
	private PaymentStatusMapper paymentStatusMapper;

	@GetMapping
	public ResponseEntity<?> getAllPaymentStatus() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);

		List<RestPaymentStatus> paymentStatus = paymentStatusMapper
				.mapAllRestPaymentStatusOnly(paymentStatusServices.getAllPaymentStatus());

		if (paymentStatus != null) {
			map.put("message", Integer.toString(paymentStatus.size()) + " Payment Status(s) Found");
			map.put("status", true);
			map.put("data", paymentStatus);
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/options/{type}" )
	public ResponseEntity<?> getAllPaymentStatus(@PathVariable("type") boolean option) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);

		if (option) {
			List<SelectOption> paymentStatus = paymentStatusMapper
					.mapAllRestPaymentStatusOnlyAsOption(paymentStatusServices.getAllPaymentStatus());

			if (paymentStatus != null) {
				map.put("message", Integer.toString(paymentStatus.size()) + " Payment Status(s) Found");
				map.put("status", true);
				map.put("data", paymentStatus);
			}
		}

		return ResponseEntity.ok(map);
	}

}
