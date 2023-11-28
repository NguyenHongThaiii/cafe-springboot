package com.cafe.website.serviceImp;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.StatusLog;
import com.cafe.website.entity.Token;
import com.cafe.website.repository.TokenRepository;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.LogService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutService implements LogoutHandler {
	@Autowired
	private TokenRepository tokenRepository;
	@Lazy
	@Autowired
	private LogService logService;
	@Lazy
	@Autowired
	private AuthService authService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		jwt = authHeader.substring(7);
		Token storedToken = tokenRepository.findByName(jwt).orElse(null);
		if (storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
			SecurityContextHolder.clearContext();
		}
		logService.createLog(request, authService.getUserFromHeader(request), "Logout User SUCCESSFULY",
				StatusLog.SUCCESSFULLY.toString(), "", "Logout");
	}

}
