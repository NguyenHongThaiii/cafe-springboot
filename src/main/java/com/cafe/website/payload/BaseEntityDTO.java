package com.cafe.website.payload;

public class BaseEntityDTO {
	private Long id;

	private Integer status;

	private String createdAt;

	private String updatedAt;

	public BaseEntityDTO() {
		super();
		this.setStatus(1);
	}

	public BaseEntityDTO(Long id, Integer status, String createdAt, String updatedAt) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

}
