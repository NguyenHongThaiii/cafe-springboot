package com.cafe.website.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.cafe.website.payload.CommentUpdateDTO;
import com.cafe.website.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("")
	public ResponseEntity<List<CommentDTO>> getListComment(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) String name,
			@RequestParam(required = false) Integer reviewId, @RequestParam(required = false) Integer userId,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,

			@RequestParam(required = false, defaultValue = "null") String sortBy) {
		List<CommentDTO> list = commentService.getListComments(limit, page, name, userId, reviewId, createdAt,
				updatedAt, sortBy);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable(name = "id") Integer id) {
		CommentDTO comment = commentService.getCommentById(id);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentCreateDTO commentCreateDto,
			HttpServletRequest request) {
		CommentDTO comment = commentService.createComment(commentCreateDto, request);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.CREATED);
	}

	@PatchMapping("/id/{id}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable(name = "id") Integer id,
			@Valid @RequestBody CommentUpdateDTO CommentUpdateDTO, HttpServletRequest request) {
		CommentDTO comment = commentService.updateComment(id, CommentUpdateDTO, request);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.OK);
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> delteCommentById(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
		commentService.deleteComment(id, request);
		return new ResponseEntity<String>("Delete successfuly", HttpStatus.OK);
	}
}
