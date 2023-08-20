package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
//	String uploadFile(MultipartFile file, String folderName, String type) throws IOException;

	void deleteImage(String publicId) throws IOException;

	void uploadImages(List<String> listImages, List<MultipartFile> listFiles, String path, String typeUpload)
			throws IOException;

	String uploadImage(MultipartFile file, String path, String typeUpload) throws IOException;

	void removeListImageFromCloudinary(String listMenusDb, String path) throws IOException;

	void removeImageFromCloudinary(String image, String path) throws IOException;
}
