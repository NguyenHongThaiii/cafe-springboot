package com.cafe.website.payload;

public class ResetDTO {
	private String password;
	private String email;
	private String otp;

	public ResetDTO(String password, String email, String otp) {
		super();
		this.password = password;
		this.email = email;
		this.otp = otp;
	}

	public ResetDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

}
