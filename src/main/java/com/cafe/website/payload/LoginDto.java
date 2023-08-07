package com.cafe.website.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDto {
	@NotEmpty(message = "Name should not be null or empty")
	@Size(min = 6, max = 20)
	private String email;

	@NotEmpty(message = "Name should not be null or empty")
	@Size(min = 6, max = 20)
	private String password;

	public LoginDto(@NotEmpty(message = "Name should not be null or empty") @Size(min = 6, max = 20) String email,
			@NotEmpty(message = "Name should not be null or empty") @Size(min = 6, max = 20) String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public LoginDto() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
