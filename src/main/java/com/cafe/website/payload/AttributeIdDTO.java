package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class AttributeIdDTO {
	@NotNull
	private Long id;

	public AttributeIdDTO() {
		// TODO Auto-generated constructor stub
	}

	public AttributeIdDTO(@NotNull Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
