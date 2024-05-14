package com.cafe.website.payload;

import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.annotation.CheckStatus;
import com.cafe.website.annotation.FileSize;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BaseCatCreateDTO extends FileMetadata {
	@CheckStatus(allowedValues = { 0, 1, 2 })
	@NotNull
	private int status;

	@NotEmpty(message = "Name should not be null or empty")
	@Size(min = 5, max = 50)
	private String name;
	@Size(min = 5, max = 50)
	@NotNull(message = "Slug should not be null or empty")
	private String slug;

	@NotNull(message = "Image should not be null or empty")
	@FileSize(max = 1048576 * 5)
	private MultipartFile imageFile;

	public BaseCatCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public BaseCatCreateDTO(@NotNull int status,
			@NotEmpty(message = "Name should not be null or empty") @Size(min = 5, max = 50) String name,
			@Size(min = 5, max = 50) @NotNull(message = "Slug should not be null or empty") String slug,
			@NotNull(message = "Image should not be null or empty") MultipartFile imageFile) {
		super();
		this.status = status;
		this.name = name;
		this.slug = slug;
		this.imageFile = imageFile;
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
