package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.cafe.website.entity.User;
import com.cafe.website.payload.ChangePasswordDTO;
import com.cafe.website.payload.LoginDTO;
import com.cafe.website.payload.LoginResponseDTO;
import com.cafe.website.payload.RegisterDTO;
import com.cafe.website.payload.RegisterResponse;
import com.cafe.website.payload.ResetPasswordDTO;
import com.cafe.website.payload.UpdateAvatarDTO;
import com.cafe.website.payload.UserDTO;
import com.cafe.website.payload.UserUpdateDTO;
import com.cafe.website.payload.ValidateOtpDTO;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	List<UserDTO> getListUser(Integer status, int limit, int page, String name, String email, String createdAt,
			String updatedAt, String sortBy);

	LoginResponseDTO login(LoginDTO loginDto, HttpServletRequest request);

	String refreshToken(HttpServletRequest request, HttpServletResponse response);

	RegisterResponse createUser(RegisterDTO registerDto, HttpServletRequest request);

	void forgotPassword(String email, HttpServletRequest request) throws MessagingException;

	UserDTO updateUser(String slug, UserUpdateDTO userUpdateDto, HttpServletRequest request);

	RegisterResponse validateRegister(ValidateOtpDTO validateDto, HttpServletRequest request);

	void handleValidateResetPassword(ValidateOtpDTO validateOtpDto, HttpServletRequest request);

	Boolean validateOtp(String otp, String key);

	UserDTO getProfile(String slug);

	UserDTO getUserById(int id);

	void updateProfileImage(String slug, UpdateAvatarDTO profileDto, HttpServletRequest request);

	void handleResePassword(ResetPasswordDTO reset, HttpServletRequest request);

	void deleteUserById(Integer id) throws IOException;

	void deleteUserBySlug(String slug) throws IOException;

	UserDTO getUserBySlug(String slug);

	String setIsWaitingDeleteUser(Integer user, HttpServletRequest request);

	User getUserFromHeader(HttpServletRequest request);

	void changePassword(ChangePasswordDTO reset, HttpServletRequest request);
}
