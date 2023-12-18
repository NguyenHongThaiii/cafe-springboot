package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class CommentDeleteDTO {
	@NotNull
	private Integer id;
	@NotNull
	private Integer reviewId;

	public CommentDeleteDTO() {
		// TODO Auto-generated constructor stub
	}

	public CommentDeleteDTO(@NotNull Integer id, @NotNull Integer reviewId) {
		super();
		this.id = id;
		this.reviewId = reviewId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

}
