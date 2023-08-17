use cafe;

-- INSERT DATA TO AREAS TABLE
INSERT INTO areas (name, slug, image, status, created_at, updated_at) 
VALUES ('Sống ảo', 'song-ao', 'https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690898797/cafe-springboot/categories/category-song-ao_hgifol.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO areas (name, slug, image, status, created_at, updated_at) 
VALUES ('Đọc sách', 'doc-sach', 'https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690898797/cafe-springboot/categories/category-doc-sach_msok4h.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO areas (name, slug, image, status, created_at, updated_at) 
VALUES ('Làm việc', 'lam-viec', 'https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690898797/cafe-springboot/categories/category-lam-viec_mgrkki.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO areas (name, slug, image, status, created_at, updated_at) 
VALUES ('Chill', 'chill', 'https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690898797/cafe-springboot/categories/category-chill_hkfzo5.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO areas (name, slug, image, status, created_at, updated_at) 
VALUES ('Hẹn hò', 'hen-ho', 'https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690898797/cafe-springboot/categories/category-hen-ho_alpfxb.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- INSET DATA TO USERS TABLE
INSERT INTO USERS (created_at,updated_at,status,address,avartar,name,email,password,phone,slug)
VALUES (1,1,1,'','','thai','123123@','$2a$10$fmwrW8yb.BP4mUTJP03nVOGna.BXkUjBKuYcgqFdJdB/AhhDz8kxi','','thai');
INSERT INTO USERS (created_at,updated_at,status,address,avartar,name,email,password,phone,slug)
VALUES (1,1,1,'','','thai123','tha123i@','$2a$10$fmwrW8yb.BP4mUTJP03nVOGna.BXkUjBKuYcgqFdJdB/AhhDz8kxi','','thai123');

-- INSERT DATA TO ROLES TABLE areas
INSERT INTO ROLES (created_at,updated_at,status,name) VALUES (1,1,1,'ROLE_ADMIN');
INSERT INTO ROLES (created_at,updated_at,status,name) VALUES (1,1,1,'ROLE_USER');

-- INSERT DATA TO USERS_ROLES TABLE
INSERT INTO USERS_ROLES (user_id,role_id) VALUES(1,1);
INSERT INTO USERS_ROLES (user_id,role_id) VALUES(2,2);

-- INSERT DATA TO BLOGS TABLE
INSERT INTO products (name,slug,created_at,updated_at,status,coordinates,email,phone,facebook,list_menu,price_min,price_max,outstanding) 
VALUES ('Seven Coffee','seven-coffee',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,'[21.0667245","105.7968339]','','0978 917 971',
'"https://www.facebook.com/SevenCoffee.HN/','["https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690976979/cafe-springboot/menu/Seven-Coffee-menu-2_m4jxbs.jpg",
"https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690976978/cafe-springboot/menu/Seven-Coffee-menu-1_ujnjhn.jpg"]',
30000,50000,1);
INSERT INTO products (name,slug,created_at,updated_at,status,coordinates,email,phone,facebook,list_menu,price_min,price_max,outstanding) 
VALUES ('Seven Coffee2','seven-coffee2',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,'[21.0667245","105.7968339]','','0978 917 971',
'"https://www.facebook.com/SevenCoffee.HN/','["https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690976979/cafe-springboot/menu/Seven-Coffee-menu-2_m4jxbs.jpg",
"https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690976978/cafe-springboot/menu/Seven-Coffee-menu-1_ujnjhn.jpg"]',
40000,50000,1);
INSERT INTO products (name,slug,created_at,updated_at,status,coordinates,email,phone,facebook,list_menu,price_min,price_max,outstanding) 
VALUES ('Seven Coffee3','seven-coffee3',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,'[21.0667245","105.7968339]','','0978 917 971',
'"https://www.facebook.com/SevenCoffee.HN/','["https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690976979/cafe-springboot/menu/Seven-Coffee-menu-2_m4jxbs.jpg",
"https://res.cloudinary.com/th-i-nguy-n/image/upload/v1690976978/cafe-springboot/menu/Seven-Coffee-menu-1_ujnjhn.jpg"]',
10000,50000,1);
INSERT INTO products (name,slug,created_at,updated_at,status,coordinates,email,phone,facebook,list_menu,price_min,price_max,outstanding) 
VALUES ('Seven Coffee4','seven-coffee4',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,'[21.0667245","105.7968339]','','0978 917 971',
'"https://www.facebook.com/SevenCoffee.HN/','[]',
10000,50000,1);
-- INSERT DATA TO PRODUCTS_AREAS TABLE
INSERT INTO products_areas(product_id,area_id) values (1,1);
INSERT INTO products_areas(product_id,area_id) values (1,2);

INSERT INTO products_areas(product_id,area_id) values (2,2);
INSERT INTO products_areas(product_id,area_id) values (3,3);
-- INSERT DATA TO RATING TABLE
INSERT INTO ratings (created_at,updated_at,status,food,location,service,price,space) values (now(),now(),1,1,1,1,1,1);
INSERT INTO ratings (created_at,updated_at,status,food,location,service,price,space) values (now(),now(),2,2,2,2,2,2);

-- INSERT DATA TO REVIEWS TABLE
INSERT INTO reviews ( `created_at`, `status`, `updated_at`,  `list_images`, `name`, `product_id`, `rating_id`, `user_id`) 
VALUES ( NOW(), 1, NOW(),  '[]', 'ádasdsad', 1, 1, 1);
INSERT INTO reviews ( `created_at`, `status`, `updated_at`,  `list_images`, `name`, `product_id`, `rating_id`, `user_id`) 
VALUES ( NOW(), 1, NOW(),  '[]', 'qưeqwe', 1, 2, 1);
