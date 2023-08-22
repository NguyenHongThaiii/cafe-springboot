package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import com.cafe.website.entity.Image;
import com.cafe.website.util.MapperUtils;

public class ImageDTO extends BaseImage {
	private ImageDTO(String url) {
		super(url);
	}

	public ImageDTO() {
		// TODO Auto-generated constructor stub
	}

	public static class Builder {
		private String url;

		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}

		public ImageDTO build() {
			return new ImageDTO(url);
		}
	}

	public static List<ImageDTO> generateListImageDTO(List<Image> listEntityImages) {
		if (listEntityImages == null)
			return null;

		List<ImageDTO> imageString = new ArrayList<>();
		for (Image image : listEntityImages) {
			imageString.add(new ImageDTO.Builder().setUrl(image.getImage()).build());
		}
		return imageString;
	}

	public static ImageDTO generateImageDTO(Image image) {
		if (image == null)
			return null;

		return new ImageDTO.Builder().setUrl(image.getImage()).build();
	}
}
