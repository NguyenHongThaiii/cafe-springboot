package com.cafe.website.entity;

import com.cafe.website.payload.HasImage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "purposes")
@Entity
public class Purpose extends BaseCategory implements HasImage {
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "purpose")
	private Image image;

	public Purpose() {
		// TODO Auto-generated constructor stub
	}

	public Purpose(Long id, int status, String createdAt, String updatedAt, String name, String slug, Image image) {
		super(id, status, createdAt, updatedAt, name, slug);
		this.image = image;
	}

	@Override
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
