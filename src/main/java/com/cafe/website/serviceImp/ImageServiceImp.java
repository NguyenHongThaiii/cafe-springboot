package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImageServiceImp implements ImageService {
	private CloudinaryService cloudinaryService;

	public ImageServiceImp(CloudinaryService cloudinaryService) {
		this.cloudinaryService = cloudinaryService;
	}

	ObjectMapper objMapper = new ObjectMapper();

	@Override
	public void deleteImage(String publicId) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void uploadImages(List<String> listImages, List<MultipartFile> listFiles, String path, String typeUpload)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public String uploadImage(MultipartFile file, String path, String typeUpload) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeListImageFromCloudinary(String listMenusDb, String path) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeImageFromCloudinary(String image, String path) throws IOException {
		// TODO Auto-generated method stub

	}

}
