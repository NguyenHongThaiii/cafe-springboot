package com.cafe.website.entity;

import com.cafe.website.payload.HasImage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "kinds")
@Entity
public class Kind extends BaseCategory implements HasImage {
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kind")
	private Image image;

	public Kind() {
		// TODO Auto-generated constructor stub
	}

	public Kind(int id, int status, Long createdAt, Long updatedAt, String slug, Image image) {
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
