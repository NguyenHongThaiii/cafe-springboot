package com.cafe.website.service;

import com.cafe.website.payload.LoginDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	String login(LoginDTO loginDto);

	String refreshToken(HttpServletRequest request, HttpServletResponse response);
}
