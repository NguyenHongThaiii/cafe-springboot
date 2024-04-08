package com.cafe.website.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.AttributeIdDTO;
import com.cafe.website.payload.FavoriteCommentCreateDTO;
import com.cafe.website.payload.FavoriteReviewCreateDTO;
import com.cafe.website.service.FavoriteService;
import com.cafe.website.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteSocketController {
	private FavoriteService favoriteService;

	public FavoriteSocketController(FavoriteService favoriteService) {
		super();
		this.favoriteService = favoriteService;
	}

	@PostMapping("/toggleFavoriteReview")
	public ResponseEntity<String> toggleFavoriteReview(@Valid @RequestBody FavoriteReviewCreateDTO favor,
			HttpServletRequest request) throws IOException {
		favoriteService.toggleFavoriteReview(favor, request);
		return ResponseEntity.ok("ok");
	}

	@PostMapping("/toggleFavoriteComment")
	public ResponseEntity<String> toggleFavoriteComment(@Valid @RequestBody FavoriteCommentCreateDTO favor,
			HttpServletRequest request) throws IOException {
		favoriteService.toggleFavoriteComment(favor, request);
		return ResponseEntity.ok("ok");
	}

	@GetMapping("/getAmountFavoriteReview/{id}")
	public ResponseEntity<Integer> getAmountFavoriteReview(@PathVariable(name = "id") Long id) throws IOException {
		Integer number = favoriteService.getAmountFavoriteReview(id);
		return new ResponseEntity<>(number, HttpStatus.OK);
	}

	@GetMapping("/getAmountFavoriteComment/{id}")
	public ResponseEntity<Integer> getAmountFavoriteComment(@PathVariable(name = "id") Long id) throws IOException {
		Integer number = favoriteService.getAmountFavoriteComment(id);
		return new ResponseEntity<>(number, HttpStatus.OK);
	}
}
