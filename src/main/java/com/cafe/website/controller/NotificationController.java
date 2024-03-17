package com.cafe.website.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.CommentListDTO;
import com.cafe.website.payload.NotificationCreateDTO;
import com.cafe.website.payload.NotificationDTO;
import com.cafe.website.payload.NotificationDeleteDTO;
import com.cafe.website.payload.NotificationUpdateDTO;
import com.cafe.website.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/notifications")
public class NotificationController {

	private NotificationService notificationService;
	private CommentSocketController commentSocketController;

	public NotificationController(NotificationService notificationService,
			CommentSocketController commentSocketController) {
		super();
		this.notificationService = notificationService;
		this.commentSocketController = commentSocketController;
	}

	@PostMapping("")
	public ResponseEntity<NotificationDTO> createNotification(
			@Valid @RequestBody NotificationCreateDTO notificationCreateDTO, HttpServletRequest request) {
		NotificationDTO notifi = notificationService.createNotification(notificationCreateDTO, request);
		return new ResponseEntity<NotificationDTO>(notifi, HttpStatus.CREATED);

	}

	@PatchMapping("")
	public ResponseEntity<NotificationDTO> updateNotification(
			@Valid @RequestBody NotificationUpdateDTO notificationUpdateDTO, HttpServletRequest request) {
		NotificationDTO notifi = notificationService.updateNotification(notificationUpdateDTO, request);
		return new ResponseEntity<NotificationDTO>(notifi, HttpStatus.OK);

	}

	@DeleteMapping("")
	public ResponseEntity<String> deleteNotification(@Valid @RequestBody NotificationDeleteDTO notificationDeleteDTO,
			HttpServletRequest request) {
		notificationService.deleteNotification(notificationDeleteDTO, request);
		return new ResponseEntity<String>("Delete successfully", HttpStatus.OK);

	}

	@GetMapping("")
	public ResponseEntity<List<NotificationDTO>> getListNotifications(
			@RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false) String url, @RequestParam(required = false) String message,
			@RequestParam(required = false) String original, @RequestParam(required = false) Integer state,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) Long userId,
			@RequestParam(required = false) Long senderId, @RequestParam(required = false) String createdAt,
			@RequestParam(required = false) String updatedAt, @RequestParam(required = false) String sortBy) {
		List<NotificationDTO> listNotifis = notificationService.getListNotifications(limit, page, url, message,
				original, state, status, userId, senderId, createdAt, updatedAt, sortBy);
		return ResponseEntity.ok(listNotifis);

	}
}
