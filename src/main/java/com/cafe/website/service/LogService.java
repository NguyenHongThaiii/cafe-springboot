package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.entity.User;
import com.cafe.website.payload.LogCreateDTO;
import com.cafe.website.payload.LogDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface LogService {

	List<LogDTO> getAllLogs(Integer limit, Integer page, Integer status, String method, Integer userId, String message,
			String agent, String result, String params, String body, String endpoint, String action, String createdAt,
			String updatedAt, String sortBy);

	void createLog(HttpServletRequest request, User user, String message, String result, String action);

	void createLog(HttpServletRequest request, User user, String message, String result, String bodyJson,
			String action);

	String getJsonBody(HttpServletRequest request) throws IOException;

	<T> T getJsonObject(HttpServletRequest request, Class<T> clazz) throws IOException;

	String getParamsAsJson(HttpServletRequest request);
}
