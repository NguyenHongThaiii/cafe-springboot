package com.cafe.website.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

	public String uploadFile(MultipartFile file, String folderName, String type) throws IOException;
}
