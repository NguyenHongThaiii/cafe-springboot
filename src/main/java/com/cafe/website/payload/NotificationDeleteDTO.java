package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class NotificationDeleteDTO {
	@NotNull
	private Long id;

	public NotificationDeleteDTO() {
		// TODO Auto-generated constructor stub
	}

	public NotificationDeleteDTO(@NotNull Long id) {
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
