package com.cafe.website.util;

import com.cafe.website.payload.FileMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class FileMetadataContainer {
	private List<FileMetadata> fileMetadataList;

	public FileMetadataContainer() {
		this.fileMetadataList = new ArrayList<>();
	}

	public List<FileMetadata> getFileMetadataList() {
		return fileMetadataList;
	}

	public void setFileMetadataList(List<FileMetadata> fileMetadataList) {
		this.fileMetadataList = fileMetadataList;
	}

	public void addFileMetadata(FileMetadata fileMetadata) {
		this.fileMetadataList.add(fileMetadata);
	}

	public static void main(String[] args) {
		FileMetadataContainer container = new FileMetadataContainer();

		// Thêm các đối tượng FileMetadata vào mảng trong lớp container
		container.addFileMetadata(new FileMetadata("file1.txt", "text/plain", 1024));
		container.addFileMetadata(new FileMetadata("file2.jpg", "image/jpeg", 2048));

		// Sử dụng ObjectMapper để chuyển đối tượng của lớp container thành đối tượng
		// JSON
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonString = objectMapper.writeValueAsString(container);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
