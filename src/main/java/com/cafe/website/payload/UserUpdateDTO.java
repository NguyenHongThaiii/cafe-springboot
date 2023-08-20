package com.cafe.website.payload;
import java.util.List;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.Role;

public class UserUpdateDTO {
	private String email;
	private String password;
	private String name;
	private String address;
	private String avartar;
	private String phone;
	private int status;
	private String slug;
	private List<Product> listProductSaved;

	private List<Role> roles;

	public UserUpdateDTO(String email, String password, String name, String address, String avartar, String phone,
			int status, List<Product> listProductSaved, List<Role> roles, String slug) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.address = address;
		this.avartar = avartar;
		this.phone = phone;
		this.status = status;
		this.listProductSaved = listProductSaved;
		this.roles = roles;
		this.slug = slug;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAvartar() {
		return avartar;
	}

	public void setAvartar(String avartar) {
		this.avartar = avartar;
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

	public List<Product> getListProductSaved() {
		return listProductSaved;
	}

	public void setListProductSaved(List<Product> listProductSaved) {
		this.listProductSaved = listProductSaved;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserUpdateDTO [email=" + email + ", password=" + password + ", name=" + name + ", address=" + address
				+ ", avartar=" + avartar + ", phone=" + phone + ", status=" + status + ", listProductSaved="
				+ listProductSaved + ", roles=" + roles + "]";
	}

}
