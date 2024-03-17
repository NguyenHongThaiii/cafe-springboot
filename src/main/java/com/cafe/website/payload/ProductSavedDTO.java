package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

public class ProductSavedDTO extends BaseEntityDTO {
	private UserDTO userDto;
	private List<ProductDTO> listProductDto = new ArrayList<>();

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
