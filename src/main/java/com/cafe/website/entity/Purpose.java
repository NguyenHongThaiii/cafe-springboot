package com.cafe.website.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "purposes")
@Entity
public class Purpose extends BaseCategory {
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "purpose")
	private Image image;

	public Purpose() {
		// TODO Auto-generated constructor stub
	}

	public Purpose(int id, int status, Long createdAt, Long updatedAt, String slug, Image image) {
		super(id, status, createdAt, updatedAt, slug);
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
