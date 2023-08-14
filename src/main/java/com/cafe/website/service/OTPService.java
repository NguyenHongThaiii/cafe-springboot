package com.cafe.website.service;

public interface OTPService {
	String generateAndStoreOtp(String email);

	String getOtpByEmail(String email);

	String generateAndStoreAnotherData(String otp);

	String getOtpBySession(String otp);

	void clearCache(String name);
}
