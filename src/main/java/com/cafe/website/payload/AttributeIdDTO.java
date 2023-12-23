package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class AttributeIdDTO {
	@NotNull
	private Integer id;

	public AttributeIdDTO() {
		// TODO Auto-generated constructor stub
	}

	public AttributeIdDTO(@NotNull Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
