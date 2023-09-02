package com.cafe.website.payload;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class PurposeUpdateDTO {
	private int status;

	private String name;
	private String slug;

	private MultipartFile imageFile;

	public PurposeUpdateDTO(int status, String name, String slug, MultipartFile imageFile) {
		super();
		this.status = status;
		this.name = name;
		this.slug = slug;
		this.imageFile = imageFile;
	}

	public PurposeUpdateDTO() {
		// TODO Auto-generated constructor stub
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

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

}
