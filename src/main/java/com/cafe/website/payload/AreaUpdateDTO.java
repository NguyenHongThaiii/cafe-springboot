package com.cafe.website.payload;

import org.springframework.web.multipart.MultipartFile;

public class AreaUpdateDTO  {

	private int status;

	private String name;

	private String slug;

	private MultipartFile image;

	public AreaUpdateDTO(int status, String name, String slug, MultipartFile image) {
		super();
		this.status = status;
		this.name = name;
		this.slug = slug;
		this.image = image;
	}

	public AreaUpdateDTO() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
