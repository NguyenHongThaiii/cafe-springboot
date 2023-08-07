package com.cafe.website.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "kinds")
@Entity
public class Kind extends BaseCategory {
	public Kind() {
		// TODO Auto-generated constructor stub
	}

	public Kind(int id, int status, Long createdAt, Long updatedAt, String name, String slug, String image) {
		super(id, status, createdAt, updatedAt, name, slug, image);
		// TODO Auto-generated constructor stub
	}

	public Kind(int id, int status, Long createdAt, Long updatedAt) {
		super(id, status, createdAt, updatedAt);
		// TODO Auto-generated constructor stub
	}
}
