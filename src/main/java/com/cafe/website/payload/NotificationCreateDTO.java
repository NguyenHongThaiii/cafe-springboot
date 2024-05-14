package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public class NotificationCreateDTO {
	@NotNull
	private int status;
	@NotNull
	private List<Long> listUsers = new ArrayList<>();
	@NotNull
	private Long senderId;
	@NotNull
	private String url;
	@NotNull
	private String original;
	@NotNull
	private Integer state;
	@NotNull
	private String message;

	public NotificationCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public NotificationCreateDTO(@NotNull int status, @NotNull List<Long> listUsers, @NotNull Long senderId,
			@NotNull String url, @NotNull String original, @NotNull Integer state, @NotNull String message) {
		super();
		this.status = status;
		this.listUsers = listUsers;
		this.senderId = senderId;
		this.url = url;
		this.original = original;
		this.state = state;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Long> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<Long> listUsers) {
		this.listUsers = listUsers;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
