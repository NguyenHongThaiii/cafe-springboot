package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

public class NotificationDTO {
	private int id;
	private int status;
	private String createdAt;
	private String updatedAt;
	private List<Long> listUsersId = new ArrayList<>();
	private Long senderId;
	private String url;
	private String original;
	private Integer state;

	public NotificationDTO() {
		// TODO Auto-generated constructor stub
	}

	public NotificationDTO(int id, int status, String createdAt, String updatedAt, List<Long> listUsersId,
			Long senderId, String url, String original, Integer state) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.listUsersId = listUsersId;
		this.senderId = senderId;
		this.url = url;
		this.original = original;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public List<Long> getListUsersId() {
		return listUsersId;
	}

	public void setListUsersId(List<Long> listUsersId) {
		this.listUsersId = listUsersId;
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

	@Override
	public String toString() {
		return "NotificationDTO [id=" + id + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", listUsersId=" + listUsersId + ", senderId=" + senderId + ", url=" + url + ", original="
				+ original + ", state=" + state + "]";
	}

}
