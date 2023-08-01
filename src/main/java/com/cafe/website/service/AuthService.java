package com.cafe.website.service;

import com.cafe.website.payload.LoginDto;

public interface AuthService {
	String login(LoginDto loginDto);
}
