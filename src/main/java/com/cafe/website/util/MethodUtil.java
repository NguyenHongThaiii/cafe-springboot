package com.cafe.website.util;

public class MethodUtil {
	public static String handleSubstringMessage(String message) {
		if (message.length() > 254) {
			return message.substring(0, 255);
		} else {
			return message.substring(0, message.length());
		}
	}
}
