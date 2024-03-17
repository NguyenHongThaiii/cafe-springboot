package com.cafe.website.serviceImp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Log;
import com.cafe.website.entity.User;
import com.cafe.website.payload.LogCreateDTO;
import com.cafe.website.payload.LogDTO;
import com.cafe.website.repository.LogRepository;
import com.cafe.website.service.LogService;
import com.cafe.website.util.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class LogServiceImp implements LogService {
	@PersistenceContext
	private EntityManager entityManager;
	private LogRepository logRepository;
	private ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(LogServiceImp.class);

	public LogServiceImp(EntityManager entityManager, LogRepository logRepository, ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.logRepository = logRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<LogDTO> getAllLogs(Integer limit, Integer page, Integer status, String method, Long userId,
			String message, String agent, String result, String params, String body, String endpoint, String action,
			String createdAt, String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<Log> loggerList = null;
		List<Sort.Order> sortOrders = new ArrayList<>();
		List<LogDTO> listLogDto;

		if (!StringUtils.isEmpty(sortBy))
			sortByList = Arrays.asList(sortBy.split(","));

		for (String sb : sortByList) {
			boolean isDescending = sb.endsWith("Desc");

			if (isDescending && !StringUtils.isEmpty(sortBy))
				sb = sb.substring(0, sb.length() - 4).trim();

			for (SortField sortField : validSortFields) {
				if (sortField.toString().equals(sb)) {
					sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
					break;
				}
			}
		}

		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));

		loggerList = logRepository.findWithFilters(status, method, userId, message, agent, result, params, body,
				endpoint, action, createdAt, updatedAt, pageable, entityManager);
		listLogDto = loggerList.stream().map(logger -> {
			LogDTO loggerDto = MapperUtils.mapToDTO(logger, LogDTO.class);

			return loggerDto;
		}).collect(Collectors.toList());

		return listLogDto;

	}

	@Override
	public void createLog(HttpServletRequest request, User user, String message, String result, String action) {
		Log log = new Log();
		log.setAgent(request.getHeader("User-Agent"));
//		log.setBody(this.getJsonBody(request));
		log.setEndpoint(request.getRequestURI());
		log.setMessage(message);
		log.setMethod(request.getMethod());
		log.setParams(this.getParamsAsJson(request));
		log.setResult(result);
		log.setUser(user);
		log.setAction(action);
		logRepository.save(log);
	}

	@Override
	public void createLog(HttpServletRequest request, User user, String message, String result, String bodyJson,
			String action) {
		Log log = new Log();
		log.setAgent(request.getHeader("User-Agent"));
		log.setBody(bodyJson);
		log.setEndpoint(request.getRequestURI());
		log.setMessage(message);
		log.setMethod(request.getMethod());
		log.setParams(this.getParamsAsJson(request));
		log.setResult(result);
		log.setUser(user);
		log.setAction(action);

		logRepository.save(log);
	}

	@Override
	public String getJsonBody(HttpServletRequest request) {
		return "";
	}

	@Override
	public <T> T getJsonObject(HttpServletRequest request, Class<T> clazz) throws IOException {
		return null;
	}

	@Override
	public String getParamsAsJson(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, Object> jsonMap = new HashMap<>();

		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			String paramName = entry.getKey();
			String[] paramValues = entry.getValue();

			if (paramValues.length == 1) {
				jsonMap.put(paramName, paramValues[0]);
			} else {
				jsonMap.put(paramName, paramValues);
			}
		}

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(jsonMap);

			return jsonString;

		} catch (Exception e) {
			e.printStackTrace();
			return "{}";
		}
	}

	@Override
	public void createLog(User user, String message, String result, String bodyJson, String action, String endpoint,
			String method, String agent) {
		Log log = new Log();
		log.setAgent(agent);
		log.setBody(bodyJson);
		log.setEndpoint(endpoint);
		log.setMessage(message);
		log.setMethod(method);
		log.setResult(result);
		log.setUser(user);
		log.setAction(action);

		logRepository.save(log);
	}

}
