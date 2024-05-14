package com.cafe.website.service;

import java.util.List;

import com.cafe.website.payload.NotificationCreateDTO;
import com.cafe.website.payload.NotificationDTO;
import com.cafe.website.payload.NotificationDeleteDTO;
import com.cafe.website.payload.NotificationUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface NotificationService {
	NotificationDTO createNotification(NotificationCreateDTO notificationCreateDto, HttpServletRequest request);

	NotificationDTO updateNotification(NotificationUpdateDTO notificationUpdateDto, HttpServletRequest request);

	void deleteNotification(NotificationDeleteDTO notificationDeleteDTO, HttpServletRequest request);

	List<NotificationDTO> getListNotifications(Integer limit, Integer page, String url, String message, String original,
			Integer state, Integer status, Long userId, Long senderId, String createdAt, String updatedAt,
			String sortBy);
}
