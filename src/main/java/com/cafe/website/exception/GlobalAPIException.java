package com.cafe.website.exception;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.cafe.website.constant.StatusLog;
import com.cafe.website.entity.User;
import com.cafe.website.payload.ErrorDetails;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.security.JwtTokenProvider;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.LogService;
import com.cafe.website.util.MethodUtil;

import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalAPIException extends ResponseEntityExceptionHandler {
	@Autowired
	private LogService loggerService;
	@Autowired
	private AuthService authService;
	private static final Logger logger = LoggerFactory.getLogger(GlobalAPIException.class);

	private String getBody(HttpServletRequest request) {
		if (request instanceof ContentCachingRequestWrapper) {
			ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				int length = Math.min(buf.length, 1024);
				try {
					return new String(buf, 0, length, wrapper.getCharacterEncoding());
				} catch (UnsupportedEncodingException ex) {
					// Encoding issue - use default encoding
					return new String(buf, 0, length);
				}
			}
		}
		return "";
	}

	// handle specific exceptions
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest, HttpServletRequest request) {
		String requestBody = getBody(request);
		ErrorDetails errorDetails = this.handleLogAndCustomMessageOfErrorDetails(new Date(), exception.getMessage(),
				request, webRequest, StatusLog.FAILED.toString(), requestBody);
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage().substring(0, 255),
//				webRequest.getDescription(false));
//		User user = null;
//		loggerService.createLog(request, user, exception.getMessage().substring(0, 255), StatusLog.FAILED.toString(),
//				requestBody, "No Specific");

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// Cafe exceptions
	@ExceptionHandler(CafeAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(CafeAPIException exception, WebRequest webRequest,
			HttpServletRequest request) {

		String requestBody = getBody(request);
		ErrorDetails errorDetails = this.handleLogAndCustomMessageOfErrorDetails(new Date(), exception.getMessage(),
				request, webRequest, StatusLog.FAILED.toString(), requestBody);
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage().substring(0, 255),
//				webRequest.getDescription(false));
//		User user = authService.getUserFromHeader(request);
//		String requestBody = getBody(request);
//		loggerService.createLog(request, user, exception.getMessage().substring(0, 255), StatusLog.FAILED.toString(),
//				requestBody, "No Specific");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// global exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest,
			HttpServletRequest request) {
		String requestBody = getBody(request);
		ErrorDetails errorDetails = this.handleLogAndCustomMessageOfErrorDetails(new Date(), exception.getMessage(),
				request, webRequest, StatusLog.FAILED.toString(), requestBody);
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage().substring(0, 255),
//				webRequest.getDescription(false));
//		User user = authService.getUserFromHeader(request);
//		String requestBody = getBody(request);
//		loggerService.createLog(request, user, exception.getMessage().substring(0, 255), StatusLog.FAILED.toString(),
//				requestBody, "No Specific");
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	UsernameNotFoundException 
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(UsernameNotFoundException exception,
			WebRequest webRequest, HttpServletRequest request) {
		String requestBody = getBody(request);
		ErrorDetails errorDetails = this.handleLogAndCustomMessageOfErrorDetails(new Date(), exception.getMessage(),
				request, webRequest, StatusLog.FAILED.toString(), requestBody);
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage().substring(0, 255),
//				webRequest.getDescription(false));
//		User user = authService.getUserFromHeader(request);
//		String requestBody = getBody(request);
//		loggerService.createLog(request, user, exception.getMessage().substring(0, 255), StatusLog.FAILED.toString(),
//				requestBody, "No Specific");
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

//	BadCredentialsException 
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(BadCredentialsException exception,
			WebRequest webRequest, HttpServletRequest request) {
		String requestBody = getBody(request);
		ErrorDetails errorDetails = this.handleLogAndCustomMessageOfErrorDetails(new Date(), exception.getMessage(),
				request, webRequest, StatusLog.FAILED.toString(), requestBody);
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), "User not found", webRequest.getDescription(false));
//		User user = authService.getUserFromHeader(request);
//		String requestBody = getBody(request);
//		loggerService.createLog(request, user, exception.getMessage().substring(0, 255), StatusLog.FAILED.toString(),
//				requestBody, "No Specific");
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// argument not valid exceptions
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		StringBuilder messages = new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = MethodUtil.handleSubstringMessage(error.getDefaultMessage());
			errors.put(fieldName, message);
			messages.append(message).append("; ");

		});
		if (request instanceof ServletWebRequest) {
			ServletWebRequest servletWebRequest = (ServletWebRequest) request;
			HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
			User user = authService.getUserFromHeader(httpServletRequest);
			String requestBody = getBody(httpServletRequest);
			loggerService.createLog(httpServletRequest, user,
					messages == null ? MethodUtil.handleSubstringMessage(ex.getMessage().substring(0, 255))
							: MethodUtil.handleSubstringMessage(messages.toString()),
					StatusLog.FAILED.toString(), requestBody, "No Specific");
		}
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	// access denied exceptions
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
			WebRequest webRequest, HttpServletRequest request) {
		String requestBody = getBody(request);
		ErrorDetails errorDetails = this.handleLogAndCustomMessageOfErrorDetails(new Date(), exception.getMessage(),
				request, webRequest, StatusLog.FAILED.toString(), requestBody);
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage().substring(0, 255),
//				webRequest.getDescription(false));
//		User user = authService.getUserFromHeader(request);
//		String requestBody = getBody(request);
//		loggerService.createLog(request, user, exception.getMessage().substring(0, 255), StatusLog.FAILED.toString(),
//				requestBody, "No Specific");
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	// insufficient exceptions
	@ExceptionHandler(InsufficientAuthenticationException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(InsufficientAuthenticationException exception,
			WebRequest webRequest, HttpServletRequest request) {
		String requestBody = getBody(request);
		ErrorDetails errorDetails = this.handleLogAndCustomMessageOfErrorDetails(new Date(), exception.getMessage(),
				request, webRequest, StatusLog.FAILED.toString(), requestBody);
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage().substring(0, 255),
//				webRequest.getDescription(false));
//		User user = authService.getUserFromHeader(request);
//		String requestBody = getBody(request);
//		loggerService.createLog(request, user, exception.getMessage().substring(0, 255), StatusLog.FAILED.toString(),
//				requestBody, "No Specific");
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	// signature exceptions
	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(SignatureException exception, WebRequest webRequest,
			HttpServletRequest request) {
		String requestBody = getBody(request);
		ErrorDetails errorDetails = this.handleLogAndCustomMessageOfErrorDetails(new Date(), exception.getMessage(),
				request, webRequest, StatusLog.FAILED.toString(), requestBody);
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage().substring(0, 255),
//				webRequest.getDescription(false));
//		User user = authService.getUserFromHeader(request);
//		String requestBody = getBody(request);
//		loggerService.createLog(request, user, exception.getMessage().substring(0, 255), StatusLog.FAILED.toString(),
//				requestBody, "No Specific");
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	private ErrorDetails handleLogAndCustomMessageOfErrorDetails(Date date, String message, HttpServletRequest request,
			WebRequest webRequest, String status, String requestBody) {

		User user = authService.getUserFromHeader(request);
		ErrorDetails errorDetails = new ErrorDetails(new Date(), MethodUtil.handleSubstringMessage(message),
				webRequest.getDescription(false));
		loggerService.createLog(request, user, MethodUtil.handleSubstringMessage(message), status, requestBody,
				"No Specific");

		return errorDetails;
	}

}
