package com.cafe.website.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
	private String avartar;
	private String phone;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "products_saved", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
	private List<Product> listProductSaved;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Review> reviews;

	@OneToMany(mappedBy = "user")
	private List<Token> tokens;

	public User(int id, int status, Long createdAt, Long updatedAt, String name, String address, String avartar,
			String phone, String email, String password, List<Product> listProductSaved, List<Role> roles,
			List<Review> reviews, List<Token> tokens, String slug) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.address = address;
		this.avartar = avartar;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.listProductSaved = listProductSaved;
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

	@Override
	public String toString() {
		return "User [email=" + email + ", slug=" + slug + ", password=" + password + ", name=" + name + ", address="
				+ address + ", avartar=" + avartar + ", phone=" + phone + ", listProductSaved=" + listProductSaved
				+ ", roles=" + roles + ", reviews=" + reviews + ", tokens=" + tokens + "]";
	}

}
