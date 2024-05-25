package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.constant.SortField;
import com.cafe.website.constant.StatusLog;
import com.cafe.website.entity.Area;
import com.cafe.website.entity.Convenience;
import com.cafe.website.entity.Image;
import com.cafe.website.entity.Kind;
import com.cafe.website.entity.Menu;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.ProductSchedule;
import com.cafe.website.entity.Purpose;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.ConvenienceDTO;
import com.cafe.website.payload.FileMetadata;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.KindDTO;
import com.cafe.website.payload.MenuDTO;
import com.cafe.website.payload.ProductCreateDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductDeleteDTO;
import com.cafe.website.payload.ProductScheduleDTO;
import com.cafe.website.payload.ProductUpdateDTO;
import com.cafe.website.payload.PurposeDTO;
import com.cafe.website.payload.UserDTO;
import com.cafe.website.repository.AreaRepository;
import com.cafe.website.repository.ConvenienceRepository;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.KindRepository;
import com.cafe.website.repository.MenuRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.ProductScheduleRepository;
import com.cafe.website.repository.PurposeRepository;
import com.cafe.website.repository.ReviewRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.LogService;
import com.cafe.website.service.ProductService;
import com.cafe.website.service.ReviewService;
import com.cafe.website.util.AreaMapper;
import com.cafe.website.util.JsonConverter;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.MethodUtil;
import com.cafe.website.util.ProductMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
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
	private UserRepository userRepository;
	private ProductMapper productMapper;
	private CloudinaryService cloudinaryService;
	private AreaMapper areaMapper;
	private ScheduledExecutorService scheduledExecutorService;
	private ReviewService reviewService;
	private LogService logService;
	private AuthService authService;
	private ObjectMapper objectMapper;

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
	private Slugify slugify = Slugify.builder().build();
	@Value("${app.timeout}")
	private String timeout;
	@Value("${app.path-menu}")
	private String path_menu;
	@Value("${app.path-product}")
	private String path_blogs;

	public ProductServiceImp(EntityManager entityManager, ProductRepository productRepository,
			AreaRepository areaRepository, KindRepository kindRepository, PurposeRepository purposeRepository,
			ConvenienceRepository conveRepository, ReviewRepository reviewRepository, ImageRepository imageRepository,
			MenuRepository menuRepository, ProductScheduleRepository productScheduleRepository,
			UserRepository userRepository, ProductMapper productMapper, CloudinaryService cloudinaryService,
			AreaMapper areaMapper, ScheduledExecutorService scheduledExecutorService, ReviewService reviewService,
			LogService logService, AuthService authService, ObjectMapper objectMapper) {
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
		this.userRepository = userRepository;
		this.productMapper = productMapper;
		this.cloudinaryService = cloudinaryService;
		this.areaMapper = areaMapper;
		this.scheduledExecutorService = scheduledExecutorService;
		this.reviewService = reviewService;
		this.logService = logService;
		this.authService = authService;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<ProductDTO> getListProducts(int limit, int page, Integer status, String rating, Boolean isWatingDelete,
			String name, String slugArea, String slugConvenience, String slugKind, String slugPurpose, Double latitude,
			Double longitude, Long userId, Float ratingsAverage, Integer outstanding, String createdAt,
			String updatedAt, String timeStatus, Integer priceMax, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.PRICEMIN,
				SortField.PRICEMAX, SortField.UPDATEDAT, SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC,
				SortField.PRICEMINDESC, SortField.PRICEMAXDESC, SortField.UPDATEDATDESC, SortField.CREATEDATDESC);

		List<String> sortByList = new ArrayList<String>();
		List<Product> productList = null;
		List<Sort.Order> sortOrders = new ArrayList<>();
		List<ProductDTO> listProductDto;
		Pageable pageable = null;

		if (page != 0) {
			pageable = PageRequest.of(page - 1, limit);
			if (!StringUtils.isEmpty(sortBy))
				sortByList = Arrays.asList(sortBy.split(","));

			for (String sb : sortByList) {
				boolean isDescending = sb.endsWith("Desc");

				if (isDescending && !StringUtils.isEmpty(sortBy))
					sb = sb.substring(0, sb.length() - 4).trim();

				for (SortField sortField : validSortFields) {
					if (sortField.toString().equals(sb.trim())) {
						sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
						break;
					}
				}
			}

		}
		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));

		productList = productRepository.findWithFilters(name, status, slugArea, slugConvenience, slugKind, slugPurpose,
				isWatingDelete, latitude, longitude, userId, ratingsAverage, outstanding, createdAt, updatedAt,
				timeStatus, priceMax, pageable, entityManager);
		listProductDto = productList.stream().map(product -> {
			ProductDTO pdto = MapperUtils.mapToDTO(product, ProductDTO.class);
			List<AreaDTO> listArea = MapperUtils.loppMapToDTO(product.getAreas(), AreaDTO.class);
			List<KindDTO> listKind = MapperUtils.loppMapToDTO(product.getKinds(), KindDTO.class);
			List<ConvenienceDTO> listCon = MapperUtils.loppMapToDTO(product.getConveniences(), ConvenienceDTO.class);
			List<PurposeDTO> listPurpose = MapperUtils.loppMapToDTO(product.getPurposes(), PurposeDTO.class);
			List<ProductScheduleDTO> listScheduleDto = new ArrayList<>();

			for (ProductSchedule schedule : product.getSchedules()) {
				ProductScheduleDTO scheduleDto = MapperUtils.mapToDTO(schedule, ProductScheduleDTO.class);
				listScheduleDto.add(scheduleDto);
			}

			pdto.setAreas(listArea);
			pdto.setPurposes(listPurpose);
			pdto.setKinds(listKind);
			pdto.setConveniences(listCon);
			pdto.setListImage(ImageDTO.generateListImageDTO(product.getListImages()));
			pdto.setListMenu(MenuDTO.generateListMenuDTO(product.getListMenus()));
			pdto.setSchedules(listScheduleDto);
			pdto.setOwner(MapperUtils.mapToDTO(product.getUser(), UserDTO.class));
			pdto.setAvgRating(reviewService.getRatingByReviewId(product.getId()));
			return pdto;
		}).collect(Collectors.toList());

		if (rating != null && rating.equalsIgnoreCase("asc"))
			Collections.sort(listProductDto, (o1, o2) -> Double.compare(o1.getAvgRating(), o2.getAvgRating()));
		if (rating != null && rating.equalsIgnoreCase("desc"))
			Collections.sort(listProductDto, (o1, o2) -> Double.compare(o2.getAvgRating(), o1.getAvgRating()));
		return listProductDto;
	}

	@Override
	public ProductDTO createProduct(ProductCreateDTO productCreateDto, HttpServletRequest request) throws IOException {
		User user = userRepository.findById(productCreateDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", productCreateDto.getUserId()));

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

		List<Purpose> listPurposes = this.getListFromIds(productCreateDto.getPurpose_id(), purposeRepository, "purpose",
				Purpose.class);
		List<PurposeDTO> listPurposeDto = MapperUtils.loppMapToDTO(listPurposes, PurposeDTO.class);

		List<Kind> listKinds = this.getListFromIds(productCreateDto.getKind_id(), kindRepository, "kind", Kind.class);
		List<KindDTO> listKindDto = MapperUtils.loppMapToDTO(listKinds, KindDTO.class);

		List<Convenience> listCon = this.getListFromIds(productCreateDto.getConvenience_id(), conveRepository,
				"convenience", Convenience.class);
		List<ConvenienceDTO> listConDto = MapperUtils.loppMapToDTO(listCon, ConvenienceDTO.class);
		if (productCreateDto.getSlug() != null)
			pdto.setSlug(slugify.slugify(productCreateDto.getSlug()));
		else
			pdto.setSlug(slugify.slugify(productCreateDto.getName()));
		productMapper.updateProductFromDto(pdto, product);

		product.setUser(user);
		product.setStatus(0);
		product.setIsWaitingDelete(false);
		product.setAreas(listAreas);
		product.setConveniences(listCon);
		product.setKinds(listKinds);
		product.setPurposes(listPurposes);
		product.setId(null);

		productRepository.save(product);
//		move to their service
		cloudinaryService.uploadImages(images, productCreateDto.getListImageFile(), path_blogs, "image");
		images.forEach(image -> {
			Image imageItem = new Image();
			imageItem.setImage(image);
			imageItem.setProduct(product);
			listImages.add(imageItem);
		});

		cloudinaryService.uploadImages(menus, productCreateDto.getListImageFile(), path_menu, "image");
		menus.forEach(menu -> {
			Menu menuItem = new Menu();
			Image image = new Image();
			image.setImage(menu);
			image.setMenu(menuItem);
			menuItem.setImage(image);
			menuItem.setProduct(product);
			listMenus.add(menuItem);
		});

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

		res.setListImage(ImageDTO.generateListImageDTO(listImages));
		res.setListMenu(MenuDTO.generateListMenuDTO(listMenus));
		res.setAreas(listAreaDto);
		res.setKinds(listKindDto);
		res.setConveniences(listConDto);
		res.setPurposes(listPurposeDto);
		res.setSchedules(listScheduleDto);
		res.setOwner(MapperUtils.mapToDTO(user, UserDTO.class));
		pdto.setAvgRating(reviewService.getRatingByReviewId(product.getId()));
		MethodUtil.convertListFileImageToInfo(productCreateDto.getListFileMetadatas(),
				productCreateDto.getListImageFile());
		MethodUtil.convertListFileImageToInfo(productCreateDto.getListFileMetadatas(),
				productCreateDto.getListMenuFile());
		productCreateDto.setListMenuFile(null);
		productCreateDto.setListImageFile(null);
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Create Product SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(),
					MethodUtil.handleSubstringMessage(objectMapper.writeValueAsString(productCreateDto)),
					"Create Product SUCCESSFULY");
			return res;
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Create Product SUCCESSFULY");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductUpdateDTO productUpdateDto, HttpServletRequest request)
			throws IOException {
		User user = userRepository.findById(productUpdateDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", productUpdateDto.getUserId()));
		if (!productRepository.existsByIdAndUserId(id, user.getId()))
			throw new CafeAPIException(HttpStatus.UNAUTHORIZED, "Access denied");

		ProductDTO pdto = this.getProductById(id);

		if (productRepository.existsBySlugAndIdNot(slugify.slugify(productUpdateDto.getSlug()), pdto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (productRepository.existsByNameAndIdNot(productUpdateDto.getName(), pdto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		List<ProductSchedule> listSchedule = new ArrayList<>();
		List<ProductScheduleDTO> listScheduleDto = new ArrayList<>();

		Product product = MapperUtils.mapToEntity(pdto, Product.class);
		productMapper.updateProductDtoFromProductUpdateDto(pdto, productUpdateDto);

		List<Area> listAreas = this.getListFromIds(productUpdateDto.getArea_id(), areaRepository, "area",
				AreaDTO.class);
		List<AreaDTO> listAreaDto = MapperUtils.loppMapToDTO(listAreas, AreaDTO.class);

		List<Purpose> listPurposes = this.getListFromIds(productUpdateDto.getPurpose_id(), purposeRepository, "purpose",
				Purpose.class);
		List<PurposeDTO> listPurposeDto = MapperUtils.loppMapToDTO(listPurposes, PurposeDTO.class);

		List<Kind> listKinds = this.getListFromIds(productUpdateDto.getArea_id(), kindRepository, "kind", Kind.class);
		List<KindDTO> listKindDto = MapperUtils.loppMapToDTO(listKinds, KindDTO.class);

		List<Convenience> listCon = this.getListFromIds(productUpdateDto.getArea_id(), conveRepository, "convenience",
				Convenience.class);
		List<ConvenienceDTO> listConDto = MapperUtils.loppMapToDTO(listKinds, ConvenienceDTO.class);
		if (productUpdateDto.getSlug() != null && !productUpdateDto.getSlug().isEmpty())
			pdto.setSlug(slugify.slugify(productUpdateDto.getSlug()));

		if (productUpdateDto.getListMenuFile().size() > 0) {
			List<String> menus = new ArrayList<>();
			List<Menu> listMenus = new ArrayList<>();
			List<Menu> listEntityMenus = menuRepository.findAllMenuByProductId(id);

			if (listEntityMenus != null)
				for (Menu menu : listEntityMenus)
					cloudinaryService.removeImageFromCloudinary(menu.getImage().getImage(), path_menu);

			cloudinaryService.uploadImages(menus, productUpdateDto.getListMenuFile(), path_menu, "image");
			menus.forEach(menuTemp -> {
				Menu menuItem = new Menu();
//				menuItem.setImage(menuTemp);
				menuItem.setProduct(product);
				listMenus.add(menuItem);
			});
			menuRepository.deleteAllMenuByProductId(id);
			product.setListMenus(listMenus);
			MethodUtil.convertListFileImageToInfo(productUpdateDto.getListFileMetadatas(),
					productUpdateDto.getListMenuFile());
			productUpdateDto.setListMenuFile(null);
		}

		if (productUpdateDto.getListImageFile().size() > 0) {
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
			product.setListImages(listImages);
			MethodUtil.convertListFileImageToInfo(productUpdateDto.getListFileMetadatas(),
					productUpdateDto.getListImageFile());
			productUpdateDto.setListImageFile(null);
		}
//		need fix
		if (productUpdateDto.getListScheduleDto() != null) {
			List<ProductScheduleDTO> listScheduleParse = new ObjectMapper()
					.readValue(productUpdateDto.getListScheduleDto(), new TypeReference<List<ProductScheduleDTO>>() {
					});
			for (ProductScheduleDTO scheduleDto : listScheduleParse) {
				ProductSchedule schedule = new ProductSchedule();
				schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
				schedule.setEndTime(scheduleDto.getEndTime());
				schedule.setStartTime(scheduleDto.getStartTime());
				schedule.setProduct(product);
				listSchedule.add(schedule);
				listScheduleDto.add(MapperUtils.mapToDTO(schedule, ProductScheduleDTO.class));
			}
		}
		product.setUser(user);
		if (listSchedule.size() > 0) {
			productScheduleRepository.deleteAllScheduleByProductId(product.getId());
			productScheduleRepository.saveAll(listSchedule);
		}

		productMapper.updateProductFromDto(pdto, product);
		product.getSchedules().forEach(sc -> {
			sc.setProduct(product);
		});
		if (listAreas.size() > 0)
			product.setAreas(listAreas);
		if (listCon.size() > 0)
			product.setConveniences(listCon);
		if (listKinds.size() > 0)
			product.setKinds(listKinds);
		if (listPurposes.size() > 0)
			product.setPurposes(listPurposes);
		if (listSchedule.size() > 0)
			product.setSchedules(listSchedule);
		productRepository.save(product);

		List<ProductScheduleDTO> listSchedulesDto = new ArrayList<>();

		for (ProductSchedule schedule : product.getSchedules()) {
			ProductScheduleDTO scheduleDto = MapperUtils.mapToDTO(schedule, ProductScheduleDTO.class);
			listSchedulesDto.add(scheduleDto);
		}

		pdto.setListImage(ImageDTO.generateListImageDTO(product.getListImages()));
		pdto.setListMenu(MenuDTO.generateListMenuDTO(product.getListMenus()));
		pdto.setAreas(listAreaDto);
		pdto.setKinds(listKindDto);
		pdto.setConveniences(listConDto);
		pdto.setPurposes(listPurposeDto);
		pdto.setSchedules(listSchedulesDto);
		pdto.setOwner(MapperUtils.mapToDTO(product.getUser(), UserDTO.class));
		pdto.setAvgRating(reviewService.getRatingByReviewId(product.getId()));

		Map<String, Object> logData = new HashMap<>();
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Update Product SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(),
					MethodUtil.handleSubstringMessage(objectMapper.writeValueAsString(logData)),
					"Update Product SUCCESSFULY");
			return pdto;
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Update Product FAILED");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public ProductDTO getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id + ""));
		ProductDTO productDto = MapperUtils.mapToDTO(product, ProductDTO.class);
		List<AreaDTO> listArea = MapperUtils.loppMapToDTO(product.getAreas(), AreaDTO.class);
		List<PurposeDTO> listPurpose = MapperUtils.loppMapToDTO(product.getPurposes(), PurposeDTO.class);
		List<KindDTO> listKind = MapperUtils.loppMapToDTO(product.getKinds(), KindDTO.class);
		List<ConvenienceDTO> listCon = MapperUtils.loppMapToDTO(product.getConveniences(), ConvenienceDTO.class);
		List<ProductScheduleDTO> listScheduleDto = new ArrayList<>();

		for (ProductSchedule schedule : product.getSchedules()) {
			ProductScheduleDTO scheduleDto = MapperUtils.mapToDTO(schedule, ProductScheduleDTO.class);
			listScheduleDto.add(scheduleDto);
		}
		// more
		productDto.setAreas(listArea);
		productDto.setConveniences(listCon);
		productDto.setKinds(listKind);
		productDto.setPurposes(listPurpose);
		productDto.setSchedules(listScheduleDto);
		productDto.setOwner(MapperUtils.mapToDTO(product.getUser(), UserDTO.class));
		productDto.setAvgRating(reviewService.getRatingByReviewId(product.getId()));
		productDto.setListImage(ImageDTO.generateListImageDTO(product.getListImages()));
		productDto.setListMenu(MenuDTO.generateListMenuDTO(product.getListMenus()));
		return productDto;
	}

	@Override
	public void deleteProduct(ProductDeleteDTO productDeleteDto, HttpServletRequest request) throws IOException {
		productRepository.findById(productDeleteDto.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productDeleteDto.getProductId()));
		productRepository.findByIdAndUserId(productDeleteDto.getProductId(), productDeleteDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("Onwer", "id", "something went wrong!"));

		List<Menu> listEntityMenus = menuRepository.findAllMenuByProductId(productDeleteDto.getProductId());
		List<Image> listEntityImages = imageRepository.findAllImageByProductId(productDeleteDto.getProductId());

		if (listEntityImages != null)
			for (Image temp : listEntityImages)
				cloudinaryService.removeImageFromCloudinary(temp.getImage(), path_blogs);

		if (listEntityMenus != null)
			for (Menu menu : listEntityMenus)
				cloudinaryService.removeImageFromCloudinary(menu.getImage().getImage(), path_menu);

		productRepository.deleteById(productDeleteDto.getProductId());
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Delete Product SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(productDeleteDto),
					"Delete Product SUCCESSFULY");
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Delete Product FAILED");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	private <T, U> List<T> getListFromIds(List<Long> ids, JpaRepository<T, Long> repository, String entityName,
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
		List<AreaDTO> listArea = MapperUtils.loppMapToDTO(product.getAreas(), AreaDTO.class);
		List<PurposeDTO> listPurpose = MapperUtils.loppMapToDTO(product.getPurposes(), PurposeDTO.class);
		List<KindDTO> listKind = MapperUtils.loppMapToDTO(product.getKinds(), KindDTO.class);
		List<ConvenienceDTO> listCon = MapperUtils.loppMapToDTO(product.getConveniences(), ConvenienceDTO.class);
		List<ProductScheduleDTO> listScheduleDto = new ArrayList<>();

		productDto.setListImage(ImageDTO.generateListImageDTO(listEntityImages));
		productDto.setAvgRating(reviewService.getRatingByReviewId(product.getId()));
		for (ProductSchedule schedule : product.getSchedules()) {
			ProductScheduleDTO scheduleDto = MapperUtils.mapToDTO(schedule, ProductScheduleDTO.class);
			listScheduleDto.add(scheduleDto);
		}
		// more
		productDto.setAreas(listArea);
		productDto.setConveniences(listCon);
		productDto.setKinds(listKind);
		productDto.setPurposes(listPurpose);
		productDto.setSchedules(listScheduleDto);
		productDto.setOwner(MapperUtils.mapToDTO(product.getUser(), UserDTO.class));
		productDto.setAvgRating(reviewService.getRatingByReviewId(product.getId()));
		productDto.setListMenu(MenuDTO.generateListMenuDTO(product.getListMenus()));

		return productDto;
	}

	@Override
	public Float getRateReviewByProduct(Long productId) {
		productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
		List<Review> listReviews = reviewRepository.findReviewByProductId(productId);

		if (listReviews == null || listReviews.size() < 1)
			return 0f;
		float total = 0;
		for (Review review : listReviews) {
			total += review.getAverageRating();
		}

		return total / listReviews.size();
	}

	@Override
	public String setIsWaitingDeleteProduct(ProductDeleteDTO productDeleteDto, HttpServletRequest request)
			throws IOException {
		Product product = productRepository.findById(productDeleteDto.getProductId()).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", productDeleteDto.getProductId() + ""));
		productRepository.findByIdAndUserId(productDeleteDto.getProductId(), productDeleteDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("Onwer", "id", "something went wrong!"));
		product.setIsWaitingDelete(true);
		productRepository.save(product);
		executeDeleteProduct(productDeleteDto, request);
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Set Delete Product SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(productDeleteDto),
					"Set Delete Product SUCCESSFULY");
			return "Your product will be deleted after 24 hours";
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Set Delete Product FAILED");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Async
	public void executeDeleteProduct(ProductDeleteDTO productDeleteDto, HttpServletRequest request) throws IOException {
		scheduledExecutorService.schedule(() -> {
			try {
				this.deleteProduct(productDeleteDto, request);
			} catch (IOException e) {
			}
		}, Integer.parseInt(timeout), TimeUnit.SECONDS);
	}

	@Override
	public Long getCountProduct(Integer status) {
		if (status == null)
			return 0L;
		productRepository.countByStatus(status);
		return productRepository.countByStatus(status);
	}

	@Override
	public Integer getCountProducts(int limit, int page, Integer status, String rating, Boolean isWatingDelete,
			String name, String slugArea, String slugConvenience, String slugKind, String slugPurpose, Double latitude,
			Double longitude, Long userId, Float ratingsAverage, Integer outstanding, String createdAt,
			String updatedAt, String timeStatus, Integer priceMax, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.PRICEMIN,
				SortField.PRICEMAX, SortField.UPDATEDAT, SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC,
				SortField.PRICEMINDESC, SortField.PRICEMAXDESC, SortField.UPDATEDATDESC, SortField.CREATEDATDESC);
		List<String> sortByList = new ArrayList<String>();
		List<Product> productList = null;
		List<Sort.Order> sortOrders = new ArrayList<>();
		Pageable pageable = null;

		if (page != 0) {
			pageable = PageRequest.of(page - 1, limit);
			if (!StringUtils.isEmpty(sortBy))
				sortByList = Arrays.asList(sortBy.split(","));

			for (String sb : sortByList) {
				boolean isDescending = sb.endsWith("Desc");

				if (isDescending && !StringUtils.isEmpty(sortBy))
					sb = sb.substring(0, sb.length() - 4).trim();

				for (SortField sortField : validSortFields) {
					if (sortField.toString().equals(sb.trim())) {
						sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
						break;
					}
				}
			}

		}
		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));
		productList = productRepository.findWithFilters(name, status, slugArea, slugConvenience, slugKind, slugPurpose,
				isWatingDelete, latitude, longitude, userId, ratingsAverage, outstanding, createdAt, updatedAt,
				timeStatus, priceMax, pageable, entityManager);
		return productList.size();
	}

}
