package com.cafe.website.payload;

import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.annotation.CheckStatus;
import com.cafe.website.annotation.FileSize;

import jakarta.validation.constraints.Size;

public class AreaUpdateDTO {
	@CheckStatus(allowedValues = { 0, 1 })
	private int status;
	@Size(min = 5, max = 50)
	private String name;
	@Size(min = 5, max = 50)
	private String slug;
	@FileSize(max = 1048576 * 5)
	private MultipartFile imageFile;

	public AreaUpdateDTO() {
		super();
		this.status = 1;

	}

	public AreaUpdateDTO(int status, @Size(min = 5, max = 50) String name, @Size(min = 5, max = 50) String slug,
			MultipartFile imageFile) {
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
