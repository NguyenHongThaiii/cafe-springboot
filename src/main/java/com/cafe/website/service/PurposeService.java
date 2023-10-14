package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.PurposeCreateDTO;
import com.cafe.website.payload.PurposeUpdateDTO;
import com.cafe.website.payload.PurposeDTO;

public interface PurposeService {
	PurposeDTO getPurposeById(int id);

	PurposeDTO getPurposeBySlug(String slug);

	PurposeDTO createPurpose(PurposeCreateDTO purposeCreateDto) throws IOException;

	PurposeDTO updatePurpose(int id, PurposeUpdateDTO purposeUpdateDto) throws IOException;

	void deletePurpose(int id) throws IOException;

	List<PurposeDTO> getListPurposes(int limit, int page, String name, String slug, String createdAt, String updatedAt,
			String sortBy);
}
