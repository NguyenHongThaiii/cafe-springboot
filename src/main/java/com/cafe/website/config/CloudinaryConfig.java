package com.cafe.website.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
	private final String CLOUD_NAME = "th-i-nguy-n";
	private final String API_KEY = "979385842436938";
	private final String API_SECRET = "gHEh8vqsRSEZJ9YWmIsYMEK4_70";

	@Bean
	public Cloudinary cloudinary() {
		Map<String, String> config = new HashMap<>();
		config.put("cloud_name", CLOUD_NAME);
		config.put("api_key", API_KEY);
		config.put("api_secret", API_SECRET);
		return new Cloudinary(config);
	}
}
