package com.cafe.website.entity;

import java.util.ArrayList;
import java.util.List;

import com.cafe.website.constant.RoleType;
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

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Review> reviews = new ArrayList<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductSaved> productSaved = new ArrayList<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> Product = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Token> tokens = new ArrayList<>();

	@Column(nullable = false, columnDefinition = "int default 0")
	private Boolean isWaitingDelete;

	public User(Long id, int status, String createdAt, String updatedAt, String email, String slug, String password,
			String name, String address, String phone, Image avatar, List<Role> roles, List<Review> reviews,
			List<Token> tokens, Boolean isWaitingDelete) {
		super(id, status, createdAt, updatedAt);
		this.email = email;
		this.slug = slug;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.avatar = avatar;
		this.roles = roles;
		this.reviews = reviews;
		this.tokens = tokens;
		this.isWaitingDelete = isWaitingDelete;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public Boolean getIsWaitingDelete() {
		return isWaitingDelete;
	}

	public void setIsWaitingDelete(Boolean isWaitingDelete) {

		this.isWaitingDelete = isWaitingDelete;
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

	@SuppressWarnings("unlikely-arg-type")
	public boolean isHasRoleAdmin(List<Role> roles) {
		boolean isHasRoleAdmin = false;
		for (Role role : roles) {
			if (role.getName().equals(RoleType.ROLE_ADMIN.toString())) {
				isHasRoleAdmin = true;
				break;
			}
		}

		return isHasRoleAdmin;
	}

	public boolean isHasRoleUser(List<Role> roles) {
		boolean isHasRoleUser = false;
		for (Role role : roles) {
			if (role.getName().equals(RoleType.ROLE_USER.toString())) {
				isHasRoleUser = true;
				break;
			}
		}

		return isHasRoleUser;
	}

	public boolean isHasRoleMod(List<Role> roles) {
		boolean isHasRoleMod = false;
		for (Role role : roles) {
			if (role.getName().equals(RoleType.ROLE_MOD.toString())) {
				isHasRoleMod = true;
				break;
			}
		}

		return isHasRoleMod;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", slug=" + slug + ", password=" + password + ", name=" + name + ", address="
				+ address + ", phone=" + phone + ", avatar=" + avatar + ", roles=" + roles + ", reviews=" + reviews
				+ ", tokens=" + tokens + ", isWaitingDelete=" + isWaitingDelete + "]";
	}

}
