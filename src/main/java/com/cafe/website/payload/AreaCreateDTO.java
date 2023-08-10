package com.cafe.website.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class AreaCreateDTO {

	@Min(value = 0, message = "Status should not be less than 0")
	@Max(value = 1, message = "status should not be greater than 1")
	private int status;
	
	@NotEmpty(message = "Name should not be null or empty")
	private String name;
	
	@NotEmpty(message = "Slug should not be null or empty")
	private String slug;
	
	@NotEmpty(message = "Image should not be null or empty")
	private String image;

	public AreaCreateDTO(int status, String name, String slug, String image) {
		super();
		this.status = status;
		this.name = name;
		this.slug = slug;
		this.image = image;
	}

	public AreaCreateDTO() {
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
