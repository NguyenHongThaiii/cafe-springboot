package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.cafe.website.payload.LoginDTO;
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
	List<UserDTO> getListUser(int limit, int page, String name, String email, String sortBy);

	String login(LoginDTO loginDto);

	String refreshToken(HttpServletRequest request, HttpServletResponse response);

	RegisterResponse createUser(RegisterDTO registerDto);

	void forgotPassword(String email) throws MessagingException;

	UserDTO updateUser(String slug, UserUpdateDTO userUpdateDto);

	RegisterResponse validateRegister(ValidateOtpDTO validateDto);

	void handleValidateResetPassword(ValidateOtpDTO validateOtpDto);

	Boolean validateOtp(String otp, String key);

	UserDTO getProfile(String slug);

	UserDTO getUserById(int id);

	void updateProfileImage(String slug, UpdateAvatarDTO profileDto);

	void handleResePassword(ResetPasswordDTO reset);

	void deleteUserById(Integer id) throws IOException;

	void deleteUserBySlug(String slug) throws IOException;

	UserDTO getUserBySlug(String slug);

	String setIsWaitingDeleteUser(Integer user);

}
