package com.cafe.website.payload;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class UpdateAvatarDTO extends FileMetadata {
	@NotNull
	private MultipartFile avatar;

	public UpdateAvatarDTO(@NotNull MultipartFile avatar) {
		super();
		this.avatar = avatar;
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

}
