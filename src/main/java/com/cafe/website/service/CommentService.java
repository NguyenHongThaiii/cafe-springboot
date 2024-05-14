package com.cafe.website.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.cafe.website.payload.CommentCreateDTO;
import com.cafe.website.payload.CommentDTO;
import com.cafe.website.payload.CommentUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface CommentService {
	List<CommentDTO> getListComments(int limit, int page, Integer status, String name, Long userId, Long reviewId,
			String createdAt, String updatedAt, String sortBy);

	List<CommentDTO> getListCommentsByReviewId(int limit, int page, Long reviewId, String sortBy);

	CommentDTO getCommentById(Long id);

	CommentDTO createComment(CommentCreateDTO commentCreateDto,
			HttpServletRequest request);

	CommentDTO updateComment(Long id, CommentUpdateDTO commentUpdateDto,
			HttpServletRequest request);

	void deleteComment(Long id,
			HttpServletRequest request);

}
