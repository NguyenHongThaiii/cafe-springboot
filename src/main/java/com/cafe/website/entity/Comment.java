package com.cafe.website.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
//import com.cafe.website.entity.BaseEntity;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
	private String name;
	private int favorite;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "review_id", nullable = false)
	private Review review;

	public Comment(int id, int status, Long createdAt, Long updatedAt, String name, int favorite, Review review) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.favorite = favorite;
		this.review = review;
	}

	public Comment(int id, int status, Long createdAt, Long updatedAt) {
		super(id, status, createdAt, updatedAt);
	}

	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

}
