package com.cafe.website.payload;

public class PurposeDTO extends BaseEntityDTO implements HasImageDTO {
	private String name;

	private String slug;

	private ImageDTO image;

	public PurposeDTO() {
		// TODO Auto-generated constructor stub
	}

	public PurposeDTO(int id, Integer status, String createdAt, String updatedAt, String name, String slug,
			ImageDTO image) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.slug = slug;
		this.image = image;
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
		return image;
	}

	@Override
	public void setImage(ImageDTO image) {
		this.image = image;

	}

}
