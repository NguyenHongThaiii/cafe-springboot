package com.cafe.website.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "roles")
@Entity
public class Role extends BaseEntity {
	private String name;

	public Role(int id, int status, String createdAt, String updatedAt, String name) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
	}

	public Role() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
