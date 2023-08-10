package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryServiceImp implements CloudinaryService {
	private final Cloudinary cloudinary;
	private final String CLOUD_NAME = "th-i-nguy-n";
	private final String API_KEY = "979385842436938";
	private final String API_SECRET = "gHEh8vqsRSEZJ9YWmIsYMEK4_70";

	public CloudinaryServiceImp() {
		// Initialize Cloudinary instance with your configuration
		cloudinary = new Cloudinary(
				ObjectUtils.asMap("cloud_name", CLOUD_NAME, "api_key", API_KEY, "api_secret", API_SECRET));
	}

	@Override
	public String uploadFile(MultipartFile file, String folerName, String type) throws IOException {
		Map<String, Object> params = ObjectUtils.asMap("folder", folerName, "resource_type", type);

		Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
		return uploadResult.get("url").toString();
	}

}
