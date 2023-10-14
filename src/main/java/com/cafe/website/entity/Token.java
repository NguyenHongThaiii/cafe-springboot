package com.cafe.website.entity;

import com.cafe.website.constant.TokenType;
//import com.cafe.website.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens")
public class Token extends BaseEntity {

	@Column(unique = true)
	public String name;

	@Enumerated(EnumType.STRING)
	public TokenType tokenType = TokenType.BEARER;

	public boolean revoked;
	public boolean expired;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User user;

	public Token(String name, TokenType tokenType, boolean revoked, boolean expired, User user) {
		this.name = name;
		this.tokenType = tokenType;
		this.revoked = revoked;
		this.expired = expired;
		this.user = user;
	}

	public Token(int id, int status, String createdAt, String updatedAt, String name, TokenType tokenType,
			boolean revoked, boolean expired, User user) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.tokenType = tokenType;
		this.revoked = revoked;
		this.expired = expired;
		this.user = user;
	}

	public Token() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}