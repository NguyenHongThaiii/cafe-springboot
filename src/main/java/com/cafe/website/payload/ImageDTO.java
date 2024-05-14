package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cafe.website.entity.Image;
import com.cafe.website.serviceImp.ProductServiceImp;
import com.cafe.website.util.MapperUtils;

public class ImageDTO extends BaseImage {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public ImageDTO() {
		// TODO Auto-generated constructor stub
	}

	public static List<ImageDTO> generateListImageDTO(List<Image> listEntityImages) {
		if (listEntityImages == null)
			return null;

		List<ImageDTO> imageString = new ArrayList<>();
		for (Image image : listEntityImages) {
			ImageDTO i = MapperUtils.mapToDTO(image, ImageDTO.class);
			i.setUrl(image.getImage());
			i.setCreatedAt(image.getCreatedAt());
			i.setId(image.getId());
			i.setUpdatedAt(image.getUpdatedAt());
			i.setStatus(image.getStatus());
			imageString.add(i);
		}
		return imageString;
	}

	public static ImageDTO generateImageDTO(Image image) {
		if (image == null)
			return null;
		
		ImageDTO i = MapperUtils.mapToDTO(image, ImageDTO.class);
		i.setUrl(image.getImage());
		i.setCreatedAt(image.getCreatedAt());
		i.setId(image.getId());
		i.setUpdatedAt(image.getUpdatedAt());
		i.setStatus(image.getStatus());
		return i;
	}

}
