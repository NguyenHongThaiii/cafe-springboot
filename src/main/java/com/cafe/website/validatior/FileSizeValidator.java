package com.cafe.website.validatior;

import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.annotation.FileSize;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {
	private long maxFileSize;

	@Override
	public void initialize(FileSize constraintAnnotation) {
		this.maxFileSize = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return value.getSize() <= maxFileSize;
	}

}
