package com.cafe.website.payload;

public class KindDTO extends BaseEntityDTO implements HasImageDTO {
	private String name;

	private String slug;

	private ImageDTO image;

	public KindDTO(int id, Integer status, String createdAt, String updatedAt, String name, String slug,
			ImageDTO image) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.slug = slug;
		this.image = image;
	}

	public KindDTO() {
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

	public ImageDTO getImage() {
		return image;
	}

	@Override
	public void setImage(ImageDTO image) {
		this.image = image;

	}

}
