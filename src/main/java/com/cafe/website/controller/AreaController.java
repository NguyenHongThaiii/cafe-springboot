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

import com.cafe.website.payload.AreaCreateDTO;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.AreaUpdateDTO;
import com.cafe.website.service.AreaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/areas")
public class AreaController {
	private AreaService areaService;

	public AreaController(AreaService areaService) {
		this.areaService = areaService;
	}

	@GetMapping("")
	public ResponseEntity<List<AreaDTO>> getListAreas(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) String name, @RequestParam(required = false) String slug,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<AreaDTO> listAreasDto = areaService.getListAreas(limit, page, status, name, slug, createdAt, updatedAt,
				sortBy);
		return new ResponseEntity<>(listAreasDto, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<AreaDTO> getAreaById(@PathVariable(name = "id") int id) {
		AreaDTO areaDto = areaService.getAreaById(id);
		return new ResponseEntity<>(areaDto, HttpStatus.OK);
	}

	@GetMapping("/{slug}")
	public ResponseEntity<AreaDTO> getAreaBySlug(@PathVariable(name = "slug") String slug) {
		AreaDTO areaDto = areaService.getAreaBySlug(slug);
		return new ResponseEntity<>(areaDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PostMapping("")
	public ResponseEntity<AreaDTO> createArea(@Valid @ModelAttribute AreaCreateDTO areaCreateDto,
			HttpServletRequest request) throws IOException {

		AreaDTO newAreaDto = areaService.createArea(areaCreateDto, request);
		return new ResponseEntity<>(newAreaDto, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PatchMapping("/id/{id}")
	public ResponseEntity<AreaDTO> updateArea(@PathVariable(name = "id") Integer id,
			@Valid @ModelAttribute AreaUpdateDTO areaDto, HttpServletRequest request) throws IOException {
		AreaDTO newAreaDto = areaService.updateArea(id, areaDto, request);
		return new ResponseEntity<>(newAreaDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> deleteArea(@PathVariable(name = "id") int id, HttpServletRequest request)
			throws IOException {
		areaService.deleteArea(id, request);
		return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
	}
}
