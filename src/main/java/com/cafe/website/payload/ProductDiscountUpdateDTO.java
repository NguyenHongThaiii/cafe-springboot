package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class ProductDiscountUpdateDTO {

	@NotNull
	private Integer percent;
	@NotNull
	private String name;
	@NotNull
	private Long expiryDate;

	public ProductDiscountUpdateDTO(Integer percent, String name, Long expiryDate) {
		super();
		this.percent = percent;
		this.name = name;
		this.expiryDate = expiryDate;
	}

	public ProductDiscountUpdateDTO() {
		// TODO Auto-generated constructor stub
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

	public Long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Long expiryDate) {
		this.expiryDate = expiryDate;
	}

}
