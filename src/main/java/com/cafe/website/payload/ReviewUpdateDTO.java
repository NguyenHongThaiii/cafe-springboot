package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.entity.Rating;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;

public class ReviewUpdateDTO {
	@NotNull
	private Long userId;
	private Rating rating;

	private List<MultipartFile> listImageFiles = new ArrayList<>();

	private String name;

	private int status;
	private Integer outstanding;
	@JsonIgnoreProperties(ignoreUnknown = true)
	private List<FileMetadata> listFileMetadatas = new ArrayList<>();

	public ReviewUpdateDTO() {
		// TODO Auto-generated constructor stub
//		this.status = 1;

	}

	public ReviewUpdateDTO(@NotNull Long userId, Rating rating, List<MultipartFile> listImageFiles, String name,
			int status, Integer outstanding, List<FileMetadata> listFileMetadatas) {
		super();
		this.userId = userId;
		this.rating = rating;
		this.listImageFiles = listImageFiles;
		this.name = name;
		this.status = status;
		this.outstanding = outstanding;
		this.listFileMetadatas = listFileMetadatas;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public List<MultipartFile> getListImageFiles() {
		return listImageFiles;
	}

	public void setListImageFiles(List<MultipartFile> listImageFiles) {
		this.listImageFiles = listImageFiles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(Integer outstanding) {
		this.outstanding = outstanding;
	}

	public List<FileMetadata> getListFileMetadatas() {
		return listFileMetadatas;
	}

	public void setListFileMetadatas(List<FileMetadata> listFileMetadatas) {
		this.listFileMetadatas = listFileMetadatas;
	}

}
