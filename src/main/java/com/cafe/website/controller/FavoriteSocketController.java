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

@Controller
public class FavoriteSocketController {
	private FavoriteService favoriteService;
	private final SimpMessagingTemplate messagingTemplate;

	public FavoriteSocketController(FavoriteService favoriteService, SimpMessagingTemplate messagingTemplate) {
		super();
		this.messagingTemplate = messagingTemplate;
		this.favoriteService = favoriteService;
	}

	@MessageMapping("/toggleFavoriteReview")
	public ResponseEntity<String> toggleFavoriteReview(@Valid @Payload FavoriteReviewCreateDTO favor,
			HttpServletRequest request) throws IOException {
		favoriteService.toggleFavoriteReview(favor, request);
		this.getAmountFavoriteReview(new AttributeIdDTO(favor.getReviewId()));
		return ResponseEntity.ok("ok");
	}

	@MessageMapping("/toggleFavoriteComment")
	public ResponseEntity<String> toggleFavoriteComment(@Valid @Payload FavoriteCommentCreateDTO favor,
			HttpServletRequest request) throws IOException {
		favoriteService.toggleFavoriteComment(favor, request);
		this.getAmountFavoriteComment(new AttributeIdDTO(favor.getCommentId()));
		return ResponseEntity.ok("ok");
	}

	@MessageMapping("/getAmountFavoriteReview")
	public ResponseEntity<Integer> getAmountFavoriteReview(@Valid @Payload AttributeIdDTO idDto) throws IOException {
		Integer number = favoriteService.getAmountFavoriteReview(idDto.getId());
		messagingTemplate.convertAndSend("/api/v1/getAmountFavoriteReview", number);
		return new ResponseEntity<>(number, HttpStatus.OK);
	}

	@MessageMapping("/getAmountFavoriteComment")
	public ResponseEntity<Integer> getAmountFavoriteComment(@Valid @Payload AttributeIdDTO idDto) throws IOException {
		Integer number = favoriteService.getAmountFavoriteComment(idDto.getId());
		messagingTemplate.convertAndSend("/api/v1/getAmountFavoriteComment", number);

		return new ResponseEntity<>(number, HttpStatus.OK);
	}
}
