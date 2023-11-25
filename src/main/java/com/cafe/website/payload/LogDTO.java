package com.cafe.website.payload;

import io.swagger.v3.oas.annotations.media.Schema;

public class LogDTO {
	@Schema(description = "Id Logger")
	private Integer id;
	@Schema(description = "Status Logger")
	private Integer status;
	@Schema(description = "CreatedAt Logger")
	private String createdAt;
	@Schema(description = "UpdatedAt Logger")
	private String updatedAt;
	@Schema(description = "Method Logger")
	private String method;
	@Schema(description = "User Id ")
	private String userId;
	@Schema(description = "Message Logger")
	private String message;
	@Schema(description = "Agent Logger")
	private String agent;
	@Schema(description = "Result Logger")
	private String result;
	@Schema(description = "Params Logger")
	private String params;
	@Schema(description = "body Logger")
	private String body;
	@Schema(description = "Endpoint Logger")
	private String endpoint;
	private String action;

	public LogDTO() {
		// TODO Auto-generated constructor stub
	}

	public LogDTO(Integer id, Integer status, String createdAt, String updatedAt, String method, String userId,
			String message, String agent, String result, String params, String body, String endpoint, String action) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.method = method;
		this.userId = userId;
		this.message = message;
		this.agent = agent;
		this.result = result;
		this.params = params;
		this.body = body;
		this.endpoint = endpoint;
		this.action = action;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
