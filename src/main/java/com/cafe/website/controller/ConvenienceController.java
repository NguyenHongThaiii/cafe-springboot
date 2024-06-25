package com.cafe.website.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.ConvenienceCreateDTO;
import com.cafe.website.payload.ConvenienceDTO;
import com.cafe.website.payload.ConvenienceUpdateDTO;
import com.cafe.website.service.ConvenienceService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/conveniences")
public class ConvenienceController {
	private ConvenienceService convenienceService;

	public ConvenienceController(ConvenienceService convenienceService) {
		this.convenienceService = convenienceService;
	}

	@GetMapping("")
	public ResponseEntity<List<ConvenienceDTO>> getListConveniences(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) String name,
			@RequestParam(required = false) String slug, @RequestParam(required = false) String createdAt,
			@RequestParam(required = false) String updatedAt,

			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<ConvenienceDTO> listConveniencesDto = convenienceService.getListConveniences(limit, page, name, slug,
				createdAt, updatedAt, sortBy);
		return new ResponseEntity<>(listConveniencesDto, HttpStatus.OK);
	}

	@GetMapping("/count")
	public ResponseEntity<Integer> getCountConveniences(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(required = false) String name,
			@RequestParam(required = false) String slug, @RequestParam(required = false) String createdAt,
			@RequestParam(required = false) String updatedAt,

			@RequestParam(required = false, defaultValue = "") String sortBy) {
		Integer listConveniencesDto = convenienceService.getCountConveniences(limit, page, name, slug, createdAt,
				updatedAt, sortBy);
		return new ResponseEntity<>(listConveniencesDto, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<ConvenienceDTO> getConvenienceById(@PathVariable(name = "id") Long id) {
		ConvenienceDTO convenienceDto = convenienceService.getConvenienceById(id);
		return new ResponseEntity<>(convenienceDto, HttpStatus.OK);
	}

	@GetMapping("/{slug}")
	public ResponseEntity<ConvenienceDTO> getConvenienceBySlug(@PathVariable(name = "slug") String slug) {
		ConvenienceDTO convenienceDto = convenienceService.getConvenienceBySlug(slug);
		return new ResponseEntity<>(convenienceDto, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<ConvenienceDTO> createConvenience(
			@Valid @ModelAttribute ConvenienceCreateDTO convenienceCreateDto, HttpServletRequest request)
			throws IOException {

		ConvenienceDTO convenienceDto = convenienceService.createConvenience(convenienceCreateDto, request);
		return new ResponseEntity<>(convenienceDto, HttpStatus.CREATED);
	}

	@PatchMapping("/id/{id}")
	public ResponseEntity<ConvenienceDTO> updateConvenience(
			@Valid @ModelAttribute ConvenienceUpdateDTO convenienceUpdateDto, @PathVariable(name = "id") Long id,
			HttpServletRequest request) throws IOException {
		ConvenienceDTO convenienceDto = convenienceService.updateConvenience(id, convenienceUpdateDto, request);
		return new ResponseEntity<>(convenienceDto, HttpStatus.OK);
	}
	@DeleteMapping("/id/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<String> deleteConvenience(@PathVariable(name = "id") Long id, HttpServletRequest request)
			throws IOException {
		convenienceService.deleteConvenience(id, request);
		return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
	}
}
