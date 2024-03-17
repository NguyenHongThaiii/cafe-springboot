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

import com.cafe.website.payload.PurposeCreateDTO;
import com.cafe.website.payload.PurposeDTO;
import com.cafe.website.payload.PurposeUpdateDTO;
import com.cafe.website.service.PurposeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/purposes")
public class PurposeController {
	private PurposeService purposeService;

	public PurposeController(PurposeService purposeService) {
		this.purposeService = purposeService;
	}

	@GetMapping("")
	public ResponseEntity<List<PurposeDTO>> getListPurposes(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) String name,
			@RequestParam(defaultValue = "1") String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) String slug,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<PurposeDTO> listPurposesDto = purposeService.getListPurposes(limit, page, name, slug, createdAt, updatedAt,
				sortBy);
		return new ResponseEntity<>(listPurposesDto, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<PurposeDTO> getPurposeById(@PathVariable(name = "id") Long id) {
		PurposeDTO PurposeDto = purposeService.getPurposeById(id);
		return new ResponseEntity<>(PurposeDto, HttpStatus.OK);
	}

	@GetMapping("/{slug}")
	public ResponseEntity<PurposeDTO> getPurposeBySlug(@PathVariable(name = "slug") String slug) {
		PurposeDTO PurposeDto = purposeService.getPurposeBySlug(slug);
		return new ResponseEntity<>(PurposeDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PostMapping("")
	public ResponseEntity<PurposeDTO> createPurpose(@Valid @ModelAttribute PurposeCreateDTO purposeCreateDto,
			HttpServletRequest request) throws IOException {

		PurposeDTO newPurposeDto = purposeService.createPurpose(purposeCreateDto, request);
		return new ResponseEntity<>(newPurposeDto, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PatchMapping("/id/{id}")
	public ResponseEntity<PurposeDTO> updatePurpose(@Valid @ModelAttribute PurposeUpdateDTO purposeDto,
			@PathVariable(name = "id") Long id, HttpServletRequest request) throws IOException {
		PurposeDTO newPurposeDto = purposeService.updatePurpose(id, purposeDto, request);
		return new ResponseEntity<>(newPurposeDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> deletePurpose(@PathVariable(name = "id") Long id, HttpServletRequest request)
			throws IOException {
		purposeService.deletePurpose(id, request);
		return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
	}
}
