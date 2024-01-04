package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class ProductDeleteDTO {
	@NotNull
	Long productId;
	@NotNull
	Long userId;

	public ProductDeleteDTO(Long productId, Long userId) {
		super();
		this.productId = productId;
		this.userId = userId;
	}

	public ProductDeleteDTO() {
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
