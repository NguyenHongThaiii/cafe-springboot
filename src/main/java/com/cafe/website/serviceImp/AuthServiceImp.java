package com.cafe.website.serviceImp;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.TokenType;
import com.cafe.website.entity.Token;
import com.cafe.website.entity.User;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.LoginDto;
import com.cafe.website.repository.RoleRepository;
import com.cafe.website.repository.TokenRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.security.JwtTokenProvider;
import com.cafe.website.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthServiceImp implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	private TokenRepository tokenRepository;
	private LogoutService logoutService;

	public AuthServiceImp(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
			TokenRepository tokenRepository, LogoutService logoutService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.tokenRepository = tokenRepository;
		this.logoutService = logoutService;
	}

	@Override
	public String login(LoginDto loginDto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);

//		save token to db and revoke all previous tokens
		User user = userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "user", 123));
		revokeAllUserTokens(user);
		saveUserToken(user, token);	

		return token;
	}

	@Override
	public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "";
	}

	private void saveUserToken(User user, String jwtToken) {
		Token token = Token.builder().user(user).name(jwtToken).tokenType(TokenType.BEARER).expired(false)
				.revoked(false).build();
		token.setStatus(1);
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}
}
