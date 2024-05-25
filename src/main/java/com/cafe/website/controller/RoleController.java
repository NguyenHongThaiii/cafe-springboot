package com.cafe.website.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.entity.Role;
import com.cafe.website.repository.RoleRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
	private RoleRepository repository;

	public RoleController(RoleRepository repository) {
		super();
		this.repository = repository;
	}

	@GetMapping("")
	public ResponseEntity<List<Role> > toggleFavoriteReview() throws IOException {
		List<Role> listRoles = repository.findAll();
		
		return new ResponseEntity<List<Role>>(listRoles, HttpStatus.OK);
	}
}
