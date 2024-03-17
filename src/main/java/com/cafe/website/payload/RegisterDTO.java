package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class RegisterDTO {
	@NotNull
	private String name;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String phone;

	public RegisterDTO(@NotNull String name, @NotNull String email, @NotNull String password, @NotNull String phone) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	public RegisterDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "RegisterDTO [name=" + name + ", email=" + email + ", password=" + password + ", phone=" + phone + "]";
	}

}
