package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class ProductDiscountCreateDTO {
	@NotNull
	private Long productId;
	@NotNull
	private Integer percent;
	@NotNull
	private String name;
	@NotNull
	private Long expiryDate;

	public ProductDiscountCreateDTO(@NotNull Long productId, @NotNull Integer percent, @NotNull String name,
			@NotNull Long expiryDate) {
		super();
		this.productId = productId;
		this.percent = percent;
		this.name = name;
		this.expiryDate = expiryDate;
	}

	public ProductDiscountCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
