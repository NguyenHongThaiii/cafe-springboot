package com.cafe.website.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "rating_id", nullable = false)
	private Rating rating;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Image> listImages = new ArrayList<>();
	@Column(length = 2000)
	private String name;
	private Integer outstanding;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Column(unique = true)
	private List<Favorite> favorites = new ArrayList<>();

	public Review() {
		// TODO Auto-generated constructor stub
	}

	public Review(User user, Product product, Rating rating, List<Comment> comments, List<Image> listImages,
			String name, Integer outstanding, List<Favorite> favorites) {
		super();
		this.user = user;
		this.product = product;
		this.rating = rating;
		this.comments = comments;
		this.listImages = listImages;
		this.name = name;
		this.outstanding = outstanding;
		this.favorites = favorites;
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

	public List<Image> getListImages() {
		return listImages;
	}

	public void setListImages(List<Image> listImages) {
		this.listImages = listImages;
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getAverageRating() {
		return this.rating.getAverageRating();
	}

	public Integer getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(Integer outstanding) {
		this.outstanding = outstanding;
	}

}
