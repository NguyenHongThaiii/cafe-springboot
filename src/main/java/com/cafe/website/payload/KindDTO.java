package com.cafe.website.payload;

import com.cafe.website.entity.BaseEntity;

public class KindDTO extends BaseEntity implements HasImageDTO {
	private String name;

	private String slug;

	private ImageDTO imageDto;

	public KindDTO(int id, int status, Long createdAt, Long updatedAt, String name, String slug, ImageDTO imageDto) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.slug = slug;
		this.imageDto = imageDto;
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

	public ImageDTO getImageDto() {
		return imageDto;
	}

	public void setImageDto(ImageDTO imageDto) {
		this.imageDto = imageDto;
	}

	@Override
	public void setImage(ImageDTO imageDto) {
		// TODO Auto-generated method stub

	}

}
