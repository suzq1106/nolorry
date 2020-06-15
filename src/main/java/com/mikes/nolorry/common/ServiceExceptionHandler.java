package com.mikes.nolorry.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ServiceExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handlerServiceException(ServiceException ex) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", 500);
		map.put("message", ex.getMessage());

		return map;
	}

}