package com.cafe.website.payload;

public class ConvenienceDTO extends BaseEntityDTO implements HasImageDTO {
	private String name;

	private String slug;

	private ImageDTO image;

	public ConvenienceDTO(int id, int status, Long createdAt, Long updatedAt, String name, String slug,
			ImageDTO image) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.slug = slug;
		this.image = image;
	}

	public ConvenienceDTO() {
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

	@Override
	public void setImage(ImageDTO image) {
		this.image = image;
	}

	public ImageDTO getImage() {
		return image;
	}

}
