package com.cafe.website.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "notifications_users", joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> users = new ArrayList<>();
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "notifications_senders", joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "sender_id", referencedColumnName = "id"))
	private List<User> senders = new ArrayList<>();
	private String url;
	private String message;
	private String original;
	private Integer state;

	public Notification() {
		// TODO Auto-generated constructor stub
	}

	public Notification(List<User> users, List<User> senders, String url, String message, String original,
			Integer state) {
		super();
		this.users = users;
		this.senders = senders;
		this.url = url;
		this.message = message;
		this.original = original;
		this.state = state;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getSenders() {
		return senders;
	}

	public void setSenders(List<User> senders) {
		this.senders = senders;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Notification [url=" + url + ", message=" + message + ", original=" + original + ", state=" + state
				+ "]";
	}

}
