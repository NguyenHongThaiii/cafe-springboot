package com.cafe.website.util;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.payload.FileMetadata;

public class MethodUtil {
	public static String handleSubstringMessage(String message) {
		if (message.length() > 254) {
			return message.substring(0, 255);
		} else {
			return message.substring(0, message.length());
		}
	}

	public static void convertListFileImageToInfo(List<FileMetadata> lf, List<MultipartFile> listImages) {
		if (listImages == null || lf == null)
			return;
		listImages.forEach(image -> {
			lf.add(new FileMetadata(image.getOriginalFilename(), image.getContentType(), image.getSize()));
		});
	}
}
