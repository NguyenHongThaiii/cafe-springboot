package com.cafe.website.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "purposes")
@Entity
public class Purpose extends BaseCategory {
	public Purpose() {
		// TODO Auto-generated constructor stub
	}

	public Purpose(int id, int status, Long createdAt, Long updatedAt, String name, String slug, String image) {
		super(id, status, createdAt, updatedAt, name, slug, image);
		// TODO Auto-generated constructor stub
	}

	public Purpose(int id, int status, Long createdAt, Long updatedAt) {
		super(id, status, createdAt, updatedAt);
		// TODO Auto-generated constructor stub
	}
}
