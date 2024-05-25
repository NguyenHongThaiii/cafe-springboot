package com.cafe.website.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.LogDTO;
import com.cafe.website.service.LogService;

@RestController
@RequestMapping("api/v1/loggers")
public class LoggerController {
	private LogService logService;

	public LoggerController(LogService logService) {
		super();
		this.logService = logService;

	}

	@GetMapping("")
	public ResponseEntity<List<LogDTO>> getListLogger(
			@RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false) String result, @RequestParam(required = false) String message,
			@RequestParam(required = false) String body, @RequestParam(required = false) String endpoint,
			@RequestParam(required = false) String action, @RequestParam(required = false) String agent,
			@RequestParam(required = false) String params, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) Long userId, @RequestParam(required = false) String method,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) String sortBy) {
		List<LogDTO> listLogs = logService.getAllLogs(limit, page, status, method, userId, message, agent, result,
				params, body, endpoint, action, createdAt, updatedAt, sortBy);
		return ResponseEntity.ok(listLogs);

	}
	@GetMapping("/count")
	public ResponseEntity<Integer> getCount(
			@RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false) String result, @RequestParam(required = false) String message,
			@RequestParam(required = false) String body, @RequestParam(required = false) String endpoint,
			@RequestParam(required = false) String action, @RequestParam(required = false) String agent,
			@RequestParam(required = false) String params, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) Long userId, @RequestParam(required = false) String method,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) String sortBy) {
		Integer listLogs = logService.getCountLogs(limit, page, status, method, userId, message, agent, result,
				params, body, endpoint, action, createdAt, updatedAt, sortBy);
		return ResponseEntity.ok(listLogs);

	}
}
