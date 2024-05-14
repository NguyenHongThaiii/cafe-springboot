package com.cafe.website.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Value;

import com.cafe.website.validatior.FileSizeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileSizeValidator.class)
public @interface FileSize {
	String message() default "File size exceeds the limit";

	long max() default 1048576;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
