package com.cafe.website.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileMetadata {
	private String originalName;
	private String contentType;
	private long size;

	public FileMetadata() {
		// TODO Auto-generated constructor stub
	}

	public FileMetadata(String originalName, String contentType, long size) {
		super();
		this.originalName = originalName;
		this.contentType = contentType;
		this.size = size;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setDataToLogging(String originName, String contentType, Long size, Runnable callback) {
		this.setContentType(contentType);
		this.setOriginalName(originName);
		this.setSize(size);
		if (callback != null) {
			callback.run();
		}
	}
}
