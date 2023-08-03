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

-- INSERT DATA TO BLOGS TABLE
INSERT INTO products (name,slug,created_at,updated_at,status,coordinates,email,phone,facebook,list_menu,prices,outstanding) 
VALUES ('','',123,123,1,'','','','','',1,1);
-- INSET DATA TO USERS TABLE
INSERT INTO USERS (created_at,updated_at,status,address,avartar,username,email,password,phone)
VALUES (1,1,1,'','','thai','123123@','$2a$10$fmwrW8yb.BP4mUTJP03nVOGna.BXkUjBKuYcgqFdJdB/AhhDz8kxi','');
INSERT INTO USERS (created_at,updated_at,status,address,avartar,username,email,password,phone)
VALUES (1,1,1,'','','thai123','thai@','$2a$10$fmwrW8yb.BP4mUTJP03nVOGna.BXkUjBKuYcgqFdJdB/AhhDz8kxi','');
-- INSERT DATA TO ROLES TABLE
INSERT INTO ROLES (created_at,updated_at,status,role) VALUES (1,1,1,'ROLE_ADMIN');
INSERT INTO ROLES (created_at,updated_at,status,role) VALUES (1,1,1,'ROLE_USER');
-- INSERT DATA TO USERS_ROLES TABLE
INSERT INTO USERS_ROLES (user_id,role_id) VALUES(1,1);
INSERT INTO USERS_ROLES (user_id,role_id) VALUES(2,2);