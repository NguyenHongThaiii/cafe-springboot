package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class ChangePasswordDTO extends ResetPasswordDTO {
	@NotNull
	private String oldPassword;

	public ChangePasswordDTO() {
		// TODO Auto-generated constructor stub
	}

	public ChangePasswordDTO(@NotNull String email, @NotNull String password, @NotNull String retypePassword,
			@NotNull String oldPassword) {
		super(email, password, retypePassword);
		this.oldPassword = oldPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
