package com.mikes.nolorry.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mikes.nolorry.common.BasicLog;
import com.mikes.nolorry.common.ExcludeAuth;
import com.mikes.nolorry.common.OptType;
import com.mikes.nolorry.dto.UserinfoDto;
import com.mikes.nolorry.service.basic.ISystemLogService;

@RestController
@RequestMapping
@Scope("prototype")
public class InOutController {

	private static final Logger logger = LoggerFactory.getLogger(InOutController.class);

	@Resource(name = "SystemLogService")
	private ISystemLogService systemLogService;

	@ExcludeAuth
	@BasicLog(optType = OptType.LOGIN, action = "登录", actionDesc = "用户登录")
	@PostMapping(value = "/login")
	public ResponseEntity<Map<String, Object>> login(UserinfoDto userinfo) throws Exception {
		logger.info("account={}, password={}", userinfo.getAccount(), userinfo.getPassword());

		Map<String, Object> result = new HashMap<String, Object>();

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();// 这个RequestContextHolder是Springmvc提供来获得请求的东西
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

		logger.info("session id {}", request.getSession().getId());

		if ("15121073881".equals(userinfo.getAccount()) && "1!2@3#4$".equals(userinfo.getPassword())) {
			String token = UUID.randomUUID().toString();

			request.getSession().setAttribute("account", userinfo.getAccount());
			request.getSession().setAttribute("accName", "Mike Su");
			request.getSession().setAttribute("token", token);

			result.put("account", userinfo.getAccount());
			result.put("accName", "Mike Su");
			result.put("token", token);

			return ResponseEntity.ok(result);
		} else {
			request.getSession().removeAttribute("account");
			request.getSession().removeAttribute("accName");
			request.getSession().removeAttribute("token");

			result.put("message", "account or password error.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}

	}

	@BasicLog(optType = OptType.LOGOUT, action = "注销", actionDesc = "用户注销")
	@GetMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout() {
		Map<String, Object> result = new HashMap<String, Object>();

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		request.getSession().removeAttribute("token");

		result.put("message", "logout");

		return ResponseEntity.ok(result);
	}

	@ExcludeAuth
	@GetMapping("/userinfo")
	public ResponseEntity<Map<String, Object>> userinfo() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("account", String.valueOf(request.getSession().getAttribute("account")));
		result.put("accName", String.valueOf(request.getSession().getAttribute("accName")));
		result.put("token", String.valueOf(request.getSession().getAttribute("token")));

		return ResponseEntity.ok(result);
	}

}
