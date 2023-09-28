package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cafe.website.entity.Image;
import com.cafe.website.entity.Menu;
import com.cafe.website.serviceImp.ProductServiceImp;
import com.cafe.website.util.MapperUtils;

public class MenuDTO extends BaseImage {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public MenuDTO() {
		// TODO Auto-generated constructor stub
	}

	public static List<MenuDTO> generateListMenuDTO(List<Menu> listEntityMenus) {
		if (listEntityMenus == null)
			return null;

		List<MenuDTO> menuUrl = new ArrayList<>();
		for (Menu menu : listEntityMenus) {
			MenuDTO m = MapperUtils.mapToDTO(menu, MenuDTO.class);
			m.setUrl(menu.getImage().getImage());
			menuUrl.add(m);
		}
		return menuUrl;
	}

	public static MenuDTO generateMenuDTO(Menu menu) {
		if (menu == null)
			return null;

		return MapperUtils.mapToDTO(menu, MenuDTO.class);
	}

}
