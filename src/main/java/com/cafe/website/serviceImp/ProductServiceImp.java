package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Area;
import com.cafe.website.entity.Convenience;
import com.cafe.website.entity.Image;
import com.cafe.website.entity.Kind;
import com.cafe.website.entity.Menu;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.ProductSchedule;
import com.cafe.website.entity.Purpose;
import com.cafe.website.entity.Review;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.BaseImage;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.ProductCreateDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductScheduleDTO;
import com.cafe.website.payload.ProductUpdateDTO;
import com.cafe.website.repository.AreaRepository;
import com.cafe.website.repository.ConvenienceRepository;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.KindRepository;
import com.cafe.website.repository.MenuRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.ProductScheduleRepository;
import com.cafe.website.repository.PurposeRepository;
import com.cafe.website.repository.ReviewRepository;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.ProductService;
import com.cafe.website.util.AreaMapper;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.ProductMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProductServiceImp implements ProductService {
	@PersistenceContext
	private EntityManager entityManager;
	private ProductRepository productRepository;
	private AreaRepository areaRepository;
	private KindRepository kindRepository;
	private PurposeRepository purposeRepository;
	private ConvenienceRepository conveRepository;
	private ReviewRepository reviewRepository;
	private ImageRepository imageRepository;
	private MenuRepository menuRepository;
	private ProductScheduleRepository productScheduleRepository;
	private ProductMapper productMapper;
	private CloudinaryService cloudinaryService;
	private AreaMapper areaMapper;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
	ObjectMapper objMapper = new ObjectMapper();
	private Slugify slugify = Slugify.builder().build();

	public ProductServiceImp(EntityManager entityManager, ProductRepository productRepository,
			AreaRepository areaRepository, KindRepository kindRepository, PurposeRepository purposeRepository,
			ConvenienceRepository conveRepository, ReviewRepository reviewRepository, ImageRepository imageRepository,
			MenuRepository menuRepository, ProductScheduleRepository productScheduleRepository,
			ProductMapper productMapper, CloudinaryService cloudinaryService, AreaMapper areaMapper) {
		super();
		this.entityManager = entityManager;
		this.productRepository = productRepository;
		this.areaRepository = areaRepository;
		this.kindRepository = kindRepository;
		this.purposeRepository = purposeRepository;
		this.conveRepository = conveRepository;
		this.reviewRepository = reviewRepository;
		this.imageRepository = imageRepository;
		this.menuRepository = menuRepository;
		this.productScheduleRepository = productScheduleRepository;
		this.productMapper = productMapper;
		this.cloudinaryService = cloudinaryService;
		this.areaMapper = areaMapper;
	}

	@Override
	public List<ProductDTO> getListProducts(int limit, int page, String name, String sortBy) {

		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.PRICEMIN,
				SortField.PRICEMAX, SortField.UPDATEDAT, SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC,
				SortField.PRICEMINDESC, SortField.PRICEMAXDESC, SortField.UPDATEDATDESC, SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<Product> productList = null;
		List<Sort.Order> sortOrders = new ArrayList<>();
		List<ProductDTO> listProductDto;

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

		productList = productRepository.findWithFilters(name, pageable, entityManager);
		listProductDto = productList.stream().map(product -> {
			ProductDTO pdto = MapperUtils.mapToDTO(product, ProductDTO.class);
			List<Image> listEntityImages = imageRepository.findAllImageByProductId(product.getId());
			List<AreaDTO> listArea = MapperUtils.loppMapToDTO(product.getAreas(), AreaDTO.class);
			List<ProductScheduleDTO> listScheduleDto = new ArrayList<>();

			for (ProductSchedule schedule : product.getSchedules()) {
				ProductScheduleDTO scheduleDto = MapperUtils.mapToDTO(schedule, ProductScheduleDTO.class);
				listScheduleDto.add(scheduleDto);
			}
			// more
			pdto.setAreasDto(listArea);
			pdto.setListImage(ImageDTO.generateListImageDTO(listEntityImages));
			pdto.setSchedules(listScheduleDto);
			return pdto;
		}).collect(Collectors.toList());

		return listProductDto;
	}

	@Override
	public ProductDTO createProduct(ProductCreateDTO productCreateDto) throws IOException {

		if (productRepository.existsBySlug(slugify.slugify(productCreateDto.getSlug())))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (productRepository.existsByName(productCreateDto.getName()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		List<Menu> listMenus = new ArrayList<>();
		List<String> menus = new ArrayList<>();
		List<Image> listImages = new ArrayList<>();
		List<String> images = new ArrayList<>();
		List<ProductSchedule> listSchedule = new ArrayList<>();
		List<ProductScheduleDTO> listScheduleDto = new ArrayList<>();
		List<ProductScheduleDTO> listScheduleParse = new ObjectMapper().readValue(productCreateDto.getListScheduleDto(),
				new TypeReference<List<ProductScheduleDTO>>() {
				});
		ProductDTO pdto = MapperUtils.mapToEntity(productCreateDto, ProductDTO.class);
		Product product = new Product();

		List<Area> listAreas = this.getListFromIds(productCreateDto.getArea_id(), areaRepository, "area",
				AreaDTO.class);
		List<AreaDTO> listAreaDto = MapperUtils.loppMapToDTO(listAreas, AreaDTO.class);

//		List<Purpose> listPurposes = this.getListFromIds(productCreateDto.getPurpose_id(), purposeRepository,
//				"purpose");
//		List<Kind> listKinds = this.getListFromIds(productCreateDto.getArea_id(), kindRepository, "kind");
//		List<Convenience> listCon = this.getListFromIds(productCreateDto.getArea_id(), conveRepository, "convenience");

		pdto.setSlug(slugify.slugify(productCreateDto.getSlug()));
		productMapper.updateProductFromDto(pdto, product);

		product.setId(0);

//		move to their service
		cloudinaryService.uploadImages(images, productCreateDto.getListImageFile(), "cafe-springboot/blogs", "image");
		images.forEach(image -> {
			Image imageItem = new Image();
			imageItem.setImage(image);
			imageItem.setProduct(product);
			listImages.add(imageItem);
		});

		cloudinaryService.uploadImages(menus, productCreateDto.getListImageFile(), "cafe-springboot/blogs", "image");
		menus.forEach(menu -> {
			Menu menuItem = new Menu();
			menuItem.setImage(menu);
			menuItem.setProduct(product);
			listMenus.add(menuItem);
		});

		if (listAreas.size() > 0)
			product.setAreas(listAreas);
//		product.setConveniences(null);
//		product.setKinds(null);
//		product.setPurposes(null);
		productRepository.save(product);
		menuRepository.saveAll(listMenus);
		imageRepository.saveAll(listImages);

		for (ProductScheduleDTO scheduleDto : listScheduleParse) {
			ProductSchedule schedule = new ProductSchedule();
			schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
			schedule.setEndTime(scheduleDto.getEndTime());
			schedule.setStartTime(scheduleDto.getStartTime());
			schedule.setProduct(product);
			listSchedule.add(schedule);
			listScheduleDto.add(MapperUtils.mapToDTO(schedule, ProductScheduleDTO.class));
		}
		productScheduleRepository.saveAll(listSchedule);
		ProductDTO res = MapperUtils.mapToDTO(product, ProductDTO.class);

		List<Image> listEntityImages = imageRepository.findAllImageByProductId(res.getId());

		res.setListImage(ImageDTO.generateListImageDTO(listEntityImages));
		res.setAreasDto(listAreaDto);
		res.setSchedules(listScheduleDto);
		return res;
	}

	@Override
	public ProductDTO updateProduct(int id, ProductUpdateDTO productUpdateDto) throws IOException {
		ProductDTO pdto = this.getProductById(id);
		String path_menu = "cafe-springboot/menu/";
		String path_blogs = "cafe-springboot/blogs/";

		if (productRepository.existsBySlugAndIdNot(slugify.slugify(productUpdateDto.getSlug()), pdto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (productRepository.existsByNameAndIdNot(productUpdateDto.getName(), pdto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		List<ProductSchedule> listSchedule = new ArrayList<>();
		List<ProductScheduleDTO> listScheduleDto = new ArrayList<>();
		List<ProductScheduleDTO> listScheduleParse = new ObjectMapper().readValue(productUpdateDto.getListScheduleDto(),
				new TypeReference<List<ProductScheduleDTO>>() {
				});
		Product product = MapperUtils.mapToEntity(pdto, Product.class);
		productUpdateDto.setId(id);
		productMapper.updateProductDtoFromProductUpdateDto(pdto, productUpdateDto);

		List<Area> listAreas = this.getListFromIds(productUpdateDto.getArea_id(), areaRepository, "area",
				AreaDTO.class);
		List<AreaDTO> listAreaDto = MapperUtils.loppMapToDTO(listAreas, AreaDTO.class);

//		List<Purpose> listPurposes = this.getListFromIds(productUpdateDto.getPurpose_id(), purposeRepository,
//				"purpose");
//		List<Kind> listKinds = this.getListFromIds(productUpdateDto.getArea_id(), kindRepository, "kind");
//		List<Convenience> listCon = this.getListFromIds(productUpdateDto.getArea_id(), conveRepository, "convenience");

		pdto.setSlug(slugify.slugify(productUpdateDto.getSlug()));

		if (productUpdateDto.getListMenuFile() != null) {
			List<String> menus = new ArrayList<>();
			List<Menu> listMenus = new ArrayList<>();
			List<Menu> listEntityMenus = menuRepository.findAllMenuByProductId(id);

			if (listEntityMenus != null)
				for (Menu menu : listEntityMenus)
					cloudinaryService.removeImageFromCloudinary(menu.getImage(), path_menu);

			cloudinaryService.uploadImages(menus, productUpdateDto.getListMenuFile(), path_menu, "image");
			menus.forEach(menuTemp -> {
				Menu menuItem = new Menu();
				menuItem.setImage(menuTemp);
				menuItem.setProduct(product);
				listMenus.add(menuItem);
			});
			menuRepository.deleteAllMenuByProductId(id);
			product.setlistMenus(listMenus);
		}

		if (productUpdateDto.getListImageFile() != null) {
			List<String> images = new ArrayList<>();
			List<Image> listImages = new ArrayList<>();
			List<Image> listEntityImages = imageRepository.findAllImageByProductId(id);

			if (listEntityImages != null)
				for (Image temp : listEntityImages)
					cloudinaryService.removeImageFromCloudinary(temp.getImage(), path_blogs);

			cloudinaryService.uploadImages(images, productUpdateDto.getListImageFile(), path_blogs, "image");
			images.forEach(imageTemp -> {
				Image imageItem = new Image();
				imageItem.setImage(imageTemp);
				imageItem.setProduct(product);
				listImages.add(imageItem);
			});
			imageRepository.deleteAllImageByProductId(id);
			product.setlistImages(listImages);
		}
		for (ProductScheduleDTO scheduleDto : listScheduleParse) {
			ProductSchedule schedule = new ProductSchedule();
			schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
			schedule.setEndTime(scheduleDto.getEndTime());
			schedule.setStartTime(scheduleDto.getStartTime());
			schedule.setProduct(product);
			listSchedule.add(schedule);
			listScheduleDto.add(MapperUtils.mapToDTO(schedule, ProductScheduleDTO.class));
		}

		productScheduleRepository.deleteAllScheduleByProductId(product.getId());
		productMapper.updateProductFromDto(pdto, product);
		if (listAreas.size() > 0)
			product.setAreas(listAreas);
//		product.setConveniences(null);
//		product.setKinds(null);
//		product.setPurposes(null);

		product.setSchedules(listSchedule);
		productRepository.save(product);

		List<Image> listEntityImages = imageRepository.findAllImageByProductId(id);
		pdto.setListImage(ImageDTO.generateListImageDTO(listEntityImages));
		pdto.setAreasDto(listAreaDto);
		pdto.setSchedules(listScheduleDto);
		return pdto;
	}

	@Override
	public ProductDTO getProductById(int id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id + ""));
		ProductDTO productDto = MapperUtils.mapToDTO(product, ProductDTO.class);
		List<Image> listEntityImages = imageRepository.findAllImageByProductId(id);
		List<AreaDTO> listArea = MapperUtils.loppMapToDTO(product.getAreas(), AreaDTO.class);
		// more
		productDto.setAreasDto(listArea);
		productDto.setListImage(ImageDTO.generateListImageDTO(listEntityImages));

		return productDto;
	}

	@Override
	public void deleteProduct(int id) throws IOException {
		ProductDTO productDto = this.getProductById(id);
		String path_menu = "cafe-springboot/menu/";
		String path_blogs = "cafe-springboot/blogs/";
		List<Menu> listEntityMenus = menuRepository.findAllMenuByProductId(id);
		List<Image> listEntityImages = imageRepository.findAllImageByProductId(id);

		if (listEntityImages != null)
			for (Image temp : listEntityImages)
				cloudinaryService.removeImageFromCloudinary(temp.getImage(), path_blogs);

		if (listEntityMenus != null)
			for (Menu menu : listEntityMenus)
				cloudinaryService.removeImageFromCloudinary(menu.getImage(), path_menu);

		productRepository.deleteById(id);

	}

	private <T, U> List<T> getListFromIds(List<Integer> ids, JpaRepository<T, Integer> repository, String entityName,
			Class<U> entityClass) {
		List<T> resultList = new ArrayList<>();
		if (ids != null)

			ids.forEach(id -> {
				T entity = repository.findById(id).orElse(null);
				if (entity != null)
					resultList.add(entity);
			});
		return resultList;
	}

	@Override
	public ProductDTO getProductBySlug(String slug) {
		Product product = productRepository.findBySlugOrName(slug, null)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "slug", slug));

		ProductDTO productDto = MapperUtils.mapToDTO(product, ProductDTO.class);
		List<Image> listEntityImages = imageRepository.findAllImageByProductId(product.getId());

		productDto.setListImage(ImageDTO.generateListImageDTO(listEntityImages));

		return productDto;
	}

	@Override
	public Float getRateReviewByProduct(Integer productId) {
		List<Review> listReviews = reviewRepository.findReviewByProductId(productId);
		logger.info(listReviews.size() + "");

		if (listReviews == null || listReviews.size() < 1)
			return 0f;
		float total = 0;
		for (Review review : listReviews) {
			total += (review.getRating().getFood() + review.getRating().getLocation() + review.getRating().getPrice()
					+ review.getRating().getSpace() + review.getRating().getService()) / 5;
		}

		return total / listReviews.size();
	}
}
