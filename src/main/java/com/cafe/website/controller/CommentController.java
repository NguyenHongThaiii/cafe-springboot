package com.cafe.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe.website.payload.CommentCreateDTO;
import com.cafe.website.payload.CommentDTO;
import com.cafe.website.payload.CommentUpdateDTO;
import com.cafe.website.service.CommentService;
import com.cafe.website.serviceImp.ProductServiceImp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class CommentController {
	private CommentService commentService;
	private final SimpMessagingTemplate messagingTemplate;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public CommentController(CommentService commentService, SimpMessagingTemplate messagingTemplate) {
		super();
		this.commentService = commentService;
		this.messagingTemplate = messagingTemplate;
	}

	@GetMapping("/api/v1/comments")
	public ResponseEntity<List<CommentDTO>> getListComment(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) String name,
			@RequestParam(required = false) Integer reviewId, @RequestParam(required = false) Integer userId,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,

			@RequestParam(required = false, defaultValue = "null") String sortBy) {
		List<CommentDTO> list = commentService.getListComments(limit, page, name, userId, reviewId, createdAt,
				updatedAt, sortBy);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/api/v1/comments/id/{id}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable(name = "id") Integer id) {
		CommentDTO comment = commentService.getCommentById(id);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.OK);
	}

	@MessageMapping("/chat")
	public void createComment(@Valid @Payload CommentCreateDTO commentCreateDto,
			SimpMessageHeaderAccessor headerAccessor) {
		CommentDTO comment = commentService.createComment(commentCreateDto, headerAccessor);
		messagingTemplate.convertAndSend("/topic/message", comment);

	}

	@PatchMapping("/api/v1/comments/id/{id}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable(name = "id") Integer id,
			@Valid @RequestBody CommentUpdateDTO CommentUpdateDTO, HttpServletRequest request) {
		CommentDTO comment = commentService.updateComment(id, CommentUpdateDTO, request);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.OK);
	}

	@DeleteMapping("/api/v1/comments/id/{id}")
	public ResponseEntity<String> delteCommentById(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
		commentService.deleteComment(id, request);
		return new ResponseEntity<String>("Delete successfuly", HttpStatus.OK);
	}
}
