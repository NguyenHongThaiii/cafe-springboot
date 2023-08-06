package com.cafe.website.constant;

public enum SearchField {
	NAME("name"), ID("id"), PRICEMIN("priceMin"), PRICEMAX("priceMax"), SLUG("slug");

	private final String value;

	SearchField(String value) {
		this.value = value;
	}
//
//	public String getValue() {
//		return value;
//	}

	@Override
	public String toString() {
		return value;
	}
}
