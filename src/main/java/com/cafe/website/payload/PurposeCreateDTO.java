package com.cafe.website.payload;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PurposeCreateDTO {
	@Min(value = 0, message = "Status should not be less than 0")
	@Max(value = 1, message = "status should not be greater than 1")
	private int status;

	@NotEmpty(message = "Name should not be null or empty")
	private String name;

	@NotNull(message = "Slug should not be null or empty")
	private String slug;

	@NotNull(message = "Image should not be null or empty")
	private MultipartFile imageFile;

	public PurposeCreateDTO(
			@Min(value = 0, message = "Status should not be less than 0") @Max(value = 1, message = "status should not be greater than 1") int status,
			@NotEmpty(message = "Name should not be null or empty") String name,
			@NotNull(message = "Slug should not be null or empty") String slug,
			@NotNull(message = "Image should not be null or empty") MultipartFile imageFile) {
		super();
		this.status = status;
		this.name = name;
		this.slug = slug;
		this.imageFile = imageFile;
	}

	public PurposeCreateDTO() {
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
