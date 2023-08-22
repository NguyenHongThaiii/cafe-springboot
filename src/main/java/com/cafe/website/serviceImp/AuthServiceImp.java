package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
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
import com.cafe.website.entity.Image;
import com.cafe.website.entity.Role;
import com.cafe.website.entity.Token;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.LoginDTO;
import com.cafe.website.payload.RegisterDTO;
import com.cafe.website.payload.RegisterResponse;
import com.cafe.website.payload.ResetPasswordDTO;
import com.cafe.website.payload.UpdateAvatarDTO;
import com.cafe.website.payload.UserDTO;
import com.cafe.website.payload.UserUpdateDTO;
import com.cafe.website.payload.ValidateOtpDTO;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.RoleRepository;
import com.cafe.website.repository.TokenRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.security.JwtTokenProvider;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.EmailService;
import com.cafe.website.service.OTPService;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.UserMapper;
import com.github.slugify.Slugify;

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
	private ImageRepository imageRepository;
	private EmailService emailService;
	private OTPService otpService;
	private UserMapper userMapper;
	private CloudinaryService cloudinaryService;
	final String path_user = "cafe-springboot/Users/";

	private Slugify slugify = Slugify.builder().build();

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public AuthServiceImp(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
			TokenRepository tokenRepository, EmailService emailService, OTPService otpService, UserMapper userMapper,
			CloudinaryService cloudinaryService, ImageRepository imageRepository) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.tokenRepository = tokenRepository;
		this.imageRepository = imageRepository;
		this.emailService = emailService;
		this.userMapper = userMapper;
		this.otpService = otpService;
		this.cloudinaryService = cloudinaryService;
	}

	@Override
	public String login(LoginDTO loginDto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);

		User user = userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", loginDto.getEmail()));
		revokeAllUserTokens(user);
		saveUserToken(user, token);

		return token;
	}

	@Override
	public RegisterResponse createUser(RegisterDTO registerDto) {
		String passwordEncode = passwordEncoder.encode(registerDto.getPassword());

		if (userRepository.existsByEmail(registerDto.getEmail()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		if (userRepository.existsByName(registerDto.getName()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!.");

		User user = MapperUtils.mapToEntity(registerDto, User.class);
		user.setPassword(passwordEncode);
		user.setSlug(slugify.slugify(user.getName()));

		List<Role> roles = new ArrayList<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		String newSlug = this.generateSlug(slugify.slugify(registerDto.getName()));
		user.setSlug(newSlug);
		String otp = otpService.generateAndStoreOtp(user.getEmail());

		emailService.sendSimpleEmail(user.getEmail(), "Otp register user", otp);
		userRepository.save(user);

		RegisterResponse reg = MapperUtils.mapToEntity(user, RegisterResponse.class);
		return reg;
	}

	@Override
	public Boolean validateOtp(String otp, String otpCache) {

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
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", validateDto.getEmail()));
		token = jwtTokenProvider.generateToken(user.getName());

		revokeAllUserTokens(user);
		saveUserToken(user, token);

		// update status user
		user.setStatus(1);
		userRepository.save(user);
		RegisterResponse reg = MapperUtils.mapToDTO(user, RegisterResponse.class);
		reg.setToken(token);
		otpService.clearCache("otpCache", validateDto.getEmail());
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
	public UserDTO updateUser(String slug, UserUpdateDTO userUpdateDto) {
		User userCurrent = userRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("User", "slug", slug));

		if (userRepository.existsBySlugAndIdNot(slugify.slugify(userUpdateDto.getSlug()), userCurrent.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (userRepository.existsByNameAndIdNot(userUpdateDto.getName(), userCurrent.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		UserDTO userDto = MapperUtils.mapToDTO(userUpdateDto, UserDTO.class);

		userDto.setId(userCurrent.getId());
		userDto.setSlug(slugify.slugify(userUpdateDto.getSlug()));
		userDto.setStatus(1);
		userMapper.updateUserFromDto(userDto, userCurrent);

		if (userUpdateDto.getPassword() != null)
			userCurrent.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));

		userRepository.save(userCurrent);

		userCurrent.setPassword(null);
		UserDTO res = MapperUtils.mapToDTO(userCurrent, UserDTO.class);
		Image image = imageRepository.findImageByUserId(res.getId()).orElse(null);
		res.setListImages(ImageDTO.generateImageDTO(image));
		return res;
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
		otpService.clearCache("otpCache", validateOtpDto.getEmail());
		otpService.generateAndStoreAnotherData(validateOtpDto.getEmail());
	}

	@Override
	public void handleResePassword(ResetPasswordDTO reset) {
		User user = userRepository.findByEmail(reset.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", reset.getEmail()));
		if (!reset.getPassword().equals(reset.getRetypePassword()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "retype password is not match");

		otpService.clearCache("session", reset.getEmail());
		String passwordEncryt = passwordEncoder.encode(reset.getPassword());
		user.setPassword(passwordEncryt);

		userRepository.save(user);

	}

	@Override
	public UserDTO getProfile(String slug) {
		User user = userRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("User", "name", slug));
		user.setPassword(null);
		user.setRoles(null);

		UserDTO res = MapperUtils.mapToDTO(user, UserDTO.class);

		Image image = imageRepository.findImageByUserId(res.getId()).orElse(null);
		res.setListImages(ImageDTO.generateImageDTO(image));

		return res;
	}

	@Override
	public void updateProfileImage(String slug, UpdateAvatarDTO profileDto) {
		User user = userRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("User", "slug", slug));
		Image image = imageRepository.findImageByUserId(user.getId()).orElse(null);

		try {
//			logger.info(user.getAvartar());
			if (image != null)
				cloudinaryService.removeImageFromCloudinary(image.getImage(), path_user);
			String avatar = cloudinaryService.uploadImage(profileDto.getAvatar(), path_user, "image");
			Image imageTemp = new Image();
			imageTemp.setUser(user);
			imageTemp.setImage(avatar);

			user.setAvatar(imageTemp);
			userRepository.save(user);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String generateSlug(String initialSlug) {
		String slug = initialSlug;
		while (userRepository.existsBySlug(slug)) {
			slug = initialSlug + RandomStringUtils.randomAlphanumeric(8);
		}

		return slug;
	}

	@Override
	public UserDTO getUserById(int id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);

		Image image = imageRepository.findImageByUserId(userDto.getId()).orElse(null);
		userDto.setListImages(ImageDTO.generateImageDTO(image));

		return userDto;
	}

	@Override
	public UserDTO getUserBySlug(String slug) {
		User user = userRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("User", "slug", slug));
		UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);

		Image image = imageRepository.findImageByUserId(userDto.getId()).orElse(null);
		userDto.setListImages(ImageDTO.generateImageDTO(image));
		return userDto;
	}

	@Override
	public void deleteUserById(Integer id) throws IOException {
		this.getUserById(id);
		// TODO Auto-generated method stub
		Image image = imageRepository.findImageByUserId(id).orElse(null);
		if (image != null)
			cloudinaryService.removeImageFromCloudinary(image.getImage(), path_user);

		userRepository.deleteById(id);
	}

	@Override
	public void deleteUserBySlug(String slug) throws IOException {
		// TODO Auto-generated method stub
		UserDTO userDto = this.getUserBySlug(slug);
		Image image = imageRepository.findImageByUserId(userDto.getId()).orElse(null);
		if (image != null)
			cloudinaryService.removeImageFromCloudinary(image.getImage(), path_user);

		userRepository.deleteUserBySlug(slug);

	}

}
