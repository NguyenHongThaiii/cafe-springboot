package com.cafe.website.constant;

public enum RoleType {
	ROLE_ADMIN("ROLE_ADMIN"), ROLE_MOD("ROLE_MOD"), ROLE_USER("ROLE_USER");

	private final String value;

	RoleType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
