package com.cafe.website.payload;

public class PurposeDTO extends BaseEntityDTO implements HasImageDTO {
	private String name;

	private String slug;

	private ImageDTO imageDto;

	public PurposeDTO(int id, int status, Long createdAt, Long updatedAt, String name, String slug, ImageDTO imageDto) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.slug = slug;
		this.imageDto = imageDto;
	}

	public PurposeDTO() {
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

	public ImageDTO getImageDto() {
		return imageDto;
	}

	public void setImageDto(ImageDTO imageDto) {
		this.imageDto = imageDto;
	}

	@Override
	public void setImage(ImageDTO imageDto) {
		this.imageDto = imageDto;

	}

}
