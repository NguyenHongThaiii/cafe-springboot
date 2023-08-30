package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class ReviewCreateDTO {
	@NotNull
	private Integer userId;
	@NotNull
	private Integer productId;

	@NotNull
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

	public ReviewCreateDTO(@NotNull Integer userId, @NotNull Integer productId, @NotNull List<MultipartFile> listImageFiles,
			@NotNull String name, @NotNull int location, @NotNull int space, @NotNull int food, @NotNull int service,
			@NotNull int price) {
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
	}

	public ReviewCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<MultipartFile> getlistImageFiles() {
		return listImageFiles;
	}

	public void setlistImageFiles(List<MultipartFile> listImageFiles) {
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

}
