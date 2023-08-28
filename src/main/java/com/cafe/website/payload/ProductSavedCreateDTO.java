package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class ProductSavedCreateDTO {
	@NotNull
	private Integer userId;

	@NotNull
	private Integer productId;

	public ProductSavedCreateDTO(Integer userId, Integer productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}

	public ProductSavedCreateDTO() {
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

}
