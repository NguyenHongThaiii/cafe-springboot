package com.cafe.website.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseCategory extends BaseEntity {
	private String name;
	private String slug;
	private String image;
}