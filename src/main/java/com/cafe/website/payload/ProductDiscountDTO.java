package com.cafe.website.payload;

public class ProductDiscountDTO extends BaseEntityDTO {
	private ProductDTO productDto;
	private Integer percent;
	private String name;
	private Long expiryDate;

	public ProductDiscountDTO(int id, Integer status, Long createdAt, Long updatedAt, ProductDTO productDto,
			Integer percent, String name, Long expiryDate) {
		super(id, status, createdAt, updatedAt);
		this.productDto = productDto;
		this.percent = percent;
		this.name = name;
		this.expiryDate = expiryDate;
	}

	public ProductDiscountDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public ProductDTO getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDTO productDto) {
		this.productDto = productDto;
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
