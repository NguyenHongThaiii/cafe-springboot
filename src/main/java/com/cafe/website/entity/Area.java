package com.cafe.website.entity;

import com.cafe.website.payload.HasImage;
import com.cafe.website.payload.ImageDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "areas")
@Entity
public class Area extends BaseCategory implements HasImage {
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "area", fetch = FetchType.LAZY)
	private Image image;

	public Area() {
		// TODO Auto-generated constructor stub
	}

	public Area(int id, int status, Long createdAt, Long updatedAt, String slug, Image image) {
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
