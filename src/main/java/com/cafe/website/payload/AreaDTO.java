package com.cafe.website.payload;

import com.cafe.website.entity.BaseEntity;

public class AreaDTO extends BaseEntityDTO implements HasImageDTO {

	private String name;

	private String slug;

	private ImageDTO imageDto;

	public AreaDTO(int id, int status, Long createdAt, Long updatedAt, String name, String slug, ImageDTO imageDto) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.slug = slug;
		this.imageDto = imageDto;
	}

	public AreaDTO() {
		super();
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
	public void setImage(ImageDTO imageDto) {
		this.imageDto = imageDto;
	}

	public ImageDTO getImageDto() {
		return imageDto;
	}

	public void setImageDto(ImageDTO imageDto) {
		this.imageDto = imageDto;
	}

}
