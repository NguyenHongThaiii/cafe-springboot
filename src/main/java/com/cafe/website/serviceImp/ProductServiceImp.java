package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Area;
import com.cafe.website.entity.Convenience;
import com.cafe.website.entity.Image;
import com.cafe.website.entity.Kind;
import com.cafe.website.entity.Menu;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.ProductOwner;
import com.cafe.website.entity.ProductSchedule;
import com.cafe.website.entity.Purpose;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.ConvenienceDTO;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.KindDTO;
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
import com.cafe.website.repository.ProductOwnerRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.ProductScheduleRepository;
import com.cafe.website.repository.PurposeRepository;
import com.cafe.website.repository.ReviewRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.ProductService;
import com.cafe.website.service.ReviewService;
import com.cafe.website.util.AreaMapper;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.ProductMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;

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
	private UserRepository userRepository;
	private ProductOwnerRepository productOwnerRepository;
	private ProductMapper productMapper;
	private CloudinaryService cloudinaryService;
	private AreaMapper areaMapper;
	private ScheduledExecutorService scheduledExecutorService;
	private ReviewService reviewService;

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
	ObjectMapper objMapper = new ObjectMapper();
	private Slugify slugify = Slugify.builder().build();
	@Value("${app.timeout}")
	private String timeout;

	public ProductServiceImp(EntityManager entityManager, ProductRepository productRepository,
			AreaRepository areaRepository, KindRepository kindRepository, PurposeRepository purposeRepository,
			ConvenienceRepository conveRepository, ReviewRepository reviewRepository, ImageRepository imageRepository,
			MenuRepository menuRepository, ProductScheduleRepository productScheduleRepository,
			UserRepository userRepository, ProductOwnerRepository productOwnerRepository, ProductMapper productMapper,
			CloudinaryService cloudinaryService, AreaMapper areaMapper,
			ScheduledExecutorService scheduledExecutorService, ReviewService reviewService) {
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
		this.productOwnerRepository = productOwnerRepository;
		this.productMapper = productMapper;
		this.cloudinaryService = cloudinaryService;
		this.areaMapper = areaMapper;
		this.scheduledExecutorService = scheduledExecutorService;
		this.reviewService = reviewService;
	}

	@Override
	public List<ProductDTO> getListProducts(int limit, int page, int status, String rating, Integer isWatingDelete,
			String name, String slugArea, String slugConvenience, String slugKind, String slugPurpose, Double latitude,
			Double longitude, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.PRICEMIN,
				SortField.PRICEMAX, SortField.UPDATEDAT, SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC,
				SortField.PRICEMINDESC, SortField.PRICEMAXDESC, SortField.UPDATEDATDESC, SortField.CREATEDATDESC);
		logger.info(latitude + "");
		logger.info(longitude + "");
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

		productList = productRepository.findWithFilters(name, status, slugArea, slugConvenience, slugKind, slugPurpose,
				isWatingDelete, latitude, longitude, pageable, entityManager);
		listProductDto = productList.stream().map(product -> {
			ProductDTO pdto = MapperUtils.mapToDTO(product, ProductDTO.class);
			List<Image> listEntityImages = imageRepository.findAllImageByProductId(product.getId());
			List<AreaDTO> listArea = MapperUtils.loppMapToDTO(product.getAreas(), AreaDTO.class);
			List<KindDTO> listKind = MapperUtils.loppMapToDTO(product.getKinds(), KindDTO.class);
			List<ConvenienceDTO> listCon = MapperUtils.loppMapToDTO(product.getConveniences(), ConvenienceDTO.class);
			List<PurposeDTO> listPurpose = MapperUtils.loppMapToDTO(product.getPurposes(), PurposeDTO.class);
			List<ProductScheduleDTO> listScheduleDto = new ArrayList<>();

			for (ProductSchedule schedule : product.getSchedules()) {
				ProductScheduleDTO scheduleDto = MapperUtils.mapToDTO(schedule, ProductScheduleDTO.class);
				listScheduleDto.add(scheduleDto);
			}

			pdto.setAreasDto(listArea);
			pdto.setPurposesDto(listPurpose);
			pdto.setKindsDto(listKind);
			pdto.setConveniencesDto(listCon);
			pdto.setListImage(ImageDTO.generateListImageDTO(listEntityImages));
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
	public ProductDTO createProduct(ProductCreateDTO productCreateDto) throws IOException {
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

		List<Kind> listKinds = this.getListFromIds(productCreateDto.getArea_id(), kindRepository, "kind", Kind.class);
		List<KindDTO> listKindDto = MapperUtils.loppMapToDTO(listKinds, KindDTO.class);

		List<Convenience> listCon = this.getListFromIds(productCreateDto.getArea_id(), conveRepository, "convenience",
				Convenience.class);
		List<ConvenienceDTO> listConDto = MapperUtils.loppMapToDTO(listKinds, ConvenienceDTO.class);

		ProductOwner productOwner = new ProductOwner();

		pdto.setSlug(slugify.slugify(productCreateDto.getSlug()));
		productMapper.updateProductFromDto(pdto, product);

		product.setId(0);
		product.setUser(user);
		product.setStatus(0);
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

		product.setAreas(listAreas);
		product.setConveniences(listCon);
		product.setKinds(listKinds);
		product.setPurposes(listPurposes);

		productRepository.save(product);
		menuRepository.saveAll(listMenus);
		imageRepository.saveAll(listImages);

		productOwner.setProduct(product);
		productOwner.setUser(user);
		productOwnerRepository.save(productOwner);

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
		res.setKindsDto(listKindDto);
		res.setConveniencesDto(listConDto);
		res.setPurposesDto(listPurposeDto);

		res.setSchedules(listScheduleDto);
		res.setOwner(MapperUtils.mapToDTO(user, UserDTO.class));
		return res;
	}

	@Override
	public ProductDTO updateProduct(int id, ProductUpdateDTO productUpdateDto) throws IOException {
		User user = userRepository.findById(productUpdateDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", productUpdateDto.getUserId()));
		if (!productOwnerRepository.existsByUserIdAndProductId(user.getId(), productUpdateDto.getId())
				&& !user.isHasRoleAdmin(user.getRoles()) && !user.isHasRoleMod(user.getRoles()))

			throw new CafeAPIException(HttpStatus.UNAUTHORIZED, "Access denied");

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

		List<Purpose> listPurposes = this.getListFromIds(productUpdateDto.getPurpose_id(), purposeRepository, "purpose",
				Purpose.class);
		List<PurposeDTO> listPurposeDto = MapperUtils.loppMapToDTO(listPurposes, PurposeDTO.class);

		List<Kind> listKinds = this.getListFromIds(productUpdateDto.getArea_id(), kindRepository, "kind", Kind.class);
		List<KindDTO> listKindDto = MapperUtils.loppMapToDTO(listKinds, KindDTO.class);

		List<Convenience> listCon = this.getListFromIds(productUpdateDto.getArea_id(), conveRepository, "convenience",
				Convenience.class);
		List<ConvenienceDTO> listConDto = MapperUtils.loppMapToDTO(listKinds, ConvenienceDTO.class);

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

		product.setUser(user);
		productScheduleRepository.deleteAllScheduleByProductId(product.getId());
		productMapper.updateProductFromDto(pdto, product);
		if (listAreas.size() > 0)
			product.setAreas(listAreas);
		if (listCon.size() > 0)
			product.setConveniences(listCon);
		if (listKinds.size() > 0)
			product.setKinds(listKinds);
		if (listPurposes.size() > 0)
			product.setPurposes(listPurposes);

		product.setSchedules(listSchedule);
		productRepository.save(product);

		List<Image> listEntityImages = imageRepository.findAllImageByProductId(id);
		pdto.setListImage(ImageDTO.generateListImageDTO(listEntityImages));
		pdto.setAreasDto(listAreaDto);
		pdto.setKindsDto(listKindDto);
		pdto.setConveniencesDto(listConDto);
		pdto.setPurposesDto(listPurposeDto);
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
	public void deleteProduct(ProductDeleteDTO productDeleteDto) throws IOException {
		this.getProductById(productDeleteDto.getProductId());
		productOwnerRepository.findByProductIdAndUserId(productDeleteDto.getProductId(), productDeleteDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("Onwer", "id", "something went wrong!"));
		String path_menu = "cafe-springboot/menu/";
		String path_blogs = "cafe-springboot/blogs/";
		List<Menu> listEntityMenus = menuRepository.findAllMenuByProductId(productDeleteDto.getProductId());
		List<Image> listEntityImages = imageRepository.findAllImageByProductId(productDeleteDto.getProductId());

		if (listEntityImages != null)
			for (Image temp : listEntityImages)
				cloudinaryService.removeImageFromCloudinary(temp.getImage(), path_blogs);

		if (listEntityMenus != null)
			for (Menu menu : listEntityMenus)
				cloudinaryService.removeImageFromCloudinary(menu.getImage(), path_menu);
		productOwnerRepository.deleteAllOwnerByProductIdAndUserId(productDeleteDto.getProductId(),
				productDeleteDto.getUserId());
		productRepository.deleteById(productDeleteDto.getProductId());
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

		if (listReviews == null || listReviews.size() < 1)
			return 0f;
		float total = 0;
		for (Review review : listReviews) {
			total += (review.getRating().getFood() + review.getRating().getLocation() + review.getRating().getPrice()
					+ review.getRating().getSpace() + review.getRating().getService()) / 5;
		}

		return total / listReviews.size();
	}

	@Override
	public String setIsWaitingDeleteProduct(ProductDeleteDTO productDeleteDto) throws IOException {
		Product product = productRepository.findById(productDeleteDto.getProductId()).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", productDeleteDto.getProductId() + ""));
		productOwnerRepository.findByProductIdAndUserId(productDeleteDto.getProductId(), productDeleteDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("Onwer", "id", "something went wrong!"));
		product.setIsWaitingDelete(1);
		productRepository.save(product);
		executeDeleteProduct(productDeleteDto);
		return "Your product will be deleted after 24 hours";
	}

	@Async
	public void executeDeleteProduct(ProductDeleteDTO productDeleteDto) throws IOException {
		scheduledExecutorService.schedule(() -> {
			try {
				this.deleteProduct(productDeleteDto);
			} catch (IOException e) {
			}
		}, Integer.parseInt(timeout), TimeUnit.SECONDS);
	}
}
