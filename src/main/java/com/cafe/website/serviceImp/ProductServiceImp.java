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
import com.cafe.website.entity.Purpose;
import com.cafe.website.entity.Review;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ProductCreateDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductUpdateDTO;
import com.cafe.website.repository.AreaRepository;
import com.cafe.website.repository.ConvenienceRepository;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.KindRepository;
import com.cafe.website.repository.MenuRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.PurposeRepository;
import com.cafe.website.repository.ReviewRepository;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.ProductService;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;

@Service
public class ProductServiceImp implements ProductService {
	private ProductRepository productRepository;
	private AreaRepository areaRepository;
	private KindRepository kindRepository;
	private PurposeRepository purposeRepository;
	private ConvenienceRepository conveRepository;
	private ReviewRepository reviewRepository;
	private ImageRepository imageRepository;
	private MenuRepository menuRepository;
	private ProductMapper productMapper;
	private CloudinaryService cloudinaryService;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
	ObjectMapper objMapper = new ObjectMapper();
	private Slugify slugify = Slugify.builder().build();

	public ProductServiceImp(ProductRepository productRepository, AreaRepository areaRepository,
			KindRepository kindRepository, PurposeRepository purposeRepository, ConvenienceRepository conveRepository,
			ProductMapper productMapper, CloudinaryService cloudinaryService, ReviewRepository reviewRepository,
			MenuRepository menuRepository, ImageRepository imageRepository) {
		super();
		this.productRepository = productRepository;
		this.areaRepository = areaRepository;
		this.kindRepository = kindRepository;
		this.purposeRepository = purposeRepository;
		this.conveRepository = conveRepository;
		this.productMapper = productMapper;
		this.cloudinaryService = cloudinaryService;
		this.reviewRepository = reviewRepository;
		this.imageRepository = imageRepository;
		this.menuRepository = menuRepository;
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

		if (name != null && !name.isEmpty())
			productList = productRepository.findByNameContainingIgnoreCase(name, pageable).getContent();
		else
			productList = productRepository.findAll(pageable).getContent();

		listProductDto = productList.stream().map(product -> MapperUtils.mapToDTO(product, ProductDTO.class))
				.collect(Collectors.toList());

		return listProductDto;
	}

	@Override
	public ProductDTO createProduct(ProductCreateDTO productCreateDto) throws IOException {

		if (productRepository.existsBySlugAndIdNot(slugify.slugify(productCreateDto.getSlug()),
				productCreateDto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (productRepository.existsByNameAndIdNot(productCreateDto.getName(), productCreateDto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		List<Menu> listMenus = new ArrayList<>();
		List<String> menus = new ArrayList<>();
		List<Image> listImages = new ArrayList<>();
		List<String> images = new ArrayList<>();
		ProductDTO pdto = MapperUtils.mapToEntity(productCreateDto, ProductDTO.class);
		Product product = new Product();

		List<Area> listAreas = this.getListFromIds(productCreateDto.getArea_id(), areaRepository, "area");
		List<Purpose> listPurposes = this.getListFromIds(productCreateDto.getPurpose_id(), purposeRepository,
				"purpose");
		List<Kind> listKinds = this.getListFromIds(productCreateDto.getArea_id(), kindRepository, "kind");
		List<Convenience> listCon = this.getListFromIds(productCreateDto.getArea_id(), conveRepository, "convenience");

		pdto.setAreas(listAreas);
		pdto.setKinds(listKinds);
		pdto.setPurposes(listPurposes);
		pdto.setConveniences(listCon);
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

		productRepository.save(product);
		menuRepository.saveAll(listMenus);
		imageRepository.saveAll(listImages);

		return MapperUtils.mapToDTO(product, ProductDTO.class);
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

		Product product = MapperUtils.mapToEntity(pdto, Product.class);
		productUpdateDto.setId(id);
		productMapper.updateProductDtoFromProductUpdateDto(pdto, productUpdateDto);

		List<Area> listAreas = this.getListFromIds(productUpdateDto.getArea_id(), areaRepository, "area");
		List<Purpose> listPurposes = this.getListFromIds(productUpdateDto.getPurpose_id(), purposeRepository,
				"purpose");
		List<Kind> listKinds = this.getListFromIds(productUpdateDto.getArea_id(), kindRepository, "kind");
		List<Convenience> listCon = this.getListFromIds(productUpdateDto.getArea_id(), conveRepository, "convenience");

		pdto.setAreas(listAreas);
		pdto.setKinds(listKinds);
		pdto.setPurposes(listPurposes);
		pdto.setConveniences(listCon);
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

		productMapper.updateProductFromDto(pdto, product);
		productRepository.save(product);

		return pdto;
	}

	@Override
	public ProductDTO getProductById(int id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id + ""));

		ProductDTO productDto = MapperUtils.mapToDTO(product, ProductDTO.class);

		return productDto;
	}

	@Override
	public String deleteProduct(int id) throws IOException {
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

		return "Delete successfully";
	}

	private <T> List<T> getListFromIds(List<Integer> ids, JpaRepository<T, Integer> repository, String entityName) {
		List<T> resultList = new ArrayList<>();
		if (ids != null)
			ids.forEach(id -> {
				T entity = repository.findById(id).orElse(null);
				if (entity != null)
					resultList.add(entity);
			});
		return resultList;
	}
}
