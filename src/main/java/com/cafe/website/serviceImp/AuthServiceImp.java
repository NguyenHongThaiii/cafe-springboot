package com.cafe.website.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.TokenType;
import com.cafe.website.entity.Role;
import com.cafe.website.entity.Token;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.LoginDTO;
import com.cafe.website.payload.RegisterDTO;
import com.cafe.website.payload.RegisterResponse;
import com.cafe.website.payload.UserDTO;
import com.cafe.website.payload.UserUpdateDTO;
import com.cafe.website.payload.ValidateOtpDTO;
import com.cafe.website.repository.RoleRepository;
import com.cafe.website.repository.TokenRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.security.JwtTokenProvider;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.EmailService;
import com.cafe.website.service.OTPService;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.UserMapper;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
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
	private EmailService emailService;
	private OTPService otpService;
	private UserMapper userMapper;
	private Slugify slug = Slugify.builder().build();

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public AuthServiceImp(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
			TokenRepository tokenRepository, EmailService emailService, OTPService otpService, UserMapper userMapper) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.tokenRepository = tokenRepository;
		this.emailService = emailService;
		this.userMapper = userMapper;
		this.otpService = otpService;
	}

	@Override
	public String login(LoginDTO loginDto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);

		User user = userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "user", loginDto.getEmail()));
		revokeAllUserTokens(user);
		saveUserToken(user, token);

		return token;
	}

	@Override
	public RegisterResponse createUser(RegisterDTO registerDto) {
		String passwordEncode = passwordEncoder.encode(registerDto.getPassword());

		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}

		User user = MapperUtils.mapToEntity(registerDto, User.class);
		user.setPassword(passwordEncode);
		user.setSlug(slug.slugify(user.getName()));

		List<Role> roles = new ArrayList<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		String otp = otpService.generateAndStoreOtp(user.getEmail());
		emailService.sendSimpleEmail(user.getEmail(), "Otp register user", otp);
		userRepository.save(user);

		RegisterResponse reg = MapperUtils.mapToEntity(user, RegisterResponse.class);
		return reg;
	}

	@Override
	public boolean validateOtp(String otp, String otpCache) {

		if (otpCache != null && !otpCache.equals(otp))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Otp is not correct!");

		return true;
	}

	@Override
	public RegisterResponse validateRegister(ValidateOtpDTO validateDto) {
		String otpCache = otpService.getOtpByEmail(validateDto.getEmail());
		String token = null;
		this.validateOtp(validateDto.getOtp(), otpCache);
		// update token
		User user = userRepository.findByEmail(validateDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "user", validateDto.getEmail()));
		token = jwtTokenProvider.generateToken(user.getName());

		revokeAllUserTokens(user);
		saveUserToken(user, token);

		// update status user
		user.setStatus(1);
		userRepository.save(user);
		RegisterResponse reg = MapperUtils.mapToDTO(user, RegisterResponse.class);
		reg.setToken(token);
		otpService.clearCache("otpCache");
		return reg;
	}

	@Override
	public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
		return "";
	}

	private void saveUserToken(User user, String jwtToken) {
		Token token = new Token();
		token.setUser(user);
		token.setName(jwtToken);
		token.setTokenType(TokenType.BEARER);
		token.setExpired(false);
		token.setRevoked(false);
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

	@Override
	public UserDTO updateUser(int id, UserUpdateDTO userUpdateDto) {
		User userCurrent = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user", id));
		UserDTO userDto = MapperUtils.mapToDTO(userUpdateDto, UserDTO.class);

		userMapper.updateUserFromDto(userDto, userCurrent);
		userCurrent.setSlug(slug.slugify(userCurrent.getName()));
		userCurrent.setStatus(1);
		if (userDto.getPassword() != null)
			userCurrent.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userRepository.save(userCurrent);
		userCurrent.setPassword(null);

		return MapperUtils.mapToDTO(userCurrent, UserDTO.class);
	}

	@Override
	public void forgotPassword(String email) throws MessagingException {
		if (email.contains("@")) {

			String otp = otpService.generateAndStoreOtp(email);
			emailService.sendSimpleEmail(email, "resetPassword", otp);
		}
	}

	@Override
	public void handleValidateResetPassword(ValidateOtpDTO validateOtpDto) {
		String otpEmail = otpService.getOtpByEmail(validateOtpDto.getEmail());
		this.validateOtp(otpEmail, validateOtpDto.getOtp());
		otpService.generateAndStoreAnotherData(otpEmail);
		otpService.clearCache("otpCache");

	}
}
