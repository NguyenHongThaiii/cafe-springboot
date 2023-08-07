package com.cafe.website.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "conveniences")
@Entity
public class Convenience extends BaseCategory {

	public Convenience(int id, int status, Long createdAt, Long updatedAt, String name, String slug, String image) {
		super(id, status, createdAt, updatedAt, name, slug, image);
		// TODO Auto-generated constructor stub
	}

	public Convenience(int id, int status, Long createdAt, Long updatedAt) {
		super(id, status, createdAt, updatedAt);
		// TODO Auto-generated constructor stub
	}

	public Convenience() {
		// TODO Auto-generated constructor stub
	}
	
}
