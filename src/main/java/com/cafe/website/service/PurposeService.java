package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.PurposeCreateDTO;
import com.cafe.website.payload.PurposeUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

import com.cafe.website.payload.PurposeDTO;

public interface PurposeService {
	PurposeDTO getPurposeById(Long id);

	PurposeDTO getPurposeBySlug(String slug);

	PurposeDTO createPurpose(PurposeCreateDTO purposeCreateDto, HttpServletRequest request) throws IOException;

	PurposeDTO updatePurpose(Long id, PurposeUpdateDTO purposeUpdateDto, HttpServletRequest request) throws IOException;

	void deletePurpose(Long id, HttpServletRequest request) throws IOException;

	List<PurposeDTO> getListPurposes(int limit, int page, String name, String slug, String createdAt, String updatedAt,
			String sortBy);

	Integer getCountPurposes(int limit, int page, String name, String slug, String createdAt, String updatedAt,
			String sortBy);
}
