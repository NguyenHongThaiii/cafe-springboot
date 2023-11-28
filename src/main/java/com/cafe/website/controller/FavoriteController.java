package com.cafe.website.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.FavoriteCreateDTO;
import com.cafe.website.service.FavoriteService;
import com.cafe.website.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {
	private FavoriteService favoriteService;

	public FavoriteController(FavoriteService favoriteService) {
		super();
		this.favoriteService = favoriteService;
	}

	@PostMapping("/toggleFavorite")
	public ResponseEntity<String> toggleFavoriteReview(@Valid @RequestBody FavoriteCreateDTO favor,
			HttpServletRequest request) throws IOException {
		favoriteService.toggleFavoriteReview(favor, request);
		return ResponseEntity.ok("ok");
	}

	@GetMapping("/{id}/reviews")
	public ResponseEntity<Integer> getAmountFavorite(@PathVariable(name = "id") int id) throws IOException {
		Integer number = favoriteService.getAmountFavorite(id);
		return new ResponseEntity<>(number, HttpStatus.OK);
	}
}
