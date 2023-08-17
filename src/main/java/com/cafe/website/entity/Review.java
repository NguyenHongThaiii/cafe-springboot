package com.cafe.website.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "reviews")
@Entity
public class Review extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "rating_id", nullable = false)
	private Rating rating;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;

	private String listImages;
	private String name;

	@ManyToMany( fetch = FetchType.LAZY)
	@JoinTable(name = "review_favorites", joinColumns = @JoinColumn(name = "review_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> favoritedBy;

	public Review(int id, int status, Long createdAt, Long updatedAt, User user, Product product, Rating rating,
			List<Comment> comments, String listImages, String name, List<User> favoritedBy) {
		super(id, status, createdAt, updatedAt);
		this.user = user;
		this.product = product;
		this.rating = rating;
		this.comments = comments;
		this.listImages = listImages;
		this.name = name;
		this.favoritedBy = favoritedBy;
	}

	public Review() {
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getListImages() {
		return listImages;
	}

	public void setListImages(String listImages) {
		this.listImages = listImages;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getFavorite() {
		return favoritedBy;
	}

	public void setFavorite(List<User> favoritedBy) {
		this.favoritedBy = favoritedBy;
	}

}
