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

import com.cafe.website.payload.KindCreateDTO;
import com.cafe.website.payload.KindDTO;
import com.cafe.website.payload.KindUpdateDTO;
import com.cafe.website.service.KindService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/kinds")
public class KindController {
	private KindService kindService;

	public KindController(KindService kindService) {
		this.kindService = kindService;
	}

	@GetMapping("")
	public ResponseEntity<List<KindDTO>> getListKinds(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) String name,
			@RequestParam(required = false) String slug, @RequestParam(required = false) String createdAt,
			@RequestParam(required = false) String updatedAt,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<KindDTO> listKindsDto = kindService.getListKinds(limit, page, name, slug, createdAt, updatedAt, sortBy);
		return new ResponseEntity<>(listKindsDto, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<KindDTO> getKindById(@PathVariable(name = "id") Long id) {
		KindDTO kindDto = kindService.getKindById(id);
		return new ResponseEntity<>(kindDto, HttpStatus.OK);
	}

	@GetMapping("/{slug}")
	public ResponseEntity<KindDTO> getKindBySlug(@PathVariable(name = "slug") String slug) {
		KindDTO kindDto = kindService.getKindBySlug(slug);
		return new ResponseEntity<>(kindDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PostMapping("")
	public ResponseEntity<KindDTO> createKind(@Valid @ModelAttribute KindCreateDTO kindCreateDto,
			HttpServletRequest request) throws IOException {

		KindDTO newKindDto = kindService.createKind(kindCreateDto, request);
		return new ResponseEntity<>(newKindDto, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PatchMapping("/id/{id}")
	public ResponseEntity<KindDTO> updateKind(@Valid @ModelAttribute KindUpdateDTO kindDto,
			@PathVariable(name = "id") Long id, HttpServletRequest request) throws IOException {
		KindDTO newKindDto = kindService.updateKind(id, kindDto, request);
		return new ResponseEntity<>(newKindDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> deleteKind(@PathVariable(name = "id") Long id, HttpServletRequest request)
			throws IOException {
		kindService.deleteKind(id, request);
		return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
	}
}
