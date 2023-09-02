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
import com.cafe.website.entity.Purpose;
import com.cafe.website.entity.Purpose;
import com.cafe.website.entity.Purpose;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.PurposeDTO;
import com.cafe.website.payload.PurposeDTO;
import com.cafe.website.payload.PurposeCreateDTO;
import com.cafe.website.payload.PurposeDTO;
import com.cafe.website.payload.PurposeUpdateDTO;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.PurposeRepository;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.PurposeService;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.PurposeMapper;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class PurposeServiceImp implements PurposeService {
	@PersistenceContext
	private EntityManager entityManager;
	private PurposeMapper purposeMapper;
	private CloudinaryService cloudinaryService;
	private PurposeRepository purposeRepository;
	private ImageRepository imageRepository;

	private Slugify slugify = Slugify.builder().build();
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
	String path_category = "cafe-springboot/categories/Purpose";

	public PurposeServiceImp(EntityManager entityManager, PurposeMapper purposeMapper,
			CloudinaryService cloudinaryService, PurposeRepository purposeRepository, ImageRepository imageRepository) {
		super();
		this.entityManager = entityManager;
		this.purposeMapper = purposeMapper;
		this.cloudinaryService = cloudinaryService;
		this.purposeRepository = purposeRepository;
		this.imageRepository = imageRepository;
	}

	@Override
	public List<PurposeDTO> getListPurposes(int limit, int page, String name, String slug, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<PurposeDTO> listPurposeDto;
		List<Purpose> listPurpose;
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

		listPurpose = purposeRepository.findWithFilters(name, slug, pageable, entityManager);

		listPurposeDto = listPurpose.stream().map(purpose -> {
			PurposeDTO purposeDto = MapperUtils.mapToDTO(purpose, PurposeDTO.class);
			Image image = imageRepository.findImageByPurposeId(purposeDto.getId()).orElse(null);
			purposeDto.setImage(ImageDTO.generateImageDTO(image));
			return purposeDto;
		}).collect(Collectors.toList());

		return listPurposeDto;
	}

	@Override
	public PurposeDTO getPurposeById(int id) {
		Purpose purpose = purposeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Purpose", "id", id + ""));
		PurposeDTO purposeDto = MapperUtils.mapToDTO(purpose, PurposeDTO.class);
		Image image = imageRepository.findImageByPurposeId(purpose.getId()).orElse(null);
		purposeDto.setImage(ImageDTO.generateImageDTO(image));
		return purposeDto;
	}

	@Override
	public PurposeDTO getPurposeBySlug(String slug) {
		Purpose purpose = purposeRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("Purpose", "slug", slug + ""));
		PurposeDTO purposeDto = MapperUtils.mapToDTO(purpose, PurposeDTO.class);
		Image image = imageRepository.findImageByPurposeId(purpose.getId()).orElse(null);
		purposeDto.setImage(ImageDTO.generateImageDTO(image));
		return purposeDto;
	}

	@Override
	public PurposeDTO createPurpose(PurposeCreateDTO purposeCreateDto) throws IOException {
		if (purposeRepository.existsBySlug(slugify.slugify(purposeCreateDto.getSlug())))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (purposeRepository.existsByName(purposeCreateDto.getName()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		Purpose purpose = MapperUtils.mapToEntity(purposeCreateDto, Purpose.class);
		purpose.setSlug(slugify.slugify(purposeCreateDto.getSlug()));

		String url = cloudinaryService.uploadImage(purposeCreateDto.getImageFile(), path_category, "image");
		Image image = new Image();
		image.setPurpose(purpose);
		image.setImage(url);
		purpose.setImage(image);

		Purpose newPurpose = purposeRepository.save(purpose);
		PurposeDTO newPurposeDto = MapperUtils.mapToDTO(newPurpose, PurposeDTO.class);
		newPurposeDto.setImage(ImageDTO.generateImageDTO(image));
		return newPurposeDto;
	}

	@Override
	public PurposeDTO updatePurpose(int id, PurposeUpdateDTO purposeUpdateDto) throws IOException {
		PurposeDTO newDto = this.getPurposeById(id);

		if (purposeRepository.existsBySlugAndIdNot(slugify.slugify(purposeUpdateDto.getSlug()), newDto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (purposeRepository.existsByNameAndIdNot(purposeUpdateDto.getName(), newDto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		Purpose purpose = purposeMapper.dtoToEntity(newDto);
		PurposeDTO purposeDTO = MapperUtils.mapToDTO(purposeUpdateDto, PurposeDTO.class);
		if (purposeUpdateDto.getImageFile() != null) {
			String url = cloudinaryService.uploadImage(purposeUpdateDto.getImageFile(), path_category, "image");
			Image image = new Image();
			image.setPurpose(purpose);
			image.setImage(url);
			purpose.setImage(image);
		}

		purposeDTO.setId(id);
		if (purposeUpdateDto.getSlug() != null)
			purposeDTO.setSlug(slugify.slugify(purposeUpdateDto.getSlug()));
		purposeMapper.updatePurposeFromDto(purposeDTO, purpose);
		purposeRepository.save(purpose);

		PurposeDTO newPurposeDTO = MapperUtils.mapToDTO(purpose, PurposeDTO.class);
		newPurposeDTO.setImage(ImageDTO.generateImageDTO(purpose.getImage()));

		return newPurposeDTO;
	}

	@Override
	public void deletePurpose(int id) throws IOException {
		purposeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Purpose", "id", id + ""));
		Image image = imageRepository.findImageByKindId(id).orElse(null);
		if (image != null)
			this.cloudinaryService.removeImageFromCloudinary(image.getImage(), path_category);

		purposeRepository.deleteById(id);

	}

}
