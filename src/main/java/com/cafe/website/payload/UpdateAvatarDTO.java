package com.cafe.website.payload;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class UpdateAvatarDTO {
	@NotNull
	private MultipartFile avatar;

	@NotNull
	private String slug;

	public UpdateAvatarDTO(@NotNull MultipartFile avatar, @NotNull String slug) {
		super();
		this.avatar = avatar;
		this.slug = slug;
	}

	public UpdateAvatarDTO() {
		// TODO Auto-generated constructor stub
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

}
