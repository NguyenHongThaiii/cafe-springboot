package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

public class NotificationUpdateDTO {
	private Long id;
	private int status;
	private String createdAt;
	private String updatedAt;
	private String url;
	private String original;
	private Integer state;

	public NotificationUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public NotificationUpdateDTO(Long id, int status, String createdAt, String updatedAt, String url, String original,
			Integer state) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.url = url;
		this.original = original;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

}
