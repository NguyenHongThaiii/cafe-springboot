package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.RoleType;
import com.cafe.website.constant.SortField;
import com.cafe.website.constant.StatusLog;
import com.cafe.website.constant.TokenType;
import com.cafe.website.entity.Image;
import com.cafe.website.entity.Role;
import com.cafe.website.entity.Token;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ChangePasswordDTO;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.LoginDTO;
import com.cafe.website.payload.LoginResponseDTO;
import com.cafe.website.payload.RegisterDTO;
import com.cafe.website.payload.RegisterResponse;
import com.cafe.website.payload.ResetPasswordDTO;
import com.cafe.website.payload.UpdateAvatarDTO;
import com.cafe.website.payload.UserDTO;
import com.cafe.website.payload.UserUpdateDTO;
import com.cafe.website.payload.ValidateOtpDTO;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.LogRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.RoleRepository;
import com.cafe.website.repository.TokenRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.security.JwtTokenProvider;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.EmailService;
import com.cafe.website.service.LogService;
import com.cafe.website.service.OTPService;
import com.cafe.website.util.JsonConverter;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.MethodUtil;
import com.cafe.website.util.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class AuthServiceImp implements AuthService {
	@PersistenceContext
	private EntityManager entityManager;
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private ProductRepository productRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	private TokenRepository tokenRepository;
	private ImageRepository imageRepository;
	private EmailService emailService;
	private OTPService otpService;
	private UserMapper userMapper;
	private CloudinaryService cloudinaryService;
	private ScheduledExecutorService scheduler;
	private LogService logService;
	private LogRepository logRepository;

	private ObjectMapper objectMapper;
	@Value("${app.path-user}")
	private String path_user;
	private Slugify slugify = Slugify.builder().build();
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
	@Value("${app.timeout}")
	private String timeout;

	public AuthServiceImp(EntityManager entityManager, AuthenticationManager authenticationManager,
			UserRepository userRepository, ProductRepository productRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, TokenRepository tokenRepository,
			ImageRepository imageRepository, EmailService emailService, OTPService otpService, UserMapper userMapper,
			CloudinaryService cloudinaryService, ScheduledExecutorService scheduler, LogService logService,
			LogRepository logRepository, ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.tokenRepository = tokenRepository;
		this.imageRepository = imageRepository;
		this.emailService = emailService;
		this.otpService = otpService;
		this.userMapper = userMapper;
		this.cloudinaryService = cloudinaryService;
		this.scheduler = scheduler;
		this.logService = logService;
		this.logRepository = logRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public LoginResponseDTO login(LoginDTO loginDto, HttpServletRequest request) {
		User user = userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", loginDto.getEmail()));
		if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()) || user.getStatus() != 1)
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Email or password is not correct.");
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		LoginResponseDTO lg = MapperUtils.mapToDTO(user, LoginResponseDTO.class);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
		revokeAllUserTokens(user);
		saveUserToken(user, token);
		if (user.getAvatar() != null) {
			lg.setImage(ImageDTO.generateImageDTO(user.getAvatar()));
		}
		try {
			loginDto.setPassword("");
			logService.createLog(request, user, "Login SUCCESSFULY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(loginDto), "Login");
			lg.setToken(token);

			return lg;
		} catch (IOException e) {
			logService.createLog(request, user, MethodUtil.handleSubstringMessage(e.getMessage()),
					StatusLog.FAILED.toString(), "Login");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}

	}

	@Override
	public LoginResponseDTO loginAdmin(LoginDTO loginDto, HttpServletRequest request) {
		User user = userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", loginDto.getEmail()));
		boolean isAdmin = false;
		if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()) || user.getStatus() != 1)
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Email or password is not correct.");

		for (Role role : user.getRoles()) {
			if (role.getName().equals(RoleType.ROLE_ADMIN.toString()))
				isAdmin = true;
		}
		if (!isAdmin)
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Email or password is not correct.");

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		LoginResponseDTO lg = MapperUtils.mapToDTO(user, LoginResponseDTO.class);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
		revokeAllUserTokens(user);
		saveUserToken(user, token);
		if (user.getAvatar() != null) {
			lg.setImage(ImageDTO.generateImageDTO(user.getAvatar()));
		}
		try {
			loginDto.setPassword("");
			logService.createLog(request, user, "Login SUCCESSFULY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(loginDto), "Login");
			lg.setToken(token);

			return lg;
		} catch (IOException e) {
			logService.createLog(request, user, MethodUtil.handleSubstringMessage(e.getMessage()),
					StatusLog.FAILED.toString(), "Login");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}

	}

	@Override
	public RegisterResponse createUser(RegisterDTO registerDto, HttpServletRequest request) {

		if (userRepository.existsByEmail(registerDto.getEmail()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		if (userRepository.existsByName(registerDto.getName()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!.");

		String passwordEncode = passwordEncoder.encode(registerDto.getPassword());
		User user = MapperUtils.mapToEntity(registerDto, User.class);
		user.setPassword(passwordEncode);
		user.setSlug(slugify.slugify(user.getName()));
		user.setStatus(0);

		List<Role> roles = new ArrayList<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		String newSlug = this.generateSlug(slugify.slugify(registerDto.getName()));
		user.setSlug(newSlug);
		user.setStatus(1);
		user.setIsWaitingDelete(false);

		userRepository.save(user);

		RegisterResponse reg = MapperUtils.mapToEntity(user, RegisterResponse.class);
		try {
			registerDto.setPassword("");
			logService.createLog(request, user, "Create User SUCCESSFULY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(registerDto), "Create User");
			return reg;
		} catch (IOException e) {
			logService.createLog(request, user, MethodUtil.handleSubstringMessage(e.getMessage()),
					StatusLog.FAILED.toString(), "Create User");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}

	}

	@Override
	public Boolean validateOtp(String otp, String otpCache) {

		if (otpCache == null || !otpCache.equals(otp))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Otp is not correct!");

		return true;
	}

	@Override
	public RegisterResponse validateRegister(ValidateOtpDTO validateDto, HttpServletRequest request) {
		String otpCache = otpService.getOtpByEmail(validateDto.getEmail().trim());
		this.validateOtp(validateDto.getOtp(), otpCache);

		User user = userRepository.findByEmail(validateDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", validateDto.getEmail()));
		String token = jwtTokenProvider.generateToken(user.getName());

		revokeAllUserTokens(user);
		saveUserToken(user, token);
		user.setStatus(1);
		userRepository.save(user);

		RegisterResponse reg = MapperUtils.mapToDTO(user, RegisterResponse.class);
		reg.setToken(token);
		otpService.clearCache("otpCache", validateDto.getEmail());
		try {
			logService.createLog(request, null, "Validate Register SUCCESSFULY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(validateDto), "Validate Register");
			return reg;
		} catch (IOException e) {
			logService.createLog(request, null, MethodUtil.handleSubstringMessage(e.getMessage()),
					StatusLog.FAILED.toString(), "Validate Register");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}

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
		List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	@Override
	public UserDTO updateUser(String slug, UserUpdateDTO userUpdateDto, HttpServletRequest request) {
		User userCurrent = userRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("User", "slug", slug));

		if (userRepository.existsBySlugAndIdNot(slugify.slugify(userUpdateDto.getSlug()), userCurrent.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (userRepository.existsByNameAndIdNot(userUpdateDto.getName(), userCurrent.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");
		Map<String, Object> logData = new HashMap<>();

		UserDTO userDto = MapperUtils.mapToDTO(userUpdateDto, UserDTO.class);

		userDto.setId(userCurrent.getId());
		if (userUpdateDto.getSlug() != null && userUpdateDto.getSlug().length() > 0)
			userDto.setSlug(slugify.slugify(userUpdateDto.getSlug()));
		userDto.setStatus(1);
		userMapper.updateUserFromDto(userDto, userCurrent);

		if (userUpdateDto.getPassword() != null)
			userCurrent.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
		List<Role> resultList = new ArrayList<>();
		if (userUpdateDto.getRoles() != null)

			userUpdateDto.getRoles().forEach(id -> {
				Role entity = roleRepository.findById(id).orElse(null);
				if (entity != null)
					resultList.add(entity);
			});

		userCurrent.setRoles(resultList);
		userRepository.save(userCurrent);
		logData.put("slug", slug);
		logData.put("userUpdateDto", userUpdateDto);
		UserDTO res = MapperUtils.mapToDTO(userCurrent, UserDTO.class);
		if (userCurrent.getAvatar() != null)
			res.setImage(ImageDTO.generateImageDTO(userCurrent.getAvatar()));

		try {
			userUpdateDto.setPassword("");
			logService.createLog(request, this.getUserFromHeader(request), "Update User SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(logData), "Update User");
			return res;

		} catch (IOException e) {
			logService.createLog(request, this.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(), "Update User");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public void forgotPassword(String email, HttpServletRequest request) throws MessagingException {
		if (email.contains("@")) {
			String otp = otpService.generateAndStoreOtp(email);
			emailService.sendSimpleEmail(email, "resetPassword", otp);
			String otpEmail = otpService.getOtpByEmail(email);

		}
		try {
			logService.createLog(request, null, "Send Email SUCCESSFULY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(email), "Forgot Password");
		} catch (IOException e) {
			logService.createLog(request, null, MethodUtil.handleSubstringMessage(e.getMessage()),
					StatusLog.FAILED.toString(), "Forgot Password");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public void handleValidateResetPassword(ValidateOtpDTO validateOtpDto, HttpServletRequest request) {
		String otpEmail = otpService.getOtpByEmail(validateOtpDto.getEmail());

		this.validateOtp(otpEmail, validateOtpDto.getOtp());
		otpService.clearCache("otpCache", validateOtpDto.getEmail().toLowerCase());
		otpService.generateAndStoreAnotherData(validateOtpDto.getEmail().toLowerCase());
		try {
			logService.createLog(request, null, "Handle Validate Reset Password SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(validateOtpDto),
					"Handle Validate Reset Password");
		} catch (IOException e) {
			logService.createLog(request, null, MethodUtil.handleSubstringMessage(e.getMessage()),
					StatusLog.FAILED.toString(), "Handle Validate Reset Password");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public void handleResePassword(ResetPasswordDTO reset, HttpServletRequest request) {
		User user = userRepository.findByEmail(reset.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", reset.getEmail()));
		if (!reset.getPassword().equals(reset.getRetypePassword()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "retype password is not match");
		String otpEmail = otpService.getOtpBySession(user.getEmail());
		if (otpEmail == null)
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Time is expired");

		otpService.clearCache("session", reset.getEmail());
		String passwordEncryt = passwordEncoder.encode(reset.getPassword());

		user.setPassword(passwordEncryt);
		user.setStatus(1);
		userRepository.save(user);
		try {
			logService.createLog(request, null, "Handle Reset Password SUCCESSFULY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(reset), "Handle Reset Password");
		} catch (IOException e) {
			logService.createLog(request, null, MethodUtil.handleSubstringMessage(e.getMessage()),
					StatusLog.FAILED.toString(), "Handle Reset Password");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}

	}

	@Override
	public UserDTO getProfile(String slug) {
		User user = userRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("User", "name", slug));

		UserDTO res = MapperUtils.mapToDTO(user, UserDTO.class);
		if (user.getAvatar() != null)
			res.setImage(ImageDTO.generateImageDTO(user.getAvatar()));
		return res;
	}

	@Override
	public void updateProfileImage(String slug, UpdateAvatarDTO profileDto, HttpServletRequest request) {
		User user = userRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("User", "slug", slug));
		Image image = imageRepository.findImageByUserId(user.getId()).orElse(null);
		Map<String, Object> logData = new HashMap<>();

		try {
			if (image != null) {
				cloudinaryService.removeImageFromCloudinary(image.getImage(), path_user);
			}
			String avatar = cloudinaryService.uploadImage(profileDto.getAvatar(), path_user, "image");
			if (image != null) {
				image.setImage(avatar);
				imageRepository.save(image);
			} else {
				Image imageTemp = new Image();
				imageTemp.setUser(user);
				imageTemp.setImage(avatar);
				imageRepository.save(imageTemp);

			}

			profileDto.setDataToLogging(profileDto.getAvatar().getOriginalFilename(),
					profileDto.getAvatar().getContentType(), profileDto.getAvatar().getSize(), () -> {
						profileDto.setAvatar(null);
					});
			logData.put("slug", slug);
			logData.put("profileDto", profileDto);
			logService.createLog(request, this.getUserFromHeader(request), "Update Image SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(logData),
					"Update Profile Image");
		} catch (IOException e) {
			logService.createLog(request, this.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Update Profile Image");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
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
	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
		if (user.getAvatar() != null)
			userDto.setImage(ImageDTO.generateImageDTO(user.getAvatar()));

		return userDto;
	}

	@Override
	public UserDTO getUserBySlug(String slug) {
		User user = userRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("User", "slug", slug));
		UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
		if (user.getAvatar() != null)
			userDto.setImage(ImageDTO.generateImageDTO(user.getAvatar()));
		return userDto;
	}

	@Override
	@Transactional
	public void deleteUserById(Long id) throws IOException {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		logRepository.deleteByUserId(id);
		Image image = imageRepository.findImageByUserId(id).orElse(null);
		if (image != null)
			cloudinaryService.removeImageFromCloudinary(image.getImage(), path_user);

		userRepository.deleteById(id);
	}

	@Override
	public void deleteUserBySlug(String slug) throws IOException {
		UserDTO userDto = this.getUserBySlug(slug);
		Image image = imageRepository.findImageByUserId(userDto.getId()).orElse(null);
		if (image != null)
			cloudinaryService.removeImageFromCloudinary(image.getImage(), path_user);

		userRepository.deleteUserBySlug(slug);

	}

	@Override
	public List<UserDTO> getListUser(Integer status, int limit, int page, String name, String email, String slug,
			String createdAt, String updatedAt, String sortBy) {

		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		List<String> sortByList = new ArrayList<String>();
		List<UserDTO> listUserDto;
		List<User> listUser;
		List<Sort.Order> sortOrders = new ArrayList<>();
		Pageable pageable = null;

		if (page != 0) {
			pageable = PageRequest.of(page - 1, limit);
			if (!StringUtils.isEmpty(sortBy))
				sortByList = Arrays.asList(sortBy.split(","));

			for (String sb : sortByList) {
				boolean isDescending = sb.endsWith("Desc");

				if (isDescending && !StringUtils.isEmpty(sortBy))
					sb = sb.substring(0, sb.length() - 4).trim();

				for (SortField sortField : validSortFields) {
					if (sortField.toString().equals(sb.trim())) {
						sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
						break;
					}
				}
			}

			if (!sortOrders.isEmpty())
				pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));
		}
		listUser = userRepository.findWithFilters(status, name, email, slug, createdAt, updatedAt, pageable,
				entityManager);

		listUserDto = listUser.stream().map(user -> {
			UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
			if (user.getAvatar() != null)
				userDto.setImage(ImageDTO.generateImageDTO(user.getAvatar()));
			return userDto;
		}).collect(Collectors.toList());

		return listUserDto;
	}

	@Override
	public String setIsWaitingDeleteUser(Long userId, HttpServletRequest request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		logRepository.deleteByUserId(userId);
		user.setRoles(null);
		userRepository.saveAndFlush(user);
		user.setIsWaitingDelete(true);
		userRepository.save(user);
		this.excuteDeleteUser(userId);
		try {
			logService.createLog(request, this.getUserFromHeader(request), "Set Waiting Delete User SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), JsonConverter.convertToJSON("userId", userId),
					"Set Waiting Delete User");
			return "Your account will be deleted after 24 hours";
		} catch (IOException e) {
			logService.createLog(request, this.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Set Waiting Delete User");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}

	}

	@Async
	public void excuteDeleteUser(Long userId) {
		scheduler.schedule(() -> {
			try {
				this.deleteUserById(userId);
			} catch (IOException e) {
			}
		}, Integer.parseInt(timeout), TimeUnit.SECONDS);

	}

	@Override
	public User getUserFromHeader(HttpServletRequest request) {
		User user = null;
		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith("Bearer ")) {
			String jwtToken = token.substring(7);
			user = userRepository.findByEmail(jwtTokenProvider.getUsername(jwtToken)).orElse(null);
		}
		return user;
	}

	@Override
	public void changePassword(ChangePasswordDTO reset, HttpServletRequest request) {
		User user = userRepository.findByEmail(reset.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", reset.getEmail()));
		try {
			if (!reset.getPassword().equals(reset.getRetypePassword()))
				throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Password and retype password not matches!");
			if (!passwordEncoder.matches(reset.getOldPassword(), user.getPassword()))
				throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Old password is not correct!");

			user.setPassword(passwordEncoder.encode(reset.getPassword()));

			userRepository.save(user);

			logService.createLog(request, user, "Create User SUCCESSFULY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(reset), "Create User");
		} catch (Exception e) {

			logService.createLog(request, this.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Set Waiting Delete User");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}

	}

	@Override
	public Integer getCountUser(Integer status, int limit, int page, String name, String email, String slug,
			String createdAt, String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		List<String> sortByList = new ArrayList<String>();
		List<User> listUser;
		List<Sort.Order> sortOrders = new ArrayList<>();
		Pageable pageable = null;

		if (page != 0) {
			pageable = PageRequest.of(page - 1, limit);
			if (!StringUtils.isEmpty(sortBy))
				sortByList = Arrays.asList(sortBy.split(","));

			for (String sb : sortByList) {
				boolean isDescending = sb.endsWith("Desc");

				if (isDescending && !StringUtils.isEmpty(sortBy))
					sb = sb.substring(0, sb.length() - 4).trim();

				for (SortField sortField : validSortFields) {
					if (sortField.toString().equals(sb.trim())) {
						sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
						break;
					}
				}
			}

			if (!sortOrders.isEmpty())
				pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));
		}
		listUser = userRepository.findWithFilters(status, name, email, slug, createdAt, updatedAt, pageable,
				entityManager);
		return listUser.size();
	}

}
