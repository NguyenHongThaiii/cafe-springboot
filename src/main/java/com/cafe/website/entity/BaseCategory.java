package com.cafe.website.entity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseCategory extends BaseEntity {
	private String name;
	private String slug;
	private String image;

	public BaseCategory(int id, int status, Long createdAt, Long updatedAt, String name, String slug, String image) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.slug = slug;
		this.image = image;
	}

	public BaseCategory(int id, int status, Long createdAt, Long updatedAt) {
		super(id, status, createdAt, updatedAt);
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}