-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cafe
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `areas`
--

DROP TABLE IF EXISTS `areas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `areas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `areas`
--

LOCK TABLES `areas` WRITE;
/*!40000 ALTER TABLE `areas` DISABLE KEYS */;
INSERT INTO `areas` VALUES (1,'2023-10-13 10:52:38.143922',1,'29-11-2023:02:05:35','Sống ảo','asasdasd'),(2,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','Đọc sách','doc-sach'),(3,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','Làm việc','lam-viec'),(4,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','Chill','chill'),(5,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','Hẹn hò','hen-ho'),(7,'21-12-2023:18:26:48',0,'21-12-2023:18:26:48','123 as','test-ting-123'),(11,'22-12-2023:02:43:58',0,'22-12-2023:02:43:58','123 asa2','test-ting-12'),(12,'22-12-2023:02:50:27',0,'22-12-2023:02:50:27','123 asa24','test-ting-1234');
/*!40000 ALTER TABLE `areas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `review_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdpo60i7auk5cudv7kkny8jrqb` (`review_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKdpo60i7auk5cudv7kkny8jrqb` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (10,'17-12-2023:14:39:04',1,'17-12-2023:15:54:25','test UPDATED UPDATED',1,1),(11,'17-12-2023:14:39:32',1,'17-12-2023:15:00:18','test2 UPDATED',1,1),(12,'17-12-2023:14:39:51',1,'17-12-2023:14:39:51','đụ mẹ mày thái',1,1),(15,'17-12-2023:15:54:20',1,'17-12-2023:15:54:20','213213',1,1),(16,'22-12-2023:23:55:10',1,'22-12-2023:23:55:10','wqe',1,1),(17,'22-12-2023:23:55:10',1,'22-12-2023:23:55:10','wqe',1,1);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conveniences`
--

DROP TABLE IF EXISTS `conveniences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conveniences` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conveniences`
--

LOCK TABLES `conveniences` WRITE;
/*!40000 ALTER TABLE `conveniences` DISABLE KEYS */;
/*!40000 ALTER TABLE `conveniences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discounts`
--

DROP TABLE IF EXISTS `discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `expiry_date` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `percent` int DEFAULT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6rfl9mcvx0j58d6kpvd4rk2r` (`product_id`),
  CONSTRAINT `FKe3tqxsyxv7qcy8uvf2lns1hx8` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discounts`
--

LOCK TABLES `discounts` WRITE;
/*!40000 ALTER TABLE `discounts` DISABLE KEYS */;
/*!40000 ALTER TABLE `discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `comment_id` int DEFAULT NULL,
  `review_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKenol5bdci3ia5woh2h80f53uw` (`user_id`,`review_id`),
  UNIQUE KEY `UK7je5w165at73ypdnd1kid59sb` (`user_id`,`comment_id`),
  KEY `FKl50ufvx8qlrb3rbnn609kmirg` (`comment_id`),
  KEY `FKgfkotln6fxuwihc2h10p5b4je` (`review_id`),
  CONSTRAINT `FKgfkotln6fxuwihc2h10p5b4je` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`id`),
  CONSTRAINT `FKk7du8b8ewipawnnpg76d55fus` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKl50ufvx8qlrb3rbnn609kmirg` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorites`
--

LOCK TABLES `favorites` WRITE;
/*!40000 ALTER TABLE `favorites` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `area_id` int DEFAULT NULL,
  `convenience_id` int DEFAULT NULL,
  `kind_id` int DEFAULT NULL,
  `menu_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `purpose_id` int DEFAULT NULL,
  `review_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2bnasywki3yuyuge6qvwv42a1` (`area_id`),
  UNIQUE KEY `UK_p9mnaka8yegvua1l97f958wfa` (`convenience_id`),
  UNIQUE KEY `UK_5vmudkpvvetsu7s6o4rj2b1n5` (`kind_id`),
  UNIQUE KEY `UK_k45hn2lj5njxgc8scmuiy9nw1` (`menu_id`),
  UNIQUE KEY `UK_o0b52sc2nqaf9skx6et0rkg1l` (`purpose_id`),
  UNIQUE KEY `UK_gn0kkmw9cx9tbd2bwc6xxbqr7` (`user_id`),
  KEY `FKghwsjbjo7mg3iufxruvq6iu3q` (`product_id`),
  KEY `FKldf2s8wlcsqgmmrokc6syxw5q` (`review_id`),
  CONSTRAINT `FK13ljqfrfwbyvnsdhihwta8cpr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKeh92qju2nvbv2nij4ijarxjn0` FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`),
  CONSTRAINT `FKeqiyalvmtp5cyg56v4tun1mnj` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`),
  CONSTRAINT `FKghwsjbjo7mg3iufxruvq6iu3q` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKldf2s8wlcsqgmmrokc6syxw5q` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`id`),
  CONSTRAINT `FKobl1tgspppifv2pwveg4ayccc` FOREIGN KEY (`convenience_id`) REFERENCES `conveniences` (`id`),
  CONSTRAINT `FKoqqscxru25m97w2m3l5fmol69` FOREIGN KEY (`purpose_id`) REFERENCES `purposes` (`id`),
  CONSTRAINT `FKtnaqnmtcffx1meppbaksr5lh9` FOREIGN KEY (`kind_id`) REFERENCES `kinds` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1695932565/cafe-springboot/blogs/gasiu6nc1cf7h1mj3rif.jpg',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(2,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1695932563/cafe-springboot/blogs/ubwdiggd8lb9astij6vk.jpg',NULL,NULL,NULL,NULL,1,NULL,NULL,NULL),(3,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1695932634/cafe-springboot/blogs/jeqsvgf9ovl0xsiajjoq.jpg',NULL,NULL,NULL,2,NULL,NULL,NULL,NULL),(4,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1695932631/cafe-springboot/blogs/xgh8rc6d7ut35mj4vlwh.jpg',NULL,NULL,NULL,NULL,2,NULL,NULL,NULL),(5,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1695932903/cafe-springboot/blogs/knjigktw9o4nl8cs6dwi.jpg',NULL,NULL,NULL,3,NULL,NULL,NULL,NULL),(7,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1695932901/cafe-springboot/blogs/d4fq8p6wubf9c2rwkgqw.jpg',NULL,NULL,NULL,NULL,3,NULL,NULL,NULL),(8,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1696005672/cafe-springboot/blogs/u3pc5qeil4yps6m0ye4m.jpg',NULL,NULL,NULL,4,NULL,NULL,NULL,NULL),(9,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1696005669/cafe-springboot/blogs/j2bkmkrtvgbwguozmakj.jpg',NULL,NULL,NULL,NULL,4,NULL,NULL,NULL),(10,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1696006391/cafe-springboot/blogs/fyayj0mauqdyeghgxxpf.jpg',NULL,NULL,NULL,5,NULL,NULL,NULL,NULL),(11,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1696006388/cafe-springboot/blogs/hzlc3kulaqf6fsxuq4vs.jpg',NULL,NULL,NULL,NULL,5,NULL,NULL,NULL),(12,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1696006741/cafe-springboot/blogs/jvakef4pgn1gdwweg5xz.jpg',NULL,NULL,NULL,6,NULL,NULL,NULL,NULL),(13,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1696006738/cafe-springboot/blogs/o0mhwe8od0eb6baku9hh.jpg',NULL,NULL,NULL,NULL,6,NULL,NULL,NULL),(14,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1696006863/cafe-springboot/menu/qqplk15lmwmu8xkeipir.jpg',NULL,NULL,NULL,7,NULL,NULL,NULL,NULL),(15,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1696006860/cafe-springboot/blogs/utkttc5ltjviyas0sflb.jpg',NULL,NULL,NULL,NULL,7,NULL,NULL,NULL),(17,'21-12-2023:18:26:48',1,'21-12-2023:18:26:48','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1703158010/cafe-springboot/categories/Area/t9jgltapx7loe6enrvzr.jpg',7,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'22-12-2023:02:43:58',1,'22-12-2023:02:43:58','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1703187837/cafe-springboot/categories/Area/w8htt3fz01jmseygk6f4.jpg',11,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,'22-12-2023:02:50:27',1,'22-12-2023:02:50:27','http://res.cloudinary.com/th-i-nguy-n/image/upload/v1703188226/cafe-springboot/categories/Area/cy3eqjemoa9nkjfmobiv.jpg',12,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kinds`
--

DROP TABLE IF EXISTS `kinds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kinds` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kinds`
--

LOCK TABLES `kinds` WRITE;
/*!40000 ALTER TABLE `kinds` DISABLE KEYS */;
/*!40000 ALTER TABLE `kinds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `agent` varchar(255) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  `endpoint` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgqy8beil5y4almtq1tiyofije` (`user_id`),
  CONSTRAINT `FKgqy8beil5y4almtq1tiyofije` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
INSERT INTO `logs` VALUES (1,'25-11-2023:18:29:58',1,'25-11-2023:18:29:58','PostmanRuntime/7.35.0','{\"email\":\"123123@\",\"password\":\"123123\"}','/api/v1/auth/signin','Login SUCCESSFULY','POST','{}','SUCCESSFULLY',1,NULL),(2,'25-11-2023:18:33:24',1,'25-11-2023:18:33:24','PostmanRuntime/7.35.0','{\"email\":\"123123@\",\"password\":\"123123\"}','/api/v1/auth/signin','Login SUCCESSFULY','POST','{\"thai123\":\"123\"}','SUCCESSFULLY',1,NULL),(3,'25-11-2023:19:15:29',1,'25-11-2023:19:15:29','PostmanRuntime/7.35.0','{\r\n    \"email\":\"123123@333\",\r\n    \"password\":\"123123\"\r\n}','/api/v1/auth/signin','User not found with email : \'123123@333\'','POST','{\"thai123\":\"123\"}','FAILED',NULL,NULL),(4,'25-11-2023:19:21:23',1,'25-11-2023:19:21:23','PostmanRuntime/7.35.0','{\r\n    \"email\":\"123123@333\",\r\n    \"password\":\"123123\"\r\n}','/api/v1/auth/signin','User not found with email : \'123123@333\'','POST','{\"thai123\":\"123\"}','FAILED',NULL,NULL),(5,'25-11-2023:21:47:54',1,'25-11-2023:21:47:54','PostmanRuntime/7.35.0','{\r\n    \"email\":\"123123@\",\r\n    \"password\":\"12312312323\"\r\n}','/api/v1/auth/signin','Bad credentials','POST','{\"thai123\":\"123\"}','FAILED',NULL,NULL),(6,'25-11-2023:21:50:04',1,'25-11-2023:21:50:04','PostmanRuntime/7.35.0','{\r\n    \"email\":\"123123@\",\r\n    \"password\":\"12312312323\"\r\n}','/api/v1/auth/signin','Email or password is not correct.','POST','{\"thai123\":\"123\"}','FAILED',NULL,NULL),(7,'25-11-2023:23:11:32',1,'25-11-2023:23:11:32','PostmanRuntime/7.35.0','','/api/v1/auth/update/thai','Handler dispatch failed: java.lang.Error: Unresolved compilation problem: \n	The method updateUser(String, UserUpdateDTO, HttpServletRequest) in the type AuthService is not applicable for the arguments (String, UserUpdateDTO)\n','PATCH','{\"status\":\"1\"}','FAILED',1,NULL),(8,'25-11-2023:23:13:01',1,'25-11-2023:23:13:01','PostmanRuntime/7.35.0','{\"email\":null,\"password\":\"\",\"name\":null,\"address\":null,\"avartar\":null,\"phone\":null,\"status\":1,\"slug\":\"thai\",\"listProductSaved\":[],\"roles\":null}','/api/v1/auth/update/thai','Update User SUCCESSFULY','PATCH','{\"status\":\"1\"}','SUCCESSFULLY',1,NULL),(9,'29-11-2023:01:57:54',1,'29-11-2023:01:57:54','PostmanRuntime/7.35.0','{\r\n    \"email\":\"123123@\",\r\n    \"password\":\"12312312323\"\r\n}','/api/v1/auth/signin','Email or password is not correct.','POST','{\"thai123\":\"123\"}','FAILED',NULL,NULL),(10,'29-11-2023:01:58:01',1,'29-11-2023:01:58:01','PostmanRuntime/7.35.0','{\r\n    \"email\":\"123123@\",\r\n    \"password\":\"123123123\"\r\n}','/api/v1/auth/signin','Email or password is not correct.','POST','{\"thai123\":\"123\"}','FAILED',NULL,NULL),(11,'29-11-2023:01:58:05',1,'29-11-2023:01:58:05','PostmanRuntime/7.35.0','{\r\n    \"email\":\"123123@\",\r\n    \"password\":\"123123\"\r\n}','/api/v1/auth/signin','Email or password is not correct.','POST','{\"thai123\":\"123\"}','FAILED',NULL,NULL),(12,'29-11-2023:01:58:09',1,'29-11-2023:01:58:09','PostmanRuntime/7.35.0','{\r\n    \"email\":\"123123@\",\r\n    \"password\":\"123123123\"\r\n}','/api/v1/auth/signin','Email or password is not correct.','POST','{\"thai123\":\"123\"}','FAILED',NULL,NULL),(13,'29-11-2023:01:58:31',1,'29-11-2023:01:58:31','PostmanRuntime/7.35.0','{\r\n    \"email\":\"thai123@\",\r\n    \"password\":\"123123123\"\r\n}','/api/v1/auth/signin','Email or password is not correct.','POST','{\"thai123\":\"123\"}','FAILED',NULL,NULL),(14,'29-11-2023:01:58:33',1,'29-11-2023:01:58:33','PostmanRuntime/7.35.0','{\"email\":\"thai123@\",\"password\":\"\"}','/api/v1/auth/signin','Login SUCCESSFULY','POST','{\"thai123\":\"123\"}','SUCCESSFULLY',4,NULL),(15,'29-11-2023:01:59:23',1,'29-11-2023:01:59:23','PostmanRuntime/7.35.0','{\"email\":\"tha123i@\",\"password\":\"\"}','/api/v1/auth/signin','Login SUCCESSFULY','POST','{\"thai123\":\"123\"}','SUCCESSFULLY',2,NULL),(16,'29-11-2023:02:00:59',1,'29-11-2023:02:00:59','PostmanRuntime/7.35.0','{\"email\":\"tha123i@\",\"password\":\"\"}','/api/v1/auth/signin','Login SUCCESSFULY','POST','{\"thai123\":\"123\"}','SUCCESSFULLY',2,NULL),(17,'29-11-2023:02:02:21',1,'29-11-2023:02:02:21','PostmanRuntime/7.35.0','{\"userId\":1,\"productId\":1,\"listImageFiles\":[],\"name\":\"123\",\"location\":2,\"space\":1,\"food\":5,\"service\":3,\"price\":1}','/api/v1/reviews','Create Review SUCCESSFULY','POST','{\"productId\":\"1\",\"service\":\"3\",\"price\":\"1\",\"name\":\"123\",\"location\":\"2\",\"userId\":\"1\",\"space\":\"1\",\"food\":\"5\"}','SUCCESSFULLY',2,NULL),(18,'29-11-2023:02:02:52',1,'29-11-2023:02:02:52','PostmanRuntime/7.35.0','{\"userId\":1,\"productId\":1,\"listImageFiles\":[],\"name\":\"123\",\"location\":2,\"space\":1,\"food\":5,\"service\":3,\"price\":1}','/api/v1/reviews','Create Review SUCCESSFULY','POST','{\"productId\":\"1\",\"service\":\"3\",\"price\":\"1\",\"name\":\"123\",\"location\":\"2\",\"userId\":\"1\",\"space\":\"1\",\"food\":\"5\"}','SUCCESSFULLY',2,NULL),(19,'29-11-2023:02:05:22',1,'29-11-2023:02:05:22','PostmanRuntime/7.35.0','','/api/v1/areas','Validation failed for argument [0] in public org.springframework.http.ResponseEntity<com.cafe.website.payload.AreaDTO> com.cafe.website.controller.AreaController.updateArea(com.cafe.website.payload.AreaUpdateDTO,jakarta.servlet.http.HttpServletRequest) th','PATCH','{\"id\":\"1\",\"slug\":\"as\",\"status\":\"1\"}','FAILED',2,NULL),(20,'29-11-2023:02:05:35',1,'29-11-2023:02:05:35','PostmanRuntime/7.35.0','{\"id\":1,\"status\":1,\"name\":null,\"slug\":\"asasdasd\",\"imageFile\":null}','/api/v1/areas','Update Area SUCCESSFULY','PATCH','{\"id\":\"1\",\"slug\":\"asasdasd\",\"status\":\"1\"}','SUCCESSFULLY',2,NULL),(21,'29-11-2023:02:06:57',1,'29-11-2023:02:06:57','PostmanRuntime/7.35.0','{\"id\":1,\"status\":1,\"name\":null,\"slug\":\"asasdasd\",\"imageFile\":null}','/api/v1/areas','Update Area SUCCESSFULY','PATCH','{\"id\":\"1\",\"slug\":\"asasdasd\",\"status\":\"1\"}','SUCCESSFULLY',2,'Update Area'),(22,'29-11-2023:02:17:04',1,'29-11-2023:02:17:04','PostmanRuntime/7.35.0','{\"status\":1,\"name\":null,\"slug\":\"asasdasd\",\"imageFile\":null}','/api/v1/areas/id/1','Update Area SUCCESSFULY','PATCH','{\"id\":\"1\",\"slug\":\"asasdasd\",\"status\":\"1\"}','SUCCESSFULLY',2,'Update Area'),(23,'29-11-2023:02:17:36',1,'29-11-2023:02:17:36','PostmanRuntime/7.35.0','{\"id\":1} {\"status\":1,\"name\":null,\"slug\":\"asasdasd\",\"imageFile\":null}','/api/v1/areas/id/1','Update Area SUCCESSFULY','PATCH','{\"id\":\"1\",\"slug\":\"asasdasd\",\"status\":\"1\"}','SUCCESSFULLY',2,'Update Area'),(24,'29-11-2023:02:21:20',1,'29-11-2023:02:21:20','PostmanRuntime/7.35.0','','/api/v1/areas/id/1','Validation failed for argument [1] in public org.springframework.http.ResponseEntity<com.cafe.website.payload.AreaDTO> com.cafe.website.controller.AreaController.updateArea(java.lang.Integer,com.cafe.website.payload.AreaUpdateDTO,jakarta.servlet.http.Http','PATCH','{\"id\":\"1\",\"slug\":\"a\",\"status\":\"1\"}','FAILED',2,'No Specific'),(25,'29-11-2023:02:23:21',1,'29-11-2023:02:23:21','PostmanRuntime/7.35.0','','/api/v1/areas/id/1','Validation failed for argument [1] in public org.springframework.http.ResponseEntity<com.cafe.website.payload.AreaDTO> com.cafe.website.controller.AreaController.updateArea(java.lang.Integer,com.cafe.website.payload.AreaUpdateDTO,jakarta.servlet.http.Http','PATCH','{\"id\":\"1\",\"slug\":\"a\",\"status\":\"1\"}','FAILED',2,'No Specific'),(26,'29-11-2023:05:57:22',1,'29-11-2023:05:57:22','PostmanRuntime/7.35.0','','/api/v1/areas/id/1','size must be between 5 and 50; ','PATCH','{\"id\":\"1\",\"slug\":\"a\",\"status\":\"1\"}','FAILED',2,'No Specific'),(27,'16-12-2023:21:04:59',1,'16-12-2023:21:04:59','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','','/ws/info','When allowCredentials is true, allowedOrigins cannot contain the special value \"*\" since that cannot be set on the \"Access-Control-Allow-Origin\" response header. To allow credentials to a set of origins, list them explicitly or consider using \"allowedOrig','GET','{\"t\":\"1702735499533\"}','FAILED',NULL,'No Specific'),(28,'16-12-2023:23:31:03',1,'16-12-2023:23:31:03',NULL,'{\"name\":\"123123\",\"reviewId\":4,\"userId\":1,\"status\":1}',NULL,'Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(29,'16-12-2023:23:35:19',1,'16-12-2023:23:35:19',NULL,'{\"name\":\"123123\",\"reviewId\":4,\"userId\":1,\"status\":1}',NULL,'Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(30,'16-12-2023:23:51:51',1,'16-12-2023:23:51:51',NULL,'{\"name\":\"123123\",\"reviewId\":4,\"userId\":1,\"status\":1}',NULL,'Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(31,'16-12-2023:23:58:20',1,'16-12-2023:23:58:20','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"123123\",\"reviewId\":4,\"userId\":1,\"status\":1}','http://localhost:8080/ws/332/ndzguzxy/websocket','Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(32,'17-12-2023:13:51:23',1,'17-12-2023:13:51:23','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"123123\",\"reviewId\":4,\"userId\":1,\"status\":1}','http://localhost:8080/ws/822/zjvib3wo/websocket','Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(33,'17-12-2023:14:36:52',1,'17-12-2023:14:36:52','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"1312\",\"reviewId\":1,\"userId\":1,\"status\":1}','http://localhost:8080/ws/244/uztvljlq/websocket','Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(34,'17-12-2023:14:39:04',1,'17-12-2023:14:39:04','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"test\",\"reviewId\":1,\"userId\":1,\"status\":1}','http://localhost:8080/ws/626/bwobpfkw/websocket','Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(35,'17-12-2023:14:39:32',1,'17-12-2023:14:39:32','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"test2\",\"reviewId\":1,\"userId\":1,\"status\":1}','http://localhost:8080/ws/626/bwobpfkw/websocket','Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(36,'17-12-2023:14:39:51',1,'17-12-2023:14:39:51','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"đụ mẹ mày thái\",\"reviewId\":1,\"userId\":1,\"status\":1}','http://localhost:8080/ws/626/bwobpfkw/websocket','Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(37,'17-12-2023:14:40:00',1,'17-12-2023:14:40:00','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"Mày chửi thề không\",\"reviewId\":1,\"userId\":1,\"status\":1}','http://localhost:8080/ws/867/3rdnr23o/websocket','Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(38,'17-12-2023:14:58:57',1,'17-12-2023:14:58:57','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"undefined UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1}','http://localhost:8080/ws/050/btfoddzu/websocket','Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(39,'17-12-2023:14:59:56',1,'17-12-2023:14:59:56','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"Mày chửi thề không UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":13}','http://localhost:8080/ws/049/tbzbw2ng/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(40,'17-12-2023:15:00:09',1,'17-12-2023:15:00:09','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"1312 UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":9}','http://localhost:8080/ws/675/ftbpccbv/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(41,'17-12-2023:15:00:13',1,'17-12-2023:15:00:13','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"test UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":10}','http://localhost:8080/ws/675/ftbpccbv/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(42,'17-12-2023:15:00:17',1,'17-12-2023:15:00:17','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"123123 UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":1}','http://localhost:8080/ws/049/tbzbw2ng/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(43,'17-12-2023:15:00:18',1,'17-12-2023:15:00:18','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"test2 UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":11}','http://localhost:8080/ws/049/tbzbw2ng/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(44,'17-12-2023:15:11:51',1,'17-12-2023:15:11:51','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"123123 UPDATED UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":1}','http://localhost:8080/ws/092/xkl3umny/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(45,'17-12-2023:15:12:06',1,'17-12-2023:15:12:06','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"123123 UPDATED UPDATED UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":1}','http://localhost:8080/ws/092/xkl3umny/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(46,'17-12-2023:15:12:09',1,'17-12-2023:15:12:09','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"123123 UPDATED UPDATED UPDATED UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":1}','http://localhost:8080/ws/092/xkl3umny/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(47,'17-12-2023:15:12:11',1,'17-12-2023:15:12:11','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"123123 UPDATED UPDATED UPDATED UPDATED UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":1}','http://localhost:8080/ws/092/xkl3umny/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(48,'17-12-2023:15:12:12',1,'17-12-2023:15:12:12','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"123123 UPDATED UPDATED UPDATED UPDATED UPDATED UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":1}','http://localhost:8080/ws/092/xkl3umny/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(49,'17-12-2023:15:12:13',1,'17-12-2023:15:12:13','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"123123 UPDATED UPDATED UPDATED UPDATED UPDATED UPDATED UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":1}','http://localhost:8080/ws/092/xkl3umny/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(50,'17-12-2023:15:15:03',1,'17-12-2023:15:15:03','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"id\":1}','http://localhost:8080/ws/329/3zavy1ml/websocket','Delete Comment SUCCESSFULLY','Delete Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(51,'17-12-2023:15:15:14',1,'17-12-2023:15:15:14','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"id\":14}','http://localhost:8080/ws/329/3zavy1ml/websocket','Delete Comment SUCCESSFULLY','Delete Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(52,'17-12-2023:15:15:15',1,'17-12-2023:15:15:15','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"Mày chửi thề không UPDATED UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":13}','http://localhost:8080/ws/329/3zavy1ml/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(53,'17-12-2023:15:15:18',1,'17-12-2023:15:15:18','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"id\":13}','http://localhost:8080/ws/329/3zavy1ml/websocket','Delete Comment SUCCESSFULLY','Delete Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(54,'17-12-2023:15:16:47',1,'17-12-2023:15:16:47','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"id\":7}','http://localhost:8080/ws/586/lgbrih4a/websocket','Delete Comment SUCCESSFULLY','Delete Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(55,'17-12-2023:15:17:40',1,'17-12-2023:15:17:40','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"id\":9}','http://localhost:8080/ws/294/cdimzqtw/websocket','Delete Comment SUCCESSFULLY','Delete Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(56,'17-12-2023:15:54:20',1,'17-12-2023:15:54:20','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"213213\",\"reviewId\":1,\"userId\":1,\"status\":1}','http://localhost:8080/ws/923/s2ugfeid/websocket','Create Comment SUCCESSFULLY','Create Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(57,'17-12-2023:15:54:23',1,'17-12-2023:15:54:23','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"123123 UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":8}','http://localhost:8080/ws/923/s2ugfeid/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(58,'17-12-2023:15:54:25',1,'17-12-2023:15:54:25','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"test UPDATED UPDATED\",\"reviewId\":1,\"userId\":1,\"status\":1,\"id\":10}','http://localhost:8080/ws/923/s2ugfeid/websocket','Update Comment SUCCESSFULLY','Update Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(59,'17-12-2023:15:54:25',1,'17-12-2023:15:54:25','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"id\":8}','http://localhost:8080/ws/923/s2ugfeid/websocket','Delete Comment SUCCESSFULLY','Delete Comment SUCCESSFULLY',NULL,'SUCCESSFULLY',1,'Websocket'),(60,'21-12-2023:18:18:22',1,'21-12-2023:18:18:22','PostmanRuntime/7.36.0','{\"email\":\"tha123i@\",\"password\":\"\"}','/api/v1/auth/signin','Login SUCCESSFULY','POST','{\"thai123\":\"123\"}','SUCCESSFULLY',2,'Login'),(61,'21-12-2023:18:18:57',1,'21-12-2023:18:18:57','PostmanRuntime/7.36.0',NULL,'/api/v1/areas','No serializer found for class sun.nio.ch.ChannelInputStream and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.cafe.website.payload.AreaCreateDTO[\"imag','POST','{\"name\":\"123 asd 123\",\"slug\":\"test ting\"}','FAILED',2,'Create Area'),(62,'21-12-2023:18:26:21',1,'21-12-2023:18:26:21','PostmanRuntime/7.36.0','','/api/v1/areas','Slug is already exists!','POST','{\"name\":\"123 asd 123\",\"slug\":\"test ting\"}','FAILED',2,'No Specific'),(63,'21-12-2023:18:26:28',1,'21-12-2023:18:26:28','PostmanRuntime/7.36.0','','/api/v1/areas','Name is already exists!','POST','{\"name\":\"123 asd 123\",\"slug\":\"test ting 123\"}','FAILED',2,'No Specific'),(64,'21-12-2023:18:26:48',1,'21-12-2023:18:26:48','PostmanRuntime/7.36.0',NULL,'/api/v1/areas','No serializer found for class sun.nio.ch.ChannelInputStream and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.cafe.website.payload.AreaCreateDTO[\"imag','POST','{\"name\":\"123 as\",\"slug\":\"test ting 123\"}','FAILED',2,'Create Area'),(66,'21-12-2023:18:28:39',1,'21-12-2023:18:28:39','PostmanRuntime/7.36.0','','/api/v1/areas','Somethings went wrong. Please try again.','POST','{\"name\":\"123 asa\",\"slug\":\"test ting 1232\"}','FAILED',2,'No Specific'),(68,'21-12-2023:18:30:53',1,'21-12-2023:18:30:53','PostmanRuntime/7.36.0','','/api/v1/areas','No serializer found for class sun.nio.ch.ChannelInputStream and no properties discovered to create B','POST','{\"name\":\"123 asa\",\"slug\":\"test ting 1232\"}','FAILED',2,'No Specific'),(70,'21-12-2023:18:32:37',1,'21-12-2023:18:32:37','PostmanRuntime/7.36.0','','/api/v1/areas','No serializer found for class sun.nio.ch.ChannelInputStream and no properties discovered to create B','POST','{\"name\":\"123 asa\",\"slug\":\"test ting 1232\"}','FAILED',2,'No Specific'),(71,'21-12-2023:18:36:09',1,'21-12-2023:18:36:09','PostmanRuntime/7.36.0','{\"id\":6} 6','/api/v1/areas/id/6','Delete Area SUCCESSFULY','DELETE','{}','SUCCESSFULLY',2,'Delete Area'),(72,'21-12-2023:22:48:04',1,'21-12-2023:22:48:04','PostmanRuntime/7.36.0','{\"id\":7} 7','/api/v1/areas/id/7','Delete Area SUCCESSFULY','DELETE','{}','SUCCESSFULLY',2,'Delete Area'),(73,'21-12-2023:22:49:01',1,'21-12-2023:22:49:01','PostmanRuntime/7.36.0','{\"id\":7} 7','/api/v1/areas/id/7','Delete Area SUCCESSFULY','DELETE','{}','SUCCESSFULLY',2,'Delete Area'),(74,'21-12-2023:22:49:45',1,'21-12-2023:22:49:45','PostmanRuntime/7.36.0','{\"id\":7} 7','/api/v1/areas/id/7','Delete Area SUCCESSFULY','DELETE','{}','SUCCESSFULLY',2,'Delete Area'),(75,'21-12-2023:22:51:36',1,'21-12-2023:22:51:36','PostmanRuntime/7.36.0','{\"id\":7} 7','/api/v1/areas/id/7','Delete Area SUCCESSFULY','DELETE','{}','SUCCESSFULLY',2,'Delete Area'),(76,'21-12-2023:22:54:44',1,'21-12-2023:22:54:44','PostmanRuntime/7.36.0','{\"id\":7} 7','/api/v1/areas/id/7','Delete Area SUCCESSFULY','DELETE','{}','SUCCESSFULLY',2,'Delete Area'),(77,'21-12-2023:22:55:12',1,'21-12-2023:22:55:12','PostmanRuntime/7.36.0','{\"id\":7} 7','/api/v1/areas/id/7','Delete Area SUCCESSFULY','DELETE','{}','SUCCESSFULLY',2,'Delete Area'),(78,'22-12-2023:02:27:54',1,'22-12-2023:02:27:54','PostmanRuntime/7.36.0','{\"id\":7} 7','/api/v1/areas/id/7','Delete Area SUCCESSFULY','DELETE','{}','SUCCESSFULLY',2,'Delete Area'),(79,'22-12-2023:02:43:58',1,'22-12-2023:02:43:58','PostmanRuntime/7.36.0','{\"originalName\":\"6 - Copy.jpg\",\"contentType\":\"image/jpeg\",\"size\":283056,\"status\":0,\"name\":\"123 asa2\",\"slug\":\"test ting 12\",\"imageFile\":null}','/api/v1/areas','Create Area SUCCESSFULY','POST','{\"name\":\"123 asa2\",\"slug\":\"test ting 12\"}','SUCCESSFULLY',2,'Create Area'),(80,'22-12-2023:02:50:18',1,'22-12-2023:02:50:18','PostmanRuntime/7.36.0','','/api/v1/areas','Slug is already exists!','POST','{\"originalName\":\"ádasdqwezxczx\",\"name\":\"123 asa24\",\"slug\":\"test ting 123\"}','FAILED',2,'No Specific'),(81,'22-12-2023:02:50:27',1,'22-12-2023:02:50:27','PostmanRuntime/7.36.0','{\"originalName\":\"6 - Copy.jpg\",\"contentType\":\"image/jpeg\",\"size\":283056,\"status\":0,\"name\":\"123 asa24\",\"slug\":\"test ting 1234\",\"imageFile\":null}','/api/v1/areas','Create Area SUCCESSFULY','POST','{\"originalName\":\"ádasdqwezxczx\",\"name\":\"123 asa24\",\"slug\":\"test ting 1234\"}','SUCCESSFULLY',2,'Create Area'),(82,'22-12-2023:02:51:45',1,'22-12-2023:02:51:45','PostmanRuntime/7.36.0','','/api/v1/areas','Invalid status value only (0, 1, 2); ','POST','{\"originalName\":\"ádasdqwezxczx\",\"name\":\"123 asa24\",\"slug\":\"test ting 1234\",\"status\":\"3\"}','FAILED',2,'No Specific'),(83,'22-12-2023:22:16:30',1,'22-12-2023:22:16:30','PostmanRuntime/7.36.0','{\"id\":1} {\"originalName\":null,\"contentType\":null,\"size\":0,\"status\":1,\"name\":null,\"slug\":\"asasdasd\",\"imageFile\":null}','/api/v1/areas/id/1','Update Area SUCCESSFULY','PATCH','{\"id\":\"1\",\"slug\":\"asasdasd\",\"status\":\"1\"}','SUCCESSFULLY',2,'Update Area'),(84,'22-12-2023:22:18:55',1,'22-12-2023:22:18:55','PostmanRuntime/7.36.0','','/api/v1/areas/id/1123','Area not found with id : \'1123\'','PATCH','{\"id\":\"1\",\"slug\":\"asasdasd\",\"status\":\"1\"}','FAILED',2,'No Specific'),(85,'22-12-2023:22:20:09',1,'22-12-2023:22:20:09','PostmanRuntime/7.36.0','','/api/v1/areas/id/1','Invalid status value only (0, 1, 2); ','PATCH','{\"id\":\"1\",\"slug\":\"asasdasd\",\"status\":\"1123123\"}','FAILED',2,'No Specific'),(86,'22-12-2023:22:20:59',1,'22-12-2023:22:20:59','PostmanRuntime/7.36.0','','/api/v1/areas/id/1','Invalid status value only (0, 1, 2); ','PATCH','{\"id\":\"1\",\"slug\":\"asasdasd\",\"status\":\"1123123\"}','FAILED',2,'No Specific'),(87,'22-12-2023:22:22:24',1,'22-12-2023:22:22:24','PostmanRuntime/7.36.0','','/api/v1/areas/id/1','Invalid status value only (0, 1, 2); ','PATCH','{\"slug\":\"asasdasd\",\"status\":\"1123123\"}','FAILED',2,'No Specific'),(88,'22-12-2023:22:23:13',1,'22-12-2023:22:23:13','PostmanRuntime/7.36.0','{\"id\":1} {\"originalName\":null,\"contentType\":null,\"size\":0,\"status\":1,\"name\":null,\"slug\":\"asasdasd\",\"imageFile\":null}','/api/v1/areas/id/1','Update Area SUCCESSFULY','PATCH','{\"slug\":\"asasdasd\",\"status\":\"1\"}','SUCCESSFULLY',2,'Update Area'),(89,'22-12-2023:22:24:07',1,'22-12-2023:22:24:07','PostmanRuntime/7.36.0','{\"Update Area\":{\"id\":1,\"areaUpdateDto\":{\"originalName\":null,\"contentType\":null,\"size\":0,\"status\":1,\"name\":null,\"slug\":\"asasdasd\",\"imageFile\":null}}}','/api/v1/areas/id/1','Update Area SUCCESSFULY','PATCH','{\"slug\":\"asasdasd\",\"status\":\"1\"}','SUCCESSFULLY',2,'Update Area'),(90,'22-12-2023:23:55:10',1,'22-12-2023:23:55:10','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"wqe\",\"reviewId\":1,\"userId\":1,\"status\":1}','http://localhost:8080/ws/509/54qcbq1u/websocket','Create Comment SUCCESSFULLY','GET',NULL,'SUCCESSFULLY',1,'Websocket'),(91,'22-12-2023:23:55:10',1,'22-12-2023:23:55:10','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36','{\"name\":\"wqe\",\"reviewId\":1,\"userId\":1,\"status\":1}','http://localhost:8080/ws/509/54qcbq1u/websocket','Create Comment SUCCESSFULLY','GET',NULL,'SUCCESSFULLY',1,'Websocket'),(92,'22-12-2023:23:56:03',1,'22-12-2023:23:56:03','PostmanRuntime/7.36.0','{\"id\":1,\"areaUpdateDto\":{\"originalName\":null,\"contentType\":null,\"size\":0,\"status\":1,\"name\":null,\"slug\":\"asasdasd\",\"imageFile\":null}}','/api/v1/areas/id/1','Update Area SUCCESSFULY','PATCH','{\"slug\":\"asasdasd\",\"status\":\"1\"}','SUCCESSFULLY',2,'Update Area'),(93,'23-12-2023:00:20:28',1,'23-12-2023:00:20:28','PostmanRuntime/7.36.0','{\"id\":7}','/api/v1/areas/id/7','Delete Area SUCCESSFULY','DELETE','{}','SUCCESSFULLY',2,'Delete Area'),(94,'23-12-2023:13:57:30',1,'23-12-2023:13:57:30','PostmanRuntime/7.36.0','{\"email\":\"tha123i@\",\"password\":\"\"}','/api/v1/auth/signin','Login SUCCESSFULY','POST','{\"thai123\":\"123\"}','SUCCESSFULLY',2,'Login');
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menus`
--

DROP TABLE IF EXISTS `menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9fy52rm98bay0xyqw62u0n6n7` (`product_id`),
  CONSTRAINT `FK9fy52rm98bay0xyqw62u0n6n7` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menus`
--

LOCK TABLES `menus` WRITE;
/*!40000 ALTER TABLE `menus` DISABLE KEYS */;
INSERT INTO `menus` VALUES (1,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',1),(2,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',2),(3,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',3),(4,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',4),(5,'2023-10-13 10:52:38.1439222023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',5),(6,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',6),(7,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',7);
/*!40000 ALTER TABLE `menus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_saved`
--

DROP TABLE IF EXISTS `product_saved`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_saved` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdpk6c1j06drvlfkw22xdn5r32` (`user_id`,`product_id`),
  KEY `FKdqm6qm931xl5ymo9evtt1c3ei` (`product_id`),
  CONSTRAINT `FK4a5toqcu47m720d40dupiqpus` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKdqm6qm931xl5ymo9evtt1c3ei` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_saved`
--

LOCK TABLES `product_saved` WRITE;
/*!40000 ALTER TABLE `product_saved` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_saved` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `facebook` varchar(255) DEFAULT NULL,
  `is_waiting_delete` int NOT NULL DEFAULT '0',
  `latitude` int DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `longitude` int DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `outstanding` int NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `price_max` int NOT NULL,
  `price_min` int NOT NULL,
  `slug` varchar(255) NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`),
  UNIQUE KEY `UK_ostq1ec3toafnjok09y9l7dox` (`slug`),
  KEY `FKdb050tk37qryv15hd932626th` (`user_id`),
  CONSTRAINT `FKdb050tk37qryv15hd932626th` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','1',NULL,NULL,0,NULL,'1',NULL,'lizan 33',0,'1',0,0,'lizan-33',1),(2,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','1',NULL,NULL,0,NULL,'1',NULL,'lizan 3',0,'1',0,0,'lizan-333',1),(3,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','1',NULL,NULL,0,NULL,'1',NULL,'lizan 3333',0,'1',0,0,'lizan-3333',1),(4,'2023-10-13 10:52:38.143922',0,'2023-10-13 10:52:38.143922','1',NULL,NULL,0,NULL,'1',NULL,'lizan 33332',0,'1',0,0,'lizan-33332',1),(5,'2023-10-13 10:52:38.143922',0,'2023-10-13 10:52:38.143922','1',NULL,NULL,0,NULL,'1',NULL,'lizan 333322',0,'1',0,0,'lizan-333322',1),(6,'2023-10-13 10:52:38.143922',0,'2023-10-13 10:52:38.143922','1',NULL,NULL,0,NULL,'1',NULL,'lizan 3333222',0,'1',0,0,'lizan-3333222',1),(7,'2023-10-13 10:52:38.143922',0,'2023-10-13 10:52:38.143922','1',NULL,NULL,0,NULL,'1',NULL,'lizan 33332222',0,'1',0,0,'lizan-33332222',1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_areas`
--

DROP TABLE IF EXISTS `products_areas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products_areas` (
  `product_id` int NOT NULL,
  `area_id` int NOT NULL,
  KEY `FK6gtw7rhu1ffsho8thke55pg74` (`area_id`),
  KEY `FKgpe7sbyc1wnwaujbu47ths3uq` (`product_id`),
  CONSTRAINT `FK6gtw7rhu1ffsho8thke55pg74` FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`),
  CONSTRAINT `FKgpe7sbyc1wnwaujbu47ths3uq` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_areas`
--

LOCK TABLES `products_areas` WRITE;
/*!40000 ALTER TABLE `products_areas` DISABLE KEYS */;
INSERT INTO `products_areas` VALUES (1,1),(2,1),(3,1),(4,1),(4,2),(5,1),(5,2),(6,1),(6,2),(7,1),(7,2);
/*!40000 ALTER TABLE `products_areas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_conveniences`
--

DROP TABLE IF EXISTS `products_conveniences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products_conveniences` (
  `product_id` int NOT NULL,
  `convenience_id` int NOT NULL,
  KEY `FKkdjw7wmb6vp3tobb8b25ytq52` (`convenience_id`),
  KEY `FKjy0smlhq69xohayg034esxcal` (`product_id`),
  CONSTRAINT `FKjy0smlhq69xohayg034esxcal` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKkdjw7wmb6vp3tobb8b25ytq52` FOREIGN KEY (`convenience_id`) REFERENCES `conveniences` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_conveniences`
--

LOCK TABLES `products_conveniences` WRITE;
/*!40000 ALTER TABLE `products_conveniences` DISABLE KEYS */;
/*!40000 ALTER TABLE `products_conveniences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_kinds`
--

DROP TABLE IF EXISTS `products_kinds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products_kinds` (
  `product_id` int NOT NULL,
  `kind_id` int NOT NULL,
  KEY `FKn8y4a6x7kyaisvy8duk5iix0v` (`kind_id`),
  KEY `FKb9ybp6i6wtny9ej49p3a1mhln` (`product_id`),
  CONSTRAINT `FKb9ybp6i6wtny9ej49p3a1mhln` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKn8y4a6x7kyaisvy8duk5iix0v` FOREIGN KEY (`kind_id`) REFERENCES `kinds` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_kinds`
--

LOCK TABLES `products_kinds` WRITE;
/*!40000 ALTER TABLE `products_kinds` DISABLE KEYS */;
/*!40000 ALTER TABLE `products_kinds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_purposes`
--

DROP TABLE IF EXISTS `products_purposes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products_purposes` (
  `product_id` int NOT NULL,
  `purpose_id` int NOT NULL,
  KEY `FKs0gthjrj2tx11ga9yhnh4j2k6` (`purpose_id`),
  KEY `FK60t1rmm3qgqcgf47vg8csocu1` (`product_id`),
  CONSTRAINT `FK60t1rmm3qgqcgf47vg8csocu1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKs0gthjrj2tx11ga9yhnh4j2k6` FOREIGN KEY (`purpose_id`) REFERENCES `purposes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_purposes`
--

LOCK TABLES `products_purposes` WRITE;
/*!40000 ALTER TABLE `products_purposes` DISABLE KEYS */;
/*!40000 ALTER TABLE `products_purposes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purposes`
--

DROP TABLE IF EXISTS `purposes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purposes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purposes`
--

LOCK TABLES `purposes` WRITE;
/*!40000 ALTER TABLE `purposes` DISABLE KEYS */;
/*!40000 ALTER TABLE `purposes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratings`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ratings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `food` int NOT NULL,
  `location` int NOT NULL,
  `price` int NOT NULL,
  `service` int NOT NULL,
  `space` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
INSERT INTO `ratings` VALUES (1,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',5,2,1,3,1),(2,'2023-10-13 10:52:38.141421',1,'2023-10-13 10:52:38.141421',5,2,1,3,1),(3,'2023-10-13 10:52:52.079460',1,'2023-10-13 10:52:52.079460',5,2,1,3,1),(4,'2023-10-13 10:53:31.222736',1,'2023-10-13 10:53:31.222736',5,2,1,3,1),(5,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',5,2,1,3,1),(6,'25-11-2023:05:10:47',1,'25-11-2023:05:10:47',5,2,1,3,1),(7,'29-11-2023:02:02:21',1,'29-11-2023:02:02:21',5,2,1,3,1),(8,'29-11-2023:02:02:52',1,'29-11-2023:02:02:52',5,2,1,3,1);
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `product_id` int NOT NULL,
  `rating_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5y0vwhq7l7ons5l5tkg98bwei` (`rating_id`),
  KEY `FKpl51cejpw4gy5swfar8br9ngi` (`product_id`),
  KEY `FKcgy7qjc1r99dp117y9en6lxye` (`user_id`),
  CONSTRAINT `FK28jawss23ob5k6vrjmlmyokxb` FOREIGN KEY (`rating_id`) REFERENCES `ratings` (`id`),
  CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKpl51cejpw4gy5swfar8br9ngi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','123',1,1,1),(2,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','123',1,2,1),(3,'2023-10-13 10:52:52.081227',1,'2023-10-13 10:52:52.081227','123',1,3,1),(4,'2023-10-13 10:53:31.224671',1,'2023-10-13 10:53:31.224671','123',1,4,1),(5,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','123',1,5,1),(6,'25-11-2023:05:10:47',1,'25-11-2023:05:10:47','123',1,6,1),(7,'29-11-2023:02:02:21',1,'29-11-2023:02:02:21','123',1,7,1),(8,'29-11-2023:02:02:52',1,'29-11-2023:02:02:52','123',1,8,1);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','ROLE_ADMIN'),(2,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedules`
--

DROP TABLE IF EXISTS `schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedules` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `day_of_week` int DEFAULT NULL,
  `end_time` bigint DEFAULT NULL,
  `start_time` bigint DEFAULT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3pvw97y8ldg9823e34is4jutg` (`product_id`),
  CONSTRAINT `FK3pvw97y8ldg9823e34is4jutg` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedules`
--

LOCK TABLES `schedules` WRITE;
/*!40000 ALTER TABLE `schedules` DISABLE KEYS */;
INSERT INTO `schedules` VALUES (51,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',1,90000,28800,3),(52,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',6,90000,28800,3),(53,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',1,90000,28800,4),(54,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',2,90000,28800,4),(55,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',1,90000,28800,5),(56,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',2,90000,28800,5),(57,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',1,90000,28800,6),(58,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',2,90000,28800,6),(59,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',1,90000,28800,7),(60,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',2,90000,28800,7);
/*!40000 ALTER TABLE `schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tokens`
--

DROP TABLE IF EXISTS `tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tokens` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `expired` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `revoked` bit(1) NOT NULL,
  `token_type` enum('BEARER') DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bbw4ayf2n7v0hphv666bltynl` (`name`),
  KEY `FK2dylsfo39lgjyqml2tbe0b0ss` (`user_id`),
  CONSTRAINT `FK2dylsfo39lgjyqml2tbe0b0ss` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tokens`
--

LOCK TABLES `tokens` WRITE;
/*!40000 ALTER TABLE `tokens` DISABLE KEYS */;
INSERT INTO `tokens` VALUES (1,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNjkzNDc2NDk5LCJleHAiOjE2OTQwODEyOTl9.UjC6-0ChTpYZQiy8cNYFKadwplvxNhEYTpDlQdM1AH_5YuXJiw87qwUDUaupCg4S',_binary '','BEARER',1),(2,'2023-10-13 10:52:38.143922',1,'29-11-2023:01:58:33',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0aGFpMTIzQCIsImlhdCI6MTY5MzQ3Njc4MiwiZXhwIjoxNjk0MDgxNTgyfQ.Pf60YtqXyWGhOGW1zEm8-0UoOsxubJ_sSy5XfJXcUD8zsigjFqHzoyFR_1_udlXC',_binary '','BEARER',4),(3,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNjkzNTAwMDI1LCJleHAiOjE2OTQxMDQ4MjV9.f3U4Y-zELhNl43oPq7KBprVBERVDdhtYilV0PFQu2BqxFm5xenZG1ge7ZveFWIpi',_binary '','BEARER',1),(4,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNjk0MzYzMjI0LCJleHAiOjE2OTQ5NjgwMjR9.o54C-63mNg6F5laqX5R2yqYzQr3cue5_MB9pBtPt349IdwBnWDaI-ZE80iiQ8kMb',_binary '','BEARER',1),(5,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:14.932242',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNjk1OTI3OTEyLCJleHAiOjE2OTY1MzI3MTJ9.Iqs-liLa7M9SlKb1aU7qs4ewTRmOGx40KyajuN5LTeJ7YI7v-fiTstSVcJ2iclQo',_binary '','BEARER',1),(6,'2023-10-13 10:52:14.948292',1,'2023-10-13 10:52:38.143922',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNjk3MTY5MTM0LCJleHAiOjE2OTc3NzM5MzR9.nX-8D87jblCbU7DINGUbq_evaYSUrcdFUmBTvEn7PFGz05xgN8NghwqOMPlvf9EL',_binary '','BEARER',1),(7,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNjk3MTc4NDA3LCJleHAiOjE2OTc3ODMyMDd9.r1eRSISIDSfgWK2vdpNAa-qfiErFsRSWtOMJiDntsEKzsDdkmWcqb5mp5Num7dtr',_binary '','BEARER',1),(8,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNjk3MTg0ODkzLCJleHAiOjE2OTc3ODk2OTN9.SPfFqxXI5Jbcn25itKY-Ky4R81gHLq7hlBzMZOYFFPfwpZSjjIijPyfgNjFTa4vh',_binary '','BEARER',1),(9,'10-11-2023:02:06:36',1,'25-11-2023:05:10:30',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNjk5NTU2Nzk2LCJleHAiOjE3MDAxNjE1OTZ9.0aKeC2CpJyGD19c3-HDB2P1zjeoMsNAjJtYthviKGjP2ybWbQ7_4_YuU7OeUCf2T',_binary '','BEARER',1),(10,'25-11-2023:05:10:30',1,'25-11-2023:18:29:37',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNzAwODYzODMwLCJleHAiOjE3MDE0Njg2MzB9.LUjOq8PLpBbX6JHZdyQk1940gTaWb-T22jWXRdyvDn8K1EfNtpE-gU29OZVY-F-C',_binary '','BEARER',1),(11,'25-11-2023:18:29:37',1,'25-11-2023:18:29:58',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNzAwOTExNzc3LCJleHAiOjE3MDE1MTY1Nzd9.-QmGdduT98evG6SRPF_xUM2k9Rx6qim-2tdXDrC2_ZVcDheruZSg2ur85c3eq5cr',_binary '','BEARER',1),(12,'25-11-2023:18:29:58',1,'25-11-2023:18:33:24',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNzAwOTExNzk4LCJleHAiOjE3MDE1MTY1OTh9.I1pzxtDi-DIwMw7PzxXL3zPvwgJioX2BURfxEiFeeClLi7ODgKKgMwS1SFHdvDuF',_binary '','BEARER',1),(13,'25-11-2023:18:33:24',1,'25-11-2023:18:33:24',_binary '\0','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjMxMjNAIiwiaWF0IjoxNzAwOTEyMDA0LCJleHAiOjE3MDE1MTY4MDR9.TaHUpvTWRJJtovL9HT4Wqy2kwnqDHjScKBMyhNcWLED9HXjS-3nrSbhQlEvkdSTk',_binary '\0','BEARER',1),(14,'29-11-2023:01:58:33',1,'29-11-2023:01:58:33',_binary '\0','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0aGFpMTIzQCIsImlhdCI6MTcwMTE5NzkxMywiZXhwIjoxNzAxODAyNzEzfQ.FX99FBldvysix4RYc-CCpcco4AuqWOC2jmu5tqJTM6fQkYRCvcoeR2nXHuthKuu4',_binary '\0','BEARER',4),(15,'29-11-2023:01:59:23',1,'29-11-2023:02:00:59',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0aGExMjNpQCIsImlhdCI6MTcwMTE5Nzk2MywiZXhwIjoxNzAxODAyNzYzfQ.rBm9l2gvPC5tLNS_UqTyoSPyMcdpJJTukCD2ej1GPliUNZcRyR8LqG1pf2eljVga',_binary '','BEARER',2),(16,'29-11-2023:02:00:59',1,'21-12-2023:18:18:22',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0aGExMjNpQCIsImlhdCI6MTcwMTE5ODA1OSwiZXhwIjoxNzAxODAyODU5fQ.U83YRjfd2Rqyid4rXt-m7LqDuyX3VoR6GBoJ8Q7ouUshxxQ7u5ZdTQKf-_KjnbcL',_binary '','BEARER',2),(17,'21-12-2023:18:18:22',1,'23-12-2023:13:57:30',_binary '','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0aGExMjNpQCIsImlhdCI6MTcwMzE1NzUwMiwiZXhwIjoxNzAzNzYyMzAyfQ.s7joYc4RksYM5D2B_v9yqNyXYxWVHpK1E1zZJmUxEGNbhUW7-srbBBawM78lLieP',_binary '','BEARER',2),(18,'23-12-2023:13:57:30',1,'23-12-2023:13:57:30',_binary '\0','eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0aGExMjNpQCIsImlhdCI6MTcwMzMxNDY1MCwiZXhwIjoxNzAzOTE5NDUwfQ.z8u9QejIeKNW3QMn-eSlmyGjyX51xN4RIotZEnuVu1_5nKomZdmacQ0EdqBLcZx7',_binary '\0','BEARER',2);
/*!40000 ALTER TABLE `tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `updated_at` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `is_waiting_delete` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_3g1j96g94xpk3lpxl2qbl985x` (`name`),
  UNIQUE KEY `UK_82kvqupf9ax45leg8b0nofwh2` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2023-10-13 10:52:38.143922',1,'25-11-2023:23:13:01','','123123@','thai','$2a$10$fmwrW8yb.BP4mUTJP03nVOGna.BXkUjBKuYcgqFdJdB/AhhDz8kxi','','thai',0),(2,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','','tha123i@','thai123','$2a$10$fmwrW8yb.BP4mUTJP03nVOGna.BXkUjBKuYcgqFdJdB/AhhDz8kxi','','thai123',0),(4,'2023-10-13 10:52:38.143922',1,'2023-10-13 10:52:38.143922','','thai123@','thai123123','$2a$10$fmwrW8yb.BP4mUTJP03nVOGna.BXkUjBKuYcgqFdJdB/AhhDz8kxi','','thai123123',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  KEY `FK2o0jvgh89lemvvo17cbqvdxaa` (`user_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(1,2),(2,2),(2,1),(4,2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-24 18:44:14
