package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class CommentUpdateDTO extends CommentCreateDTO {
	@NotNull
	private Integer id;

	public CommentUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public CommentUpdateDTO(String name, Integer reviewId, Integer userId, Integer status, Integer id) {
		super(name, reviewId, userId, status);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
