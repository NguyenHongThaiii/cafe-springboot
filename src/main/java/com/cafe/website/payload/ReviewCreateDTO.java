package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReviewCreateDTO {
	@NotNull
	private Long userId;
	@NotNull
	private Long productId;

	private List<MultipartFile> listImageFiles = new ArrayList<>();
	@NotNull
	private String name;
	@NotNull
	private int location;
	@NotNull
	private int space;
	@NotNull
	private int food;
	@NotNull
	private int service;
	@NotNull
	private int price;
	@Min(value = 0, message = "status should not be less than 0")
	@Max(value = 1, message = "status should not be greater than 1")
	private Integer outstanding;
	@JsonIgnoreProperties(ignoreUnknown = true)
	private List<FileMetadata> listFileMetadatas = new ArrayList<>();

	public ReviewCreateDTO(@NotNull Long userId, @NotNull Long productId, List<MultipartFile> listImageFiles,
			@NotNull String name, @NotNull int location, @NotNull int space, @NotNull int food, @NotNull int service,
			@NotNull int price, Integer outstanding, List<FileMetadata> listFileMetadatas) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.listImageFiles = listImageFiles;
		this.name = name;
		this.location = location;
		this.space = space;
		this.food = food;
		this.service = service;
		this.price = price;
		this.outstanding = outstanding;
		this.listFileMetadatas = listFileMetadatas;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getService() {
		return service;
	}

	public void setService(int service) {
		this.service = service;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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
