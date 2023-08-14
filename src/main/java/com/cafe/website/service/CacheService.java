package com.cafe.website.service;

public interface CacheService {

	String generateAndStoreOtp(String email);

	String getOtpByEmail(String email);

}
