package com.cafe.website.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseCategory extends BaseEntity {
	private String name;
	private String slug;

	public BaseCategory(Long id, int status, String createdAt, String updatedAt, String name, String slug) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.slug = slug;
	}

	public BaseCategory() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	@Override
	public String toString() {
		return "BaseCategory [name=" + name + ", slug=" + slug + "]";
	}

}