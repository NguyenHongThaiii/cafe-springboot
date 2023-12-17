package com.cafe.website.serviceImp;

import jakarta.validation.constraints.NotNull;

public class DeleteDTO {
	@NotNull
	private Integer id;
	@NotNull
	private Integer reviewId;

	public DeleteDTO() {
		// TODO Auto-generated constructor stub
	}

	public DeleteDTO(@NotNull Integer id, @NotNull Integer reviewId) {
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
