package com.cafe.website.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "users")
@Entity
public class User extends BaseEntity {
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String slug;
	private String password;
	@Column(unique = true)
	private String name;
	private String address;
	private String phone;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Image avatar;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Token> tokens = new ArrayList<>();

	public User(int id, int status, Long createdAt, Long updatedAt, String name, String address, Image avartar,
			String phone, String email, String password, List<Role> roles, List<Review> reviews, List<Token> tokens,
			String slug) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.address = address;
		this.avatar = avartar;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.reviews = reviews;
		this.tokens = tokens;
		this.slug = slug;
	}

	public User(int id, int status, Long createdAt, Long updatedAt) {
		super(id, status, createdAt, updatedAt);
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
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

	public Image getAvatar() {
		return avatar;
	}

	public void setAvatar(Image avartar) {
		this.avatar = avartar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

}
