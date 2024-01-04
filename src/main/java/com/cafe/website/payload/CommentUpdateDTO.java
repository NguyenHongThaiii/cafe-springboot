package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class CommentUpdateDTO extends CommentCreateDTO {
	@NotNull
	private Long id;

	public CommentUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public CommentUpdateDTO(String name, Long reviewId, Long userId, Integer status, Long id) {
		super(name, reviewId, userId, status);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
