package com.cafe.website.payload;

public class BaseCatDTO extends BaseEntityDTO {
	private String name;
	private String slug;
	private String image;

	public BaseCatDTO(Long id, Integer status, String createdAt, String updatedAt, String name, String slug,
			String image) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.slug = slug;
		this.image = image;
	}

	public BaseCatDTO() {
		// TODO Auto-generated constructor stub
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
