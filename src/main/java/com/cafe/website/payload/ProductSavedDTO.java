package com.cafe.website.payload;

import java.util.List;

public class ProductSavedDTO {
	private UserDTO userDto;
	private List<ProductDTO> listProductDto;

	public ProductSavedDTO(UserDTO userDto, List<ProductDTO> listProductDto) {
		super();
		this.userDto = userDto;
		this.listProductDto = listProductDto;
	}

	public ProductSavedDTO() {
		// TODO Auto-generated constructor stub
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}

	public List<ProductDTO> getListProductDto() {
		return listProductDto;
	}

	public void setListProductDto(List<ProductDTO> listProductDto) {
		this.listProductDto = listProductDto;
	}

}
