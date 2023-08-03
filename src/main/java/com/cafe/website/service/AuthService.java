package com.cafe.website.service;

import com.cafe.website.payload.LoginDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	String login(LoginDto loginDto);

	String refreshToken(HttpServletRequest request, HttpServletResponse response);
}
