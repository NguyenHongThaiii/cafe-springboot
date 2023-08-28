package com.cafe.website.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "favorites", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "review_id" }),
		@UniqueConstraint(columnNames = { "user_id", "comment_id" }) })

public class Favorite extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	private Comment comment;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	private Review review;

	public Favorite(int id, int status, Long createdAt, Long updatedAt, Comment comment, User user, Review review) {
		super(id, status, createdAt, updatedAt);
		this.comment = comment;
		this.user = user;
		this.review = review;
	}

	public Favorite() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
