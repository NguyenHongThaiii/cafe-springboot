package com.cafe.website.payload;

public class CommentListDTO {
	private Integer limit;
	private Integer page;
	private String name;
	private Integer reviewId;
	private Integer userId;
	private String createdAt;
	private String updatedAt;
	private String sortBy;

	public CommentListDTO() {
		this.limit = 10;
		this.page = 1;
	}

	public CommentListDTO(Integer limit, Integer page, String name, Integer reviewId, Integer userId, String createdAt,
			String updatedAt, String sortBy) {
		super();
		this.limit = limit;
		this.page = page;
		this.name = name;
		this.reviewId = reviewId;
		this.userId = userId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.sortBy = sortBy;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}
