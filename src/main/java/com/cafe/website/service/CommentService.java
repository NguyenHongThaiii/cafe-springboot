package com.cafe.website.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.cafe.website.payload.CommentCreateDTO;
import com.cafe.website.payload.CommentDTO;
import com.cafe.website.payload.CommentUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface CommentService {
	List<CommentDTO> getListComments(int limit, int page, String name, Integer userId, Integer reviewId,
			String createdAt, String updatedAt, String sortBy);

	List<CommentDTO> getListCommentsByReviewId(int limit, int page, Integer reviewId, String sortBy);

	CommentDTO getCommentById(int id);

	CommentDTO createComment(CommentCreateDTO commentCreateDto, SimpMessageHeaderAccessor headerAccessor);

	CommentDTO updateComment(Integer id, CommentUpdateDTO commentUpdateDto, SimpMessageHeaderAccessor headerAccessor);

	void deleteComment(int id, SimpMessageHeaderAccessor headerAccessor);

}
