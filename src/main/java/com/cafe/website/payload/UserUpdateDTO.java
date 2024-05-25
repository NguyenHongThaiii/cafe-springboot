package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.Role;

public class UserUpdateDTO {
	private String password;
	private String name;
	private String address;
	private String phone;
	private int status;
	private String slug;
	private List<Product> listProductSaved = new ArrayList<>();
	private List<Long> roles;

	public UserUpdateDTO() {
		// TODO Auto-generated constructor stub
		this.status = 1;

	}

	public UserUpdateDTO(String password, String name, String address, String phone, int status, String slug,
			List<Product> listProductSaved, List<Long> roles) {
		super();
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.status = status;
		this.slug = slug;
		this.listProductSaved = listProductSaved;
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public List<Product> getListProductSaved() {
		return listProductSaved;
	}

	public void setListProductSaved(List<Product> listProductSaved) {
		this.listProductSaved = listProductSaved;
	}

	public List<Long> getRoles() {
		return roles;
	}

	public void setRoles(List<Long> roles) {
		this.roles = roles;
	}

}
