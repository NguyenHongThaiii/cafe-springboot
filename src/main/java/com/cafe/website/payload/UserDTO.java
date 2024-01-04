package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.Role;

public class UserDTO extends BaseEntityDTO {
	private String email;
	private String name;
	private String address;
	private String phone;
	private String slug;
	private List<Role> roles = new ArrayList<>();
	private ImageDTO image;
	private Boolean isWaitingDelete;

	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	public UserDTO(String email, String name, String address, String phone, String slug, List<Role> roles,
			ImageDTO image, Boolean isWaitingDelete) {
		super();
		this.email = email;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.slug = slug;
		this.roles = roles;
		this.image = image;
		this.isWaitingDelete = isWaitingDelete;
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

	public ImageDTO getImage() {
		return image;
	}

	public void setImage(ImageDTO image) {
		this.image = image;
	}

	public Boolean getIsWaitingDelete() {
		return isWaitingDelete;
	}

	public void setIsWaitingDelete(Boolean isWaitingDelete) {
		this.isWaitingDelete = isWaitingDelete;
	}

}
