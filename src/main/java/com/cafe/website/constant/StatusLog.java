package com.cafe.website.constant;

public enum StatusLog {
	SUCCESSFULLY("SUCCESSFULLY"), WARNING("WARNING"), FAILED("FAILED");

	private final String value;

	StatusLog(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
