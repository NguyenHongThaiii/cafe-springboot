package com.cafe.website.entity;

import com.cafe.website.payload.HasImage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "conveniences")
@Entity
public class Convenience extends BaseCategory implements HasImage {
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "convenience")
	private Image image;

	public Convenience() {
		// TODO Auto-generated constructor stub
	}

	public Convenience(int id, int status, Long createdAt, Long updatedAt, String slug, Image image) {
		super(id, status, createdAt, updatedAt, slug);
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
