package com.cafe.website.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.cafe.website.payload.CommentCreateDTO;
import com.cafe.website.payload.CommentDTO;
import com.cafe.website.payload.CommentUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface CommentService {
	List<CommentDTO> getListComments(int limit, int page, String name, Long userId, Long reviewId,
			String createdAt, String updatedAt, String sortBy);

	List<CommentDTO> getListCommentsByReviewId(int limit, int page, Long reviewId, String sortBy);

	CommentDTO getCommentById(Long id);

	CommentDTO createComment(CommentCreateDTO commentCreateDto, SimpMessageHeaderAccessor headerAccessor);

	CommentDTO updateComment(Long id, CommentUpdateDTO commentUpdateDto, SimpMessageHeaderAccessor headerAccessor);

	void deleteComment(Long id, SimpMessageHeaderAccessor headerAccessor);

}
