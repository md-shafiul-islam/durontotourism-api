package com.usoit.api.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.usoit.api.exception.ExceptionResponse;

//@ControllerAdvice
//@RestController
public class CustomizedResponseEntityExcetionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> hadelAllExceptions(Exception exception, WebRequest request){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
