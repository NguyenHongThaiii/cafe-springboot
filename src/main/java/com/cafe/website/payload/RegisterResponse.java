package com.cafe.website.payload;

public class RegisterResponse {
	private String name;

	private String email;

	private String phone;
	private int status;
	private String createdAt;
	private String updatedAt;
	private String token;

	public RegisterResponse() {
		// TODO Auto-generated constructor stub
	}

	public RegisterResponse(String name, String email, String phone, int status, String createdAt, String updatedAt,
			String token) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.token = token;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
