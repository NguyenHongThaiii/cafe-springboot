package com.cafe.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.CommentCreateDTO;
import com.cafe.website.payload.CommentDTO;
import com.cafe.website.payload.CommentDeleteDTO;
import com.cafe.website.payload.AttributeIdDTO;
import com.cafe.website.payload.CommentListDTO;
import com.cafe.website.payload.CommentUpdateDTO;
import com.cafe.website.service.CommentService;
import com.cafe.website.serviceImp.ProductServiceImp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comments")

public class CommentSocketController {
	private CommentService commentService;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public CommentSocketController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	@GetMapping("")
	public ResponseEntity<List<CommentDTO>> getListComment(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) Long userId, @RequestParam(required = false) Long reviewId,
			@RequestParam(required = false) String name, @RequestParam(required = false) String createdAt,
			@RequestParam(required = false) String updatedAt,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<CommentDTO> list = commentService.getListComments(limit, page, status, name, userId, reviewId, createdAt,
				updatedAt, sortBy);
		return new ResponseEntity<List<CommentDTO>>(list, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable(name = "id") Long id) {
		CommentDTO comment = commentService.getCommentById(id);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PostMapping("")
	public CommentDTO createComment(@Valid @RequestBody CommentCreateDTO commentCreateDto, HttpServletRequest request) {
		CommentDTO comment = commentService.createComment(commentCreateDto, request);
		return comment;
	}

//	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("")
	public CommentDTO createCommentAdmin(@Valid @RequestBody CommentCreateDTO commentCreateDto,
			HttpServletRequest request) {
		CommentDTO comment = commentService.createComment(commentCreateDto, request);
		return comment;
	}

//	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")

	@PatchMapping("/updateComment")
	public CommentDTO updateComment(@Valid @RequestBody CommentUpdateDTO commentUpdateDTO, HttpServletRequest request) {
		CommentDTO comment = commentService.updateComment(commentUpdateDTO.getId(), commentUpdateDTO, request);
		return comment;
	}

//	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("/admin/updateComment")
	public CommentDTO updateCommentAdmin(@Valid @RequestBody CommentUpdateDTO commentUpdateDTO,
			HttpServletRequest request) {
		CommentDTO comment = commentService.updateComment(commentUpdateDTO.getId(), commentUpdateDTO, request);
		return comment;
	}

//	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@DeleteMapping("")
	public String delteCommentById(@Valid @RequestBody CommentDeleteDTO deleteDTO, HttpServletRequest request) {
		commentService.deleteComment(deleteDTO.getId(), request);
		return "Delete successfuly";
	}

//	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")

	@DeleteMapping("/admin/deleteComment")
	public String delteCommentByIdAdmin(@Valid @RequestBody AttributeIdDTO deleteDTO, HttpServletRequest request) {
		commentService.deleteComment(deleteDTO.getId(), request);
		return "Delete successfuly";
	}

}
