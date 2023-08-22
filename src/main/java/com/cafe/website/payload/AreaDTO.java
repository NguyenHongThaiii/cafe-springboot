package com.cafe.website.payload;

public class AreaDTO {
	private int id;
	private Long createdAt;
	private Long updatedAt;
	private int status;

	private String name;

	private String slug;

	private ImageDTO imageDto;

	public AreaDTO(int id, int status, Long createdAt, Long updatedAt, String name, String slug, ImageDTO imageDto) {
		super();
		this.id = id;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.name = name;
		this.slug = slug;
		this.imageDto = imageDto;
	}

	public AreaDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
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

	public ImageDTO getImage() {
		return imageDto;
	}

	public void setImage(ImageDTO imageDto) {
		this.imageDto = imageDto;
	}

}
