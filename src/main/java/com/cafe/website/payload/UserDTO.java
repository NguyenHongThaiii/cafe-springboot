package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.Role;

public class UserDTO {
	private int id;
	private int status;
	private String email;
	private String name;
	private String address;
	private String phone;
	private String slug;
	private List<Role> roles = new ArrayList<>();
	private ImageDTO imageDto;

	public UserDTO(int id, int status, String email, String name, String address, String phone, String slug,
			List<Role> roles, ImageDTO imageDto) {
		super();
		this.id = id;
		this.status = status;
		this.email = email;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.slug = slug;
		this.roles = roles;
		this.imageDto = imageDto;
	}

	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	public ImageDTO getImageDto() {
		return imageDto;
	}

	public void setImageDto(ImageDTO imageDto) {
		this.imageDto = imageDto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
