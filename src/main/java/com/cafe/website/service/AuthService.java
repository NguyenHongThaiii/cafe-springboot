package com.cafe.website.service;

import com.cafe.website.payload.LoginDTO;
import com.cafe.website.payload.RegisterDTO;
import com.cafe.website.payload.RegisterResponse;
import com.cafe.website.payload.ResetPasswordDTO;
import com.cafe.website.payload.UpdateAvatarDTO;
import com.cafe.website.payload.UserDTO;
import com.cafe.website.payload.UserUpdateDTO;
import com.cafe.website.payload.ValidateOtpDTO;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
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

	void updateProfileImage(UpdateAvatarDTO profileDto);

	void handleResePassword(ResetPasswordDTO reset);

}
