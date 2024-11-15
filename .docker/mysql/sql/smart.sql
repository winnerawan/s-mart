-- MySQL dump 10.13  Distrib 8.0.40, for macos14 (arm64)
--
-- Host: 127.0.0.1    Database: smart
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `$cache_panel`
--

DROP TABLE IF EXISTS `$cache_panel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `$cache_panel` (
  `key` varbinary(48) NOT NULL,
  `value` blob NOT NULL,
  `expiration` int unsigned NOT NULL,
  PRIMARY KEY (`key`),
  KEY `expiration` (`expiration`) USING BTREE,
  KEY `key` (`key`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `$cache_panel`
--

LOCK TABLES `$cache_panel` WRITE;
/*!40000 ALTER TABLE `$cache_panel` DISABLE KEYS */;
/*!40000 ALTER TABLE `$cache_panel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `$cache_service_api`
--

DROP TABLE IF EXISTS `$cache_service_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `$cache_service_api` (
  `key` varbinary(48) NOT NULL,
  `value` blob NOT NULL,
  `expiration` int unsigned NOT NULL,
  PRIMARY KEY (`key`),
  KEY `expiration` (`expiration`) USING BTREE,
  KEY `key` (`key`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `$cache_service_api`
--

LOCK TABLES `$cache_service_api` WRITE;
/*!40000 ALTER TABLE `$cache_service_api` DISABLE KEYS */;
/*!40000 ALTER TABLE `$cache_service_api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `$session_panel`
--

DROP TABLE IF EXISTS `$session_panel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `$session_panel` (
  `id` varbinary(64) NOT NULL,
  `user_id` varbinary(255) DEFAULT NULL,
  `ip_address` varbinary(48) DEFAULT NULL,
  `user_agent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `payload` blob NOT NULL,
  `last_activity` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `last_activity` (`last_activity`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `$session_panel`
--

LOCK TABLES `$session_panel` WRITE;
/*!40000 ALTER TABLE `$session_panel` DISABLE KEYS */;
/*!40000 ALTER TABLE `$session_panel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `$session_service_api`
--

DROP TABLE IF EXISTS `$session_service_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `$session_service_api` (
  `id` varbinary(64) NOT NULL,
  `user_id` varbinary(255) DEFAULT NULL,
  `ip_address` varbinary(48) DEFAULT NULL,
  `user_agent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `payload` blob NOT NULL,
  `last_activity` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `last_activity` (`last_activity`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `$session_service_api`
--

LOCK TABLES `$session_service_api` WRITE;
/*!40000 ALTER TABLE `$session_service_api` DISABLE KEYS */;
/*!40000 ALTER TABLE `$session_service_api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `description` varchar(128) DEFAULT NULL,
  `position` tinyint unsigned NOT NULL DEFAULT '1',
  `icon` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Sembako',NULL,1,NULL),(2,'Kebutuhuan Pokok',NULL,2,NULL),(3,'Snack',NULL,3,NULL),(4,'Obat',NULL,4,NULL),(5,'Rokok','',1,NULL),(7,'Gas',NULL,1,NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `default` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Ecer','2024-11-09 16:17:41',NULL,1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` binary(26) NOT NULL,
  `category_id` int unsigned NOT NULL,
  `sku` binary(30) NOT NULL,
  `name` varchar(192) NOT NULL,
  `description` varchar(128) DEFAULT NULL,
  `last_purchase_price` decimal(12,2) unsigned DEFAULT NULL,
  `image` varchar(128) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `temp_qty` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`sku`),
  UNIQUE KEY `uid_UNIQUE` (`id`),
  UNIQUE KEY `sku_UNIQUE` (`sku`),
  KEY `fk_1_idx` (`category_id`),
  CONSTRAINT `fk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_media`
--

DROP TABLE IF EXISTS `item_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_media` (
  `item_id` binary(26) NOT NULL,
  `category_id` int unsigned NOT NULL,
  `sku` binary(30) NOT NULL,
  `media_id` binary(26) NOT NULL,
  `media_user_id` bigint unsigned NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`item_id`,`media_id`) USING BTREE,
  KEY `index_1` (`created_user_id`) USING BTREE,
  KEY `index_2` (`created_datetime`) USING BTREE,
  KEY `media_user_id` (`media_user_id`,`media_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `item_media_ibfk_2` FOREIGN KEY (`created_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `item_media_ibfk_3` FOREIGN KEY (`media_user_id`, `media_id`) REFERENCES `media` (`user_id`, `id`) ON UPDATE CASCADE,
  CONSTRAINT `item_media_ibfk_4` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_media`
--

LOCK TABLES `item_media` WRITE;
/*!40000 ALTER TABLE `item_media` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_stock`
--

DROP TABLE IF EXISTS `item_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_stock` (
  `item_id` binary(26) NOT NULL,
  `sku` binary(30) NOT NULL,
  `category_id` int unsigned NOT NULL,
  `stock` int unsigned NOT NULL DEFAULT '0',
  `purchase_price` decimal(12,2) unsigned NOT NULL,
  `selling_price` decimal(12,2) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`item_id`,`sku`),
  KEY `index2` (`category_id`),
  CONSTRAINT `is_fk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `is_fk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_stock`
--

LOCK TABLES `item_stock` WRITE;
/*!40000 ALTER TABLE `item_stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media` (
  `user_id` bigint unsigned NOT NULL,
  `id` binary(26) NOT NULL,
  `media_path_id` bigint unsigned NOT NULL,
  `media_status_id` bigint unsigned NOT NULL,
  `media_mimetype_id` bigint unsigned NOT NULL,
  `file` varbinary(255) NOT NULL DEFAULT '',
  `size` bigint unsigned NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`user_id`,`id`),
  KEY `index_1` (`user_id`,`id`) USING BTREE,
  KEY `index_3` (`media_status_id`) USING BTREE,
  KEY `index_5` (`size`) USING BTREE,
  KEY `index_2` (`media_path_id`) USING BTREE,
  KEY `index_4` (`media_mimetype_id`) USING BTREE,
  KEY `index_7` (`created_user_id`) USING BTREE,
  KEY `index_8` (`created_datetime`) USING BTREE,
  FULLTEXT KEY `index_6` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_mimetype`
--

DROP TABLE IF EXISTS `media_mimetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_mimetype` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `mimetype` varbinary(128) NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_2` (`mimetype`) USING BTREE,
  KEY `index_1` (`id`) USING BTREE,
  KEY `index_3` (`created_user_id`) USING BTREE,
  KEY `index_4` (`created_datetime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_mimetype`
--

LOCK TABLES `media_mimetype` WRITE;
/*!40000 ALTER TABLE `media_mimetype` DISABLE KEYS */;
INSERT INTO `media_mimetype` VALUES (1,'PNG Image',_binary 'image/png',1,'2023-03-04 08:15:36.424303',0,NULL,NULL),(2,'JPEG Image',_binary 'image/jpeg',1,'2023-03-04 08:15:36.424303',0,NULL,NULL),(3,'Video MP4',_binary 'video/mp4',1,'2023-04-19 16:51:05.000000',0,NULL,'2023-04-19 17:45:36.114916'),(4,'WebP',_binary 'image/webp',1,'2023-06-03 06:29:45.347610',0,NULL,NULL);
/*!40000 ALTER TABLE `media_mimetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_path`
--

DROP TABLE IF EXISTS `media_path`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_path` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `media_path_status_id` bigint unsigned NOT NULL,
  `name` varbinary(32) NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `index_1` (`id`) USING BTREE,
  KEY `index_3` (`created_user_id`) USING BTREE,
  KEY `index_4` (`created_datetime`) USING BTREE,
  KEY `index_2` (`media_path_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_path`
--

LOCK TABLES `media_path` WRITE;
/*!40000 ALTER TABLE `media_path` DISABLE KEYS */;
INSERT INTO `media_path` VALUES (1,1,_binary 'images',1,'2023-03-04 08:15:36.449947',0,NULL,'2019-07-25 01:13:11.407262');
/*!40000 ALTER TABLE `media_path` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_path_status`
--

DROP TABLE IF EXISTS `media_path_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_path_status` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `index_1` (`id`) USING BTREE,
  KEY `index_2` (`created_user_id`) USING BTREE,
  KEY `index_3` (`created_datetime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_path_status`
--

LOCK TABLES `media_path_status` WRITE;
/*!40000 ALTER TABLE `media_path_status` DISABLE KEYS */;
INSERT INTO `media_path_status` VALUES (1,'Active',1,'2023-03-04 08:15:36.477583',0,NULL,NULL),(2,'Removed',1,'2023-03-04 08:15:36.477583',0,NULL,NULL),(3,'Inactive',1,'2023-03-04 08:15:36.477583',0,NULL,NULL);
/*!40000 ALTER TABLE `media_path_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_status`
--

DROP TABLE IF EXISTS `media_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_status` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `index_1` (`id`) USING BTREE,
  KEY `index_2` (`created_user_id`) USING BTREE,
  KEY `index_3` (`created_datetime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_status`
--

LOCK TABLES `media_status` WRITE;
/*!40000 ALTER TABLE `media_status` DISABLE KEYS */;
INSERT INTO `media_status` VALUES (1,'Active',1,'2023-03-04 08:15:36.501887',0,NULL,NULL),(2,'Removed',1,'2023-03-04 08:15:36.501887',0,NULL,NULL),(3,'Temporary',1,'2023-03-04 08:15:36.501887',0,NULL,NULL);
/*!40000 ALTER TABLE `media_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password`
--

DROP TABLE IF EXISTS `password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password` (
  `user_id` bigint unsigned NOT NULL,
  `password` blob NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`user_id`),
  KEY `index_1` (`user_id`) USING BTREE,
  KEY `index_2` (`created_user_id`) USING BTREE,
  KEY `index_3` (`created_datetime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password`
--

LOCK TABLES `password` WRITE;
/*!40000 ALTER TABLE `password` DISABLE KEYS */;
INSERT INTO `password` VALUES (2,_binary '$2a$12$dqWe8rzHgot0dVT647kJX.kLqTM3VA5ULgtR6X8RgiabeMxZVce7e',1,'2023-03-04 13:47:00.872163',0,NULL,NULL),(60,_binary '$2y$10$6BLyjAcDewrPWckgNPBH.e/nnGIypFHYpSqmUCojO18gPYi2or.ge',1,'2023-03-04 10:05:03.009228',0,NULL,NULL);
/*!40000 ALTER TABLE `password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_type`
--

DROP TABLE IF EXISTS `pay_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_type` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(72) NOT NULL,
  `icon` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_type`
--

LOCK TABLES `pay_type` WRITE;
/*!40000 ALTER TABLE `pay_type` DISABLE KEYS */;
INSERT INTO `pay_type` VALUES (1,'Tunai',NULL),(2,'QRIS',NULL),(3,'Transfer',NULL);
/*!40000 ALTER TABLE `pay_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase` (
  `id` binary(26) NOT NULL,
  `supplier_id` int unsigned NOT NULL,
  `total` decimal(12,2) NOT NULL DEFAULT '0.00',
  `description` varchar(192) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `p_fk_1_idx` (`supplier_id`),
  CONSTRAINT `p_fk_1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_item`
--

DROP TABLE IF EXISTS `purchase_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_item` (
  `purchase_id` binary(26) NOT NULL,
  `item_id` binary(26) NOT NULL,
  `price` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `qty` int unsigned NOT NULL DEFAULT '0',
  `subtotal` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`purchase_id`,`item_id`),
  KEY `index1` (`purchase_id`),
  KEY `index2` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_item`
--

LOCK TABLES `purchase_item` WRITE;
/*!40000 ALTER TABLE `purchase_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale`
--

DROP TABLE IF EXISTS `sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale` (
  `id` binary(26) NOT NULL,
  `customer_id` int unsigned NOT NULL DEFAULT '1',
  `tax_id` int unsigned NOT NULL DEFAULT '1',
  `pay_type_id` int unsigned NOT NULL DEFAULT '1',
  `subtotal` decimal(12,2) unsigned NOT NULL,
  `discount` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `total` decimal(12,2) unsigned NOT NULL,
  `total_purchase_price` decimal(12,2) unsigned NOT NULL,
  `total_profit` decimal(12,2) unsigned NOT NULL,
  `created_datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index2` (`customer_id`),
  KEY `index3` (`tax_id`),
  KEY `index4` (`pay_type_id`),
  CONSTRAINT `s_fk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `s_fk_2` FOREIGN KEY (`pay_type_id`) REFERENCES `pay_type` (`id`),
  CONSTRAINT `s_fk_3` FOREIGN KEY (`tax_id`) REFERENCES `tax` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale`
--

LOCK TABLES `sale` WRITE;
/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
/*!40000 ALTER TABLE `sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_item`
--

DROP TABLE IF EXISTS `sale_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_item` (
  `sale_id` binary(26) NOT NULL,
  `item_id` binary(26) NOT NULL,
  `category_id` int unsigned NOT NULL,
  `price` decimal(12,2) unsigned NOT NULL,
  `qty` int unsigned NOT NULL,
  `subtotal` decimal(12,2) unsigned NOT NULL,
  `purchase_price` decimal(12,2) unsigned NOT NULL,
  `profit` decimal(12,2) unsigned NOT NULL,
  PRIMARY KEY (`sale_id`,`item_id`),
  KEY `index2` (`category_id`),
  KEY `si_fk_2_idx` (`item_id`),
  CONSTRAINT `si_fk_1` FOREIGN KEY (`sale_id`) REFERENCES `sale` (`id`),
  CONSTRAINT `si_fk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `si_fk_3` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_item`
--

LOCK TABLES `sale_item` WRITE;
/*!40000 ALTER TABLE `sale_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `sale_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(192) NOT NULL,
  `address` varchar(192) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'Supplier 1',NULL,NULL,'2024-11-10 07:50:18');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax`
--

DROP TABLE IF EXISTS `tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tax` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `value` int unsigned NOT NULL DEFAULT '0',
  `active` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index2` (`active`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax`
--

LOCK TABLES `tax` WRITE;
/*!40000 ALTER TABLE `tax` DISABLE KEYS */;
INSERT INTO `tax` VALUES (1,'0%',0,1);
/*!40000 ALTER TABLE `tax` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `user_id` bigint unsigned NOT NULL,
  `token_type_id` bigint unsigned NOT NULL,
  `token_status_id` bigint unsigned NOT NULL,
  `prefix` varbinary(14) NOT NULL,
  `suffix` varbinary(14) NOT NULL,
  `unique` varbinary(32) NOT NULL,
  `created` datetime NOT NULL,
  `expired` datetime NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`user_id`,`token_type_id`,`prefix`,`suffix`,`unique`),
  KEY `index_1` (`user_id`,`token_type_id`,`prefix`,`suffix`,`unique`) USING BTREE,
  KEY `index_2` (`token_type_id`) USING BTREE,
  KEY `index_3` (`token_status_id`) USING BTREE,
  KEY `index_6` (`created_user_id`) USING BTREE,
  KEY `index_7` (`created_datetime`) USING BTREE,
  KEY `index_4` (`created`),
  KEY `index_5` (`expired`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (2,1,1,_binary '20241109175026',_binary '20271109175026',_binary 'ysYTA4K5UkxWw2XncLtGJC8jdmS1gDl7','2024-11-09 17:50:26','2027-11-09 17:50:26',2,'2024-11-09 17:50:26.035464',0,NULL,NULL),(2,1,1,_binary '20241109175049',_binary '20271109175049',_binary 'bLuGsBmtFEvlO20ZTqnkHMVi4CrIpXWa','2024-11-09 17:50:49','2027-11-09 17:50:49',2,'2024-11-09 17:50:49.778899',0,NULL,NULL),(2,1,1,_binary '20241109175057',_binary '20271109175057',_binary '2LKNqeIVthXuO5bjmHUa6zdGBQWDsyFE','2024-11-09 17:50:57','2027-11-09 17:50:57',2,'2024-11-09 17:50:57.949779',0,NULL,NULL),(2,1,1,_binary '20241109175749',_binary '20271109175749',_binary 'cZ4qBhoOwfj0dCEmHsInRuF1QrV7D5Ja','2024-11-09 17:57:49','2027-11-09 17:57:49',2,'2024-11-09 17:57:49.573693',0,NULL,NULL),(2,1,1,_binary '20241109175757',_binary '20271109175757',_binary '4X2D3USvAOY1QKB0dFR6ePZhGLC9wiqa','2024-11-09 17:57:57','2027-11-09 17:57:57',2,'2024-11-09 17:57:57.738690',0,NULL,NULL);
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token_status`
--

DROP TABLE IF EXISTS `token_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token_status` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `index_1` (`id`) USING BTREE,
  KEY `index_2` (`created_user_id`) USING BTREE,
  KEY `index_3` (`created_datetime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token_status`
--

LOCK TABLES `token_status` WRITE;
/*!40000 ALTER TABLE `token_status` DISABLE KEYS */;
INSERT INTO `token_status` VALUES (1,'Active',1,'2023-03-04 08:15:36.606116',0,NULL,NULL),(2,'Revoked',1,'2023-03-04 08:15:36.606116',0,NULL,NULL);
/*!40000 ALTER TABLE `token_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token_type`
--

DROP TABLE IF EXISTS `token_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token_type` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `index_1` (`id`) USING BTREE,
  KEY `index_2` (`created_user_id`) USING BTREE,
  KEY `index_3` (`created_datetime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token_type`
--

LOCK TABLES `token_type` WRITE;
/*!40000 ALTER TABLE `token_type` DISABLE KEYS */;
INSERT INTO `token_type` VALUES (1,'Bearer',1,'2023-03-04 08:15:36.630296',0,NULL,NULL),(2,'Session',1,'2023-03-04 08:15:36.630296',0,NULL,NULL);
/*!40000 ALTER TABLE `token_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_type_id` bigint unsigned NOT NULL,
  `user_status_id` bigint unsigned NOT NULL DEFAULT '1',
  `name` varchar(192) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_first_name` tinyint unsigned NOT NULL DEFAULT '1',
  `first_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_last_name` tinyint unsigned NOT NULL DEFAULT '0',
  `last_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `phone` varbinary(32) DEFAULT NULL,
  `created_user_id` bigint unsigned NOT NULL DEFAULT '1',
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `PK` (`id`) USING BTREE,
  KEY `user_type_id` (`user_type_id`),
  KEY `user_status_id` (`user_status_id`),
  KEY `created_user_id` (`created_user_id`),
  KEY `created_datetime` (`created_datetime`),
  KEY `is_first_name` (`is_first_name`),
  KEY `is_last_name` (`is_last_name`),
  FULLTEXT KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,1,'Root ',1,'Root',0,'',NULL,1,'2023-03-04 08:15:36.658397',0,62,'2023-05-04 17:37:30.453842'),(2,1,1,'Dienta Sherly',1,'Dienta',1,'Sherly',NULL,1,'2023-03-04 10:05:02.908764',0,62,'2024-11-09 17:57:43.872774');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_status`
--

DROP TABLE IF EXISTS `user_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_status` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `index_1` (`id`) USING BTREE,
  KEY `index_2` (`created_user_id`) USING BTREE,
  KEY `index_3` (`created_datetime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_status`
--

LOCK TABLES `user_status` WRITE;
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
INSERT INTO `user_status` VALUES (1,'Active',1,'2023-03-04 08:15:36.788860',0,NULL,NULL),(2,'Inactive',1,'2023-03-04 08:15:36.788860',0,NULL,'2023-03-09 02:21:36.811394'),(3,'Removed',1,'2023-03-04 08:15:36.788860',0,NULL,'2023-03-09 02:21:36.833362');
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_type` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  KEY `index_1` (`id`) USING BTREE,
  KEY `index_2` (`created_user_id`) USING BTREE,
  KEY `index_3` (`created_datetime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'System',1,'2023-03-04 08:15:36.841156',0,NULL,'2023-03-04 08:22:24.816117'),(2,'Owner',1,'2023-03-04 08:15:36.841156',0,NULL,'2023-03-04 08:22:24.825420');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `username`
--

DROP TABLE IF EXISTS `username`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `username` (
  `user_id` bigint unsigned NOT NULL,
  `username` varbinary(32) NOT NULL,
  `created_user_id` bigint unsigned NOT NULL,
  `created_datetime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `is_updated` tinyint unsigned NOT NULL DEFAULT '0',
  `updated_user_id` bigint unsigned DEFAULT NULL,
  `updated_datetime` datetime(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  `device` varchar(192) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `index_1` (`user_id`) USING BTREE,
  KEY `index_3` (`created_user_id`) USING BTREE,
  KEY `index_4` (`created_datetime`) USING BTREE,
  KEY `index_2` (`username`) USING BTREE,
  CONSTRAINT `u_fk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `username`
--

LOCK TABLES `username` WRITE;
/*!40000 ALTER TABLE `username` DISABLE KEYS */;
INSERT INTO `username` VALUES (1,_binary 'root',1,'2023-03-04 08:15:36.684579',0,NULL,NULL,NULL),(2,_binary 'test@gmail.com',1,'2023-03-04 10:05:02.929362',0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `username` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-15  0:59:35
