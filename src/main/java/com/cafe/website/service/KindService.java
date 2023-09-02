package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.KindCreateDTO;
import com.cafe.website.payload.KindDTO;
import com.cafe.website.payload.KindUpdateDTO;

public interface KindService {

	KindDTO getKindById(int id);

	KindDTO getKindBySlug(String slug);

	KindDTO createKind(KindCreateDTO kindCreateDto) throws IOException;

	KindDTO updateKind(int id, KindUpdateDTO kindUpdateDto) throws IOException;

	void deleteKind(int id) throws IOException;

	List<KindDTO> getListKinds(int limit, int page, String name, String slug, String sortBy);
}
