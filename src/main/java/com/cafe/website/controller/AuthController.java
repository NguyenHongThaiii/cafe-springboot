package com.cafe.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.ForgotPasswordDTO;
import com.cafe.website.payload.JWTAuthResponse;
import com.cafe.website.payload.LoginDTO;
import com.cafe.website.payload.ProfileDTO;
import com.cafe.website.payload.RegisterDTO;
import com.cafe.website.payload.RegisterResponse;
import com.cafe.website.payload.ResetPasswordDTO;
import com.cafe.website.payload.UpdateAvatarDTO;
import com.cafe.website.payload.UserDTO;
import com.cafe.website.payload.UserUpdateDTO;
import com.cafe.website.payload.ValidateOtpDTO;
import com.cafe.website.service.AuthService;
import com.cafe.website.serviceImp.ProductServiceImp;

import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private AuthService authService;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getListUsers(@RequestParam(defaultValue = "5") int limit,
			@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String name,
			@RequestParam(required = false) String email,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<UserDTO> listUserDto = authService.getListUser(limit, page, name, email, sortBy);
		return ResponseEntity.ok(listUserDto);
	}

	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDTO loginDto) {
		String token = authService.login(loginDto);

		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		return ResponseEntity.ok(jwtAuthResponse);
	}

	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody RegisterDTO regsiterDto) {
		logger.info(regsiterDto.toString());
		RegisterResponse reg = authService.createUser(regsiterDto);

		return new ResponseEntity<RegisterResponse>(reg, HttpStatus.CREATED);

	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("/users/profile/{slug}")
	public ResponseEntity<UserDTO> getProfile(@Valid @PathVariable(name = "slug") String slug) {
		UserDTO userDto = authService.getProfile(slug);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/users/id/{id}")
	public ResponseEntity<UserDTO> getUserById(@Valid @PathVariable(name = "id") int id) {
		UserDTO userDto = authService.getUserById(id);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/users/{slug}")
	public ResponseEntity<UserDTO> getUserBySlug(@Valid @PathVariable(name = "slug") String slug) {
		UserDTO userDto = authService.getUserBySlug(slug);
		return ResponseEntity.ok(userDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/users/id/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable(name = "id") int id) throws java.io.IOException {
		authService.deleteUserById(id);
		return ResponseEntity.ok("Delete Successfully");
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/users/{slug}")
	public ResponseEntity<String> deleteUserBySlug(@PathVariable(name = "slug") String slug)
			throws java.io.IOException {
		authService.deleteUserBySlug(slug);
		return ResponseEntity.ok("Delete Successfully");
	}

	@PostMapping(value = { "/validateRegister", })
	public ResponseEntity<RegisterResponse> validateRegister(@Valid @RequestBody ValidateOtpDTO validate) {
		RegisterResponse reg = authService.validateRegister(validate);
		return new ResponseEntity<>(reg, HttpStatus.CREATED);
	}

	@PostMapping(value = { "/validateReset", })
	public ResponseEntity<String> validateReset(@Valid @RequestBody ValidateOtpDTO validate) {
		authService.handleValidateResetPassword(validate);
		return ResponseEntity.ok("Ok");
	}

	@PostMapping(value = { "/forgotPassword", })
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDto)
			throws MessagingException {
		authService.forgotPassword(forgotPasswordDto.getEmail());
		return ResponseEntity.ok("Ok");
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("/update/{slug}")
	public ResponseEntity<UserDTO> updateUser(@Valid @ModelAttribute UserUpdateDTO userUpdateDto,
			@PathVariable(name = "slug") String slug) {
		UserDTO userDto = authService.updateUser(slug, userUpdateDto);
		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@Valid @RequestBody(required = true) ResetPasswordDTO reset) {
		authService.handleResePassword(reset);
		return ResponseEntity.ok("Ok");
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("/avatar/{slug}")
	public ResponseEntity<String> updateAvatar(@Valid @ModelAttribute UpdateAvatarDTO avatarDto,
			@PathVariable(name = "slug") String slug) {
		authService.updateProfileImage(slug, avatarDto);
		return ResponseEntity.ok("Ok");
	}

	@PostMapping("/refresh-token")
//	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	public ResponseEntity<JWTAuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		String token = authService.refreshToken(request, response);
		jwtAuthResponse.setAccessToken(token);
		return ResponseEntity.ok(jwtAuthResponse);
	}
}
