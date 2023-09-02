package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Image;
import com.cafe.website.entity.Kind;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.KindCreateDTO;
import com.cafe.website.payload.KindDTO;
import com.cafe.website.payload.KindUpdateDTO;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.KindRepository;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.KindService;
import com.cafe.website.util.KindMapper;
import com.cafe.website.util.MapperUtils;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class KindServiceImp implements KindService {
	@PersistenceContext
	private EntityManager entityManager;
	private KindMapper kindMapper;
	private CloudinaryService cloudinaryService;
	private KindRepository kindRepository;
	private ImageRepository imageRepository;

	private Slugify slugify = Slugify.builder().build();
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
	String path_category = "cafe-springboot/categories/Kind";

	public KindServiceImp(EntityManager entityManager, KindMapper kindMapper, CloudinaryService cloudinaryService,
			KindRepository kindRepository, ImageRepository imageRepository) {
		super();
		this.entityManager = entityManager;
		this.kindMapper = kindMapper;
		this.cloudinaryService = cloudinaryService;
		this.kindRepository = kindRepository;
		this.imageRepository = imageRepository;
	}

	@Override
	public List<KindDTO> getListKinds(int limit, int page, String name, String slug, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<KindDTO> listKindDto;
		List<Kind> listKind;
		List<Sort.Order> sortOrders = new ArrayList<>();
		if (!StringUtils.isEmpty(sortBy))
			sortByList = Arrays.asList(sortBy.split(","));

		for (String sb : sortByList) {
			boolean isDescending = sb.endsWith("Desc");

			if (isDescending && !StringUtils.isEmpty(sortBy))
				sb = sb.substring(0, sb.length() - 4).trim();

			for (SortField sortField : validSortFields) {
				if (sortField.toString().equals(sb)) {
					sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
					break;
				}
			}
		}

		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));

		listKind = kindRepository.findWithFilters(name, slug, pageable, entityManager);

		listKindDto = listKind.stream().map(kind -> {
			KindDTO kindDto = MapperUtils.mapToDTO(kind, KindDTO.class);
			Image image = imageRepository.findImageByKindId(kind.getId()).orElse(null);
			kindDto.setImage(ImageDTO.generateImageDTO(image));
			return kindDto;
		}).collect(Collectors.toList());

		return listKindDto;
	}

	@Override
	public KindDTO getKindById(int id) {
		Kind kind = kindRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("kind", "id", id + ""));
		KindDTO kindDto = MapperUtils.mapToDTO(kind, KindDTO.class);
		Image image = imageRepository.findImageByKindId(kind.getId()).orElse(null);
		kindDto.setImage(ImageDTO.generateImageDTO(image));
		return kindDto;
	}

	@Override
	public KindDTO getKindBySlug(String slug) {
		Kind kind = kindRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("Kind", "slug", slug));
		KindDTO kindDto = MapperUtils.mapToDTO(kind, KindDTO.class);
		Image image = imageRepository.findImageByKindId(kind.getId()).orElse(null);

		kindDto.setImage(ImageDTO.generateImageDTO(image));
		return kindDto;
	}

	@Override
	public KindDTO createKind(KindCreateDTO kindCreateDto) throws IOException {
		if (kindRepository.existsBySlug(slugify.slugify(kindCreateDto.getSlug())))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (kindRepository.existsByName(kindCreateDto.getName()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		Kind kind = MapperUtils.mapToEntity(kindCreateDto, Kind.class);
		kind.setSlug(slugify.slugify(kindCreateDto.getSlug()));
		String url = cloudinaryService.uploadImage(kindCreateDto.getImageFile(), path_category, "image");

		Image image = new Image();
		image.setKind(kind);
		image.setImage(url);
		kind.setImage(image);

		Kind newKind = kindRepository.save(kind);

		KindDTO newKindDto = MapperUtils.mapToDTO(newKind, KindDTO.class);
		newKindDto.setImage(ImageDTO.generateImageDTO(image));
		return newKindDto;
	}

	@Override
	public KindDTO updateKind(int id, KindUpdateDTO kindUpdateDto) throws IOException {
		KindDTO newDto = this.getKindById(id);

		if (kindRepository.existsBySlugAndIdNot(slugify.slugify(kindUpdateDto.getSlug()), newDto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (kindRepository.existsByNameAndIdNot(kindUpdateDto.getName(), newDto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		Kind kind = kindMapper.dtoToEntity(newDto);
		KindDTO kindDto = MapperUtils.mapToDTO(kindUpdateDto, KindDTO.class);
		if (kindUpdateDto.getImageFile() != null) {
			String url = cloudinaryService.uploadImage(kindUpdateDto.getImageFile(), path_category, "image");
			Image image = new Image();
			image.setKind(kind);
			image.setImage(url);
			kind.setImage(image);
		}
		kindDto.setId(id);
		if (kindUpdateDto.getSlug() != null)
			kindDto.setSlug(slugify.slugify(kindUpdateDto.getSlug()));

		kindMapper.updateKindFromDto(kindDto, kind);
		kindRepository.save(kind);

		KindDTO newKindDto = MapperUtils.mapToDTO(kind, KindDTO.class);
		newKindDto.setImage(ImageDTO.generateImageDTO(kind.getImage()));

		return newKindDto;
	}

	@Override
	public void deleteKind(int id) throws IOException {
		kindRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("kind", "id", id + ""));
		Image image = imageRepository.findImageByKindId(id).orElse(null);
		if (image != null)
			this.cloudinaryService.removeImageFromCloudinary(image.getImage(), path_category);

		kindRepository.deleteById(id);
	}

}
