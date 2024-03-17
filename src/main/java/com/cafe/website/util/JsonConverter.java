package com.cafe.website.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class JsonConverter {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String convertToJSON(String key, Object value) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		return objectMapper.writeValueAsString(map);
	}

	public static void main(String[] args) {
		try {
			String jsonString = convertToJSON("slug", "azxczxc");
			System.out.println(jsonString); // {"slug":"azxczxc"}

			String jsonInteger = convertToJSON("number", 123);
			System.out.println(jsonInteger); // {"number":123}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
