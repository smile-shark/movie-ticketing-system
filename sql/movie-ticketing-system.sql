-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: movie_ticketing_system
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `advertisement`
--

DROP TABLE IF EXISTS `advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `advertisement` (
  `advertisement_id` char(32) NOT NULL,
  `advertisement_image` text,
  `advertisement_url` text,
  `advertisement_end_time` datetime DEFAULT ((now() + interval 43200 minute)),
  PRIMARY KEY (`advertisement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `apply_for_new_movie`
--

DROP TABLE IF EXISTS `apply_for_new_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply_for_new_movie` (
  `apply_id` char(32) NOT NULL,
  `platform_management_id` char(32) DEFAULT NULL,
  `cinema_management_id` char(32) DEFAULT NULL,
  `apply_reason` text,
  `movie_name` varchar(30) DEFAULT NULL,
  `movie_start_time` datetime DEFAULT NULL,
  `movie_duration` varchar(20) DEFAULT NULL,
  `director` varchar(20) DEFAULT NULL,
  `star` varchar(30) DEFAULT NULL,
  `introduction` text,
  `score` double(10,2) DEFAULT NULL,
  `poster_image` text,
  `issuing_region` varchar(20) DEFAULT NULL,
  `handle_state` int DEFAULT NULL,
  PRIMARY KEY (`apply_id`),
  KEY `FK_Reference_18` (`platform_management_id`),
  KEY `FK_Reference_19` (`cinema_management_id`),
  CONSTRAINT `FK_Reference_18` FOREIGN KEY (`platform_management_id`) REFERENCES `platform_management` (`platform_management_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_19` FOREIGN KEY (`cinema_management_id`) REFERENCES `cinema_management` (`cinema_management_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `apply_movie_type_middle`
--

DROP TABLE IF EXISTS `apply_movie_type_middle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply_movie_type_middle` (
  `apply_id` char(32) DEFAULT NULL,
  `movie_type_id` char(32) DEFAULT NULL,
  KEY `FK_Reference_20` (`apply_id`),
  KEY `FK_Reference_21` (`movie_type_id`),
  CONSTRAINT `FK_Reference_20` FOREIGN KEY (`apply_id`) REFERENCES `apply_for_new_movie` (`apply_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_21` FOREIGN KEY (`movie_type_id`) REFERENCES `movie_type` (`movie_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cinema`
--

DROP TABLE IF EXISTS `cinema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinema` (
  `cinema_id` char(32) NOT NULL,
  `cinema_brand_id` char(32) DEFAULT NULL,
  `cinema_management_id` char(32) DEFAULT NULL,
  `county_id` char(32) DEFAULT NULL,
  `cinema_name` varchar(30) DEFAULT NULL,
  `cinema_address` text,
  `cinema_phone` varchar(30) DEFAULT NULL,
  `cinema_email` text,
  `cinema_url` text,
  `cinema_points` double(10,2) DEFAULT NULL,
  `cinema_start_time` datetime DEFAULT NULL,
  `cinema_end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`cinema_id`),
  KEY `FK_Reference_10` (`cinema_management_id`),
  KEY `FK_Reference_11` (`county_id`),
  KEY `FK_Reference_9` (`cinema_brand_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`cinema_management_id`) REFERENCES `cinema_management` (`cinema_management_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`county_id`) REFERENCES `county` (`county_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`cinema_brand_id`) REFERENCES `cinema_brand` (`cinema_brand_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cinema_brand`
--

DROP TABLE IF EXISTS `cinema_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinema_brand` (
  `cinema_brand_id` char(32) NOT NULL,
  `cinema_brand_name` varchar(30) DEFAULT NULL,
  `cinema_brand_logo` text,
  PRIMARY KEY (`cinema_brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cinema_management`
--

DROP TABLE IF EXISTS `cinema_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinema_management` (
  `cinema_management_id` char(32) NOT NULL,
  `cinema_management_email` text,
  `cinema_management_password` varchar(20) DEFAULT NULL,
  `cinema_management_profile_picture` text,
  PRIMARY KEY (`cinema_management_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `county`
--

DROP TABLE IF EXISTS `county`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `county` (
  `county_id` char(32) NOT NULL,
  `market_id` char(32) DEFAULT NULL,
  `county_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`county_id`),
  KEY `FK_Reference_2` (`market_id`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`market_id`) REFERENCES `market` (`market_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `economize`
--

DROP TABLE IF EXISTS `economize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `economize` (
  `economize_id` char(32) NOT NULL,
  `economize_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`economize_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_verify`
--

DROP TABLE IF EXISTS `email_verify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_verify` (
  `email` text,
  `email_verify_code` char(4) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `freeze`
--

DROP TABLE IF EXISTS `freeze`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freeze` (
  `platform_management_id` char(32) DEFAULT NULL,
  `user_id` char(32) DEFAULT NULL,
  `freeze_reson` text,
  KEY `FK_Reference_7` (`platform_management_id`),
  KEY `FK_Reference_8` (`user_id`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`platform_management_id`) REFERENCES `platform_management` (`platform_management_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `global_profile_picture`
--

DROP TABLE IF EXISTS `global_profile_picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_profile_picture` (
  `global_profile_picture` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `market`
--

DROP TABLE IF EXISTS `market`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `market` (
  `market_id` char(32) NOT NULL,
  `economize_id` char(32) DEFAULT NULL,
  `market_name` char(30) DEFAULT NULL,
  PRIMARY KEY (`market_id`),
  KEY `FK_Reference_1` (`economize_id`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`economize_id`) REFERENCES `economize` (`economize_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mobile_display`
--

DROP TABLE IF EXISTS `mobile_display`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mobile_display` (
  `movie_id` char(32) DEFAULT NULL,
  `mobile_display_image` text,
  `mobile_display_end_time` datetime DEFAULT ((now() + interval 43200 minute)),
  KEY `FK_Reference_6` (`movie_id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `movie_id` char(32) NOT NULL,
  `movie_name` varchar(30) DEFAULT NULL,
  `movie_start_time` datetime DEFAULT NULL,
  `movie_duration` varchar(20) DEFAULT NULL,
  `director` varchar(20) DEFAULT NULL,
  `star` text,
  `introduction` text,
  `score` double(10,2) DEFAULT NULL,
  `poster_image` text,
  `issuing_region` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `movie_type`
--

DROP TABLE IF EXISTS `movie_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_type` (
  `movie_type_id` char(32) NOT NULL,
  `movie_type_name` char(20) DEFAULT NULL,
  PRIMARY KEY (`movie_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `movie_type_middle`
--

DROP TABLE IF EXISTS `movie_type_middle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie_type_middle` (
  `movie_type_id` char(32) DEFAULT NULL,
  `movie_id` char(32) DEFAULT NULL,
  KEY `FK_Reference_4` (`movie_type_id`),
  KEY `FK_Reference_5` (`movie_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`movie_type_id`) REFERENCES `movie_type` (`movie_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `order_id` char(32) NOT NULL,
  `user_id` char(32) DEFAULT NULL,
  `slice_arrangement_id` char(32) DEFAULT NULL,
  `vote_all_price` double(10,2) DEFAULT NULL,
  `seats` json DEFAULT NULL,
  `vote_pay_state` int DEFAULT '0',
  `pay_timeout` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `code_image` text,
  PRIMARY KEY (`order_id`),
  KEY `FK_Reference_16` (`user_id`),
  KEY `FK_Reference_17` (`slice_arrangement_id`),
  CONSTRAINT `FK_Reference_16` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_17` FOREIGN KEY (`slice_arrangement_id`) REFERENCES `slice_arrangement` (`slice_arrangement_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `platform_management`
--

DROP TABLE IF EXISTS `platform_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `platform_management` (
  `platform_management_id` char(32) NOT NULL,
  `platform_management_account` varchar(20) DEFAULT NULL,
  `platform_management_password` varchar(20) DEFAULT NULL,
  `platform_management_name` varchar(20) DEFAULT NULL,
  `platform_management_profile_picture` text,
  PRIMARY KEY (`platform_management_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reject`
--

DROP TABLE IF EXISTS `reject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reject` (
  `reject_id` char(32) NOT NULL,
  `apply_id` char(32) DEFAULT NULL,
  `cinema_management_id` char(32) DEFAULT NULL,
  `reject_context` text,
  PRIMARY KEY (`reject_id`),
  KEY `FK_Reference_22` (`apply_id`),
  KEY `FK_Reference_23` (`cinema_management_id`),
  CONSTRAINT `FK_Reference_22` FOREIGN KEY (`apply_id`) REFERENCES `apply_for_new_movie` (`apply_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_23` FOREIGN KEY (`cinema_management_id`) REFERENCES `cinema_management` (`cinema_management_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `screening_room`
--

DROP TABLE IF EXISTS `screening_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `screening_room` (
  `screening_room_id` char(32) NOT NULL,
  `cinema_id` char(32) DEFAULT NULL,
  `screening_room_name` varchar(30) DEFAULT NULL,
  `row_all` int DEFAULT NULL,
  `col_all` int DEFAULT NULL,
  `layout` json DEFAULT NULL,
  PRIMARY KEY (`screening_room_id`),
  KEY `FK_Reference_12` (`cinema_id`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`cinema_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `slice_arrangement`
--

DROP TABLE IF EXISTS `slice_arrangement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slice_arrangement` (
  `slice_arrangement_id` char(32) NOT NULL,
  `cinema_id` char(32) DEFAULT NULL,
  `movie_id` char(32) DEFAULT NULL,
  `screening_room_id` char(32) DEFAULT NULL,
  `slice_arrangement_start_time` datetime DEFAULT NULL,
  `vote_price` double(10,2) DEFAULT NULL,
  `layout` json DEFAULT NULL,
  PRIMARY KEY (`slice_arrangement_id`),
  KEY `FK_Reference_13` (`cinema_id`),
  KEY `FK_Reference_14` (`movie_id`),
  KEY `FK_Reference_15` (`screening_room_id`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`cinema_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`screening_room_id`) REFERENCES `screening_room` (`screening_room_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` char(32) NOT NULL,
  `user_email` text,
  `user_password` char(32) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_profile_picture` text,
  `user_points` int DEFAULT NULL,
  `user_level` int DEFAULT NULL,
  `user_state` int DEFAULT NULL COMMENT '0：正常，1：冻结',
  `market_id` char(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `user_market_market_id_fk` (`market_id`),
  CONSTRAINT `user_market_market_id_fk` FOREIGN KEY (`market_id`) REFERENCES `market` (`market_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-23 11:25:11
