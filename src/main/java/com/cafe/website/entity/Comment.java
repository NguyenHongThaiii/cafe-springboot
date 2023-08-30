package com.cafe.website.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
//import com.cafe.website.entity.BaseEntity;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
	private String name;
// productId

	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Favorite> favorites = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id", nullable = false)
	private Review review;

	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(int id, int status, Long createdAt, Long updatedAt, String name, List<Favorite> favorites, User user,
			Review review) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.favorites = favorites;
		this.user = user;
		this.review = review;
	}

	public List<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

}
