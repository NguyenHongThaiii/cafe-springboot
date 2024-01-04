package com.cafe.website.payload;

import com.cafe.website.annotation.CheckStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentCreateDTO {
	@NotNull
	@Size(max = 255)
	private String name;
	@NotNull
	private Long reviewId;
	@NotNull
	private Long userId;
	@NotNull
	@CheckStatus(allowedValues = { 0, 1 })
	private Integer status;

	public CommentCreateDTO(String name, Long reviewId, Long userId, Integer status) {
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
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CommentCreateDTO [name=" + name + ", reviewId=" + reviewId + ", userId=" + userId + ", status=" + status
				+ "]";
	}

}
