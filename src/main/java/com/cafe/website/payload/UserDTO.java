package com.cafe.website.payload;
import java.util.List;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.Role;

public class UserDTO {
	private int id;
	private int status;
	private String email;
	private String password;
	private String name;
	private String address;
	private String avartar;
	private String phone;
	private String slug;
	private List<Product> listProductSaved;
	private List<Role> roles;

	public UserDTO(int id, int status, String email, String password, String name, String address, String avartar,
			String phone, String slug, List<Product> listProductSaved, List<Role> roles) {
		super();
		this.id = id;
		this.status = status;
		this.email = email;
		this.password = password;
		this.name = name;
		this.address = address;
		this.avartar = avartar;
		this.phone = phone;
		this.slug = slug;
		this.listProductSaved = listProductSaved;
		this.roles = roles;
	}

	public UserDTO() {
		// TODO Auto-generated constructor stub
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserDTO [status=" + status + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", address=" + address + ", avartar=" + avartar + ", phone=" + phone + ", slug=" + slug
				+ ", listProductSaved=" + listProductSaved + ", roles=" + roles + "]";
	}

}
