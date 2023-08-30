package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.entity.Rating;

public class ReviewUpdateDTO {

	private Rating rating;

	private List<MultipartFile> listImageFiles = new ArrayList<>();

	private String name;

	private int status;
	
	public ReviewUpdateDTO(Rating rating, List<MultipartFile> listImageFiles, String name, int status) {
		super();
		this.rating = rating;
		this.listImageFiles = listImageFiles;
		this.name = name;
		this.status = status;
	}

	public ReviewUpdateDTO() {
		// TODO Auto-generated constructor stub
//		this.status = 1;

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

}
