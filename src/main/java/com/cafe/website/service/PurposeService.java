package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.PurposeCreateDTO;
import com.cafe.website.payload.PurposeUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

import com.cafe.website.payload.PurposeDTO;

public interface PurposeService {
	PurposeDTO getPurposeById(int id);

	PurposeDTO getPurposeBySlug(String slug);

	PurposeDTO createPurpose(PurposeCreateDTO purposeCreateDto, HttpServletRequest request) throws IOException;

	PurposeDTO updatePurpose(int id, PurposeUpdateDTO purposeUpdateDto, HttpServletRequest request) throws IOException;

	void deletePurpose(int id, HttpServletRequest request) throws IOException;

	List<PurposeDTO> getListPurposes(int limit, int page, String name, String slug, String createdAt, String updatedAt,
			String sortBy);
}
