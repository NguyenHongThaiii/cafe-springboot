package com.cafe.website.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity()
@Table(name = "logs")
public class Log extends BaseEntity {
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	private String message;
	private String agent;
	private String result;
	@Column(length = 2000)
	private String params;
	@Column(length = 2000)
	private String body;
	private String endpoint;
	private String method;
	private String action;

	public Log() {
		// TODO Auto-generated constructor stub
	}

	public Log(User user, String message, String agent, String result, String params, String body, String endpoint,
			String method, String action) {
		super();
		this.user = user;
		this.message = message;
		this.agent = agent;
		this.result = result;
		this.params = params;
		this.body = body;
		this.endpoint = endpoint;
		this.method = method;
		this.action = action;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
