package com.cafe.website.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseCatDTO extends BaseEntityDTO {
	private String name;
	private String slug;
	private String image;
}
