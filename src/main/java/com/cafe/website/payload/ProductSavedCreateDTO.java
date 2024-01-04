package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class ProductSavedCreateDTO {
	@NotNull
	private Long userId;

	@NotNull
	private Long productId;

	public ProductSavedCreateDTO(Long userId, Long productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}

	public ProductSavedCreateDTO() {
		// TODO Auto-generated constructor stub
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

}
