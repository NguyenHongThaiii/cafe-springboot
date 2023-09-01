package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class ProductDeleteDTO {
	@NotNull
	Integer productId;
	@NotNull
	Integer userId;

	public ProductDeleteDTO(Integer productId, Integer userId) {
		super();
		this.productId = productId;
		this.userId = userId;
	}

	public ProductDeleteDTO() {
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
