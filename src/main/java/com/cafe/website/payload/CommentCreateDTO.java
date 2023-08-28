package com.cafe.website.payload;

public class CommentCreateDTO {
	private String name;
	private Integer reviewId;
	private Integer userId;
	private Integer status;

	public CommentCreateDTO(String name, Integer reviewId, Integer userId, Integer status) {
		super();
		this.name = name;
		this.reviewId = reviewId;
		this.userId = userId;
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public CommentCreateDTO() {
		// TODO Auto-generated constructor stub
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

}
