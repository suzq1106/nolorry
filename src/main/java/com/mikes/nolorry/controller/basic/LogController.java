package com.mikes.nolorry.controller.basic;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mikes.nolorry.dto.SystemLogDto;
import com.mikes.nolorry.model.basic.SystemLog;
import com.mikes.nolorry.service.basic.ISystemLogService;

@RestController
@RequestMapping("/basic/log")
public class LogController {

	private static final Logger logger = LoggerFactory.getLogger(LogController.class);

	@Resource(name = "SystemLogService")
	private ISystemLogService systemLogService;

	@GetMapping
	public ResponseEntity<List<SystemLog>> find(SystemLogDto condition) {
		List<SystemLog> list = systemLogService.findByCondition(condition);
		logger.info("list={}", list);

		return ResponseEntity.ok(list);
	}

}
