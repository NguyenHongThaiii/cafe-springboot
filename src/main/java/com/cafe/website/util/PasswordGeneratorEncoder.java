package com.cafe.website.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.slugify.Slugify;

public class PasswordGeneratorEncoder {
	public static void main(String[] args) {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        System.out.println(passwordEncoder.encode("123123"));
//        System.out.println(passwordEncoder.encode("ramesh"));
//    	final Slugify slg = Slugify.builder().build();
//    	final String result = slg.slugify("Nguyễn Hồng Thái23123213!");
//    	System.out.print(result);
		String image = "http://res.cloudinary.com/th-i-nguy-n/image/upload/v1692103911/cafe-springboot/Users/lzcyx8ptzxett68n2z35.jpg";
		String[] parts = image.split("/");
		String lastPart = parts[parts.length - 1];
		String idPart = lastPart.substring(0, lastPart.lastIndexOf("."));
		System.out.print(idPart);
	}
}
