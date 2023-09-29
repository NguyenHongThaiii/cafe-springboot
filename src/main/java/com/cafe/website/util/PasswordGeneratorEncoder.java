package com.cafe.website.util;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder {
	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(
				passwordEncoder.matches("123123", "$2a$10$eFClZhQxVCG8LiEIgxrvwO.0o25hZcxteOv7ClxUG0mDVBZARgNiS"));
//        				final Slugify slg = Slugify.builder().build();
//		final String result = slg.slugify("nguyen-hong-thai23123213");
//		System.out.print(result);
//		String image = "http://res.cloudinary.com/th-i-nguy-n/image/upload/v1692103911/cafe-springboot/Users/lzcyx8ptzxett68n2z35.jpg";
//		String[] parts = image.split("/");
//		String lastPart = parts[parts.length - 1];
//		String idPart = lastPart.substring(0, lastPart.lastIndexOf("."));
//		System.out.print(idPart);
		String uniqueKey = UUID.randomUUID().toString();
		System.out.println(uniqueKey);
		Float a = 10f;
		System.out.println(a / 3);
	}

}
