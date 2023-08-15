package com.cafe.website.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	private String fieldValueString;
	private UUID fieldUUID;


	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValueString) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValueString));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValueString = fieldValueString;
	}

	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public UUID getFieldUUID() {
		return fieldUUID;
	}

	public String getFieldValueString() {
		return fieldValueString;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public long getFieldValue() {
		return fieldValue;
	}
}
