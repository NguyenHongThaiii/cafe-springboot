package com.cafe.website.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BaseEntityDTO {
	private int id;

	private int status;

	private Long createdAt;

	private Long updatedAt;
}
