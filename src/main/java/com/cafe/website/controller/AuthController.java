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

import com.cafe.website.payload.ChangePasswordDTO;
import com.cafe.website.payload.ForgotPasswordDTO;
import com.cafe.website.payload.JWTAuthResponse;
import com.cafe.website.payload.LoginDTO;
import com.cafe.website.payload.LoginResponseDTO;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
	public ResponseEntity<List<UserDTO>> getListUsers(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) String name, @RequestParam(required = false) String email,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) String slug,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<UserDTO> listUserDto = authService.getListUser(status, limit, page, name, email,slug, createdAt, updatedAt,
				sortBy);
		return ResponseEntity.ok(listUserDto);
	}
	@GetMapping("/users/count")
	public ResponseEntity<Integer> getCountUsers(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) String name, @RequestParam(required = false) String email,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) String slug,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		Integer listUserDto = authService.getCountUser(status, limit, page, name, email,slug, createdAt, updatedAt,
				sortBy);
		return ResponseEntity.ok(listUserDto);
	}

	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDto, HttpServletRequest request) {
		LoginResponseDTO lg = authService.login(loginDto, request);

//		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
//		jwtAuthResponse.setAccessToken(token.getToken());
		return ResponseEntity.ok(lg);
	}
	@PostMapping(value = { "/admin/login", "/admin/signin" })
	public ResponseEntity<LoginResponseDTO> loginAdmin(@Valid @RequestBody LoginDTO loginDto, HttpServletRequest request) {
		LoginResponseDTO lg = authService.loginAdmin(loginDto, request);

//		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
//		jwtAuthResponse.setAccessToken(token.getToken());
		return ResponseEntity.ok(lg);
	}

	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody RegisterDTO regsiterDto,
			HttpServletRequest request) {
		RegisterResponse reg = authService.createUser(regsiterDto, request);

		return new ResponseEntity<RegisterResponse>(reg, HttpStatus.CREATED);

	}

//	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@GetMapping("/users/profile/{slug}")
	public ResponseEntity<UserDTO> getProfile(@Valid @PathVariable(name = "slug") String slug) {
		UserDTO userDto = authService.getProfile(slug);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/users/id/{id}")
	public ResponseEntity<UserDTO> getUserById(@Valid @PathVariable(name = "id") Long id) {
		UserDTO userDto = authService.getUserById(id);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/users/{slug}")
	public ResponseEntity<UserDTO> getUserBySlug(@Valid @PathVariable(name = "slug") String slug) {
		UserDTO userDto = authService.getUserBySlug(slug);
		return ResponseEntity.ok(userDto);
	}

	@DeleteMapping("/users/id/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable(name = "id") Long id) throws java.io.IOException  {
		authService.deleteUserById(id);
		return ResponseEntity.ok("Delete Successfully");
	}

	@DeleteMapping("/users/wait/{id}")
	public ResponseEntity<String> deleteUserByIdAndTime(@PathVariable(name = "id") Long id,HttpServletRequest request) throws java.io.IOException  {
		authService.setIsWaitingDeleteUser(id,request);
		return ResponseEntity.ok("Delete Successfully");
	}
	@DeleteMapping("/users/{slug}")
	public ResponseEntity<String> deleteUserBySlug(@PathVariable(name = "slug") String slug)
			throws java.io.IOException {
		authService.deleteUserBySlug(slug);
		return ResponseEntity.ok("Delete Successfully");
	}

	@PostMapping(value = { "/validateRegister", })
	public ResponseEntity<RegisterResponse> validateRegister(@Valid @RequestBody ValidateOtpDTO validate,
			HttpServletRequest request) {
		RegisterResponse reg = authService.validateRegister(validate, request);
		return new ResponseEntity<>(reg, HttpStatus.CREATED);
	}

	@PostMapping(value = { "/validateReset", })
	public ResponseEntity<String> validateReset(@Valid @RequestBody ValidateOtpDTO validate,
			HttpServletRequest request) {
		authService.handleValidateResetPassword(validate, request);
		return ResponseEntity.ok("Ok");
	}

	@PostMapping(value = { "/forgotPassword", })
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDto,
			HttpServletRequest request) throws MessagingException {
		authService.forgotPassword(forgotPasswordDto.getEmail(), request);
		return ResponseEntity.ok("Ok");
	}

	@PatchMapping("/update/{slug}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDto,
			@PathVariable(name = "slug") String slug, HttpServletRequest request) {
		UserDTO userDto = authService.updateUser(slug, userUpdateDto, request);
		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@Valid @RequestBody(required = true) ResetPasswordDTO reset,
			HttpServletRequest request) {
		authService.handleResePassword(reset, request);
		return ResponseEntity.ok("Ok");
	}

	@PostMapping("/avatar/{slug}")
	public ResponseEntity<String> updateAvatar(@Valid @ModelAttribute UpdateAvatarDTO avatarDto,
			@PathVariable(name = "slug") String slug, HttpServletRequest request) {
		authService.updateProfileImage(slug, avatarDto, request);
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

	@SecurityRequirement(name = "jwt")
	@Operation(summary = "Change password user")
	@ApiResponse(responseCode = "200", description = "Http status 200 OK")
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@Valid @RequestBody(required = true) ChangePasswordDTO reset,
			HttpServletRequest request) {
		authService.changePassword(reset, request);
		return ResponseEntity.ok("Ok");
	}
}
