package com.cafe.website.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.entity.Area;
import com.cafe.website.service.AreaService;

@RestController
@RequestMapping("api/v1")
public class AreaController {
	private AreaService areaService;

	public AreaController(AreaService areaService) {
		this.areaService = areaService;
	}
	
	@RequestMapping("/areas")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Area>> getListAreas() {
		List<Area> listAreas = areaService.getListAreas();
		return new ResponseEntity<>(listAreas, HttpStatus.OK);
	}
}
