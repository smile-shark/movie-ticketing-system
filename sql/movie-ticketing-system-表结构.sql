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

drop database if exists movie_ticketing_system;

create database movie_ticketing_system character set utf8;

use movie_ticketing_system;

DROP TABLE IF EXISTS `advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `advertisement` (
  `advertisement_id` char(32) NOT NULL,
  `advertisement_image` text COMMENT '广告图片',
  `advertisement_url` text COMMENT '广告转跳的连接',
  `advertisement_end_time` datetime DEFAULT ((now() + interval 43200 minute)) COMMENT '广告到期的时间',
  PRIMARY KEY (`advertisement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='广告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `apply_for_new_movie`
--

DROP TABLE IF EXISTS `apply_for_new_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply_for_new_movie` (
  `apply_id` char(32) NOT NULL COMMENT '申请id',
  `platform_management_id` char(32) DEFAULT NULL COMMENT '管理id（处理人）',
  `cinema_management_id` char(32) DEFAULT NULL COMMENT '影院管理id',
  `apply_reason` text COMMENT '申请内容/理由',
  `movie_name` varchar(30) DEFAULT NULL,
  `movie_start_time` datetime DEFAULT NULL,
  `movie_duration` varchar(20) DEFAULT NULL,
  `director` varchar(20) DEFAULT NULL,
  `star` text,
  `introduction` text,
  `score` double(10,2) DEFAULT NULL,
  `poster_image` text,
  `issuing_region` varchar(20) DEFAULT NULL,
  `handle_state` int DEFAULT NULL COMMENT '处理状态（0：审核中，1：已同意，2：已驳回）',
  PRIMARY KEY (`apply_id`),
  KEY `FK_Reference_18` (`platform_management_id`),
  KEY `FK_Reference_19` (`cinema_management_id`),
  CONSTRAINT `FK_Reference_18` FOREIGN KEY (`platform_management_id`) REFERENCES `platform_management` (`platform_management_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_19` FOREIGN KEY (`cinema_management_id`) REFERENCES `cinema_management` (`cinema_management_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='申请新电影';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `apply_movie_type_middle`
--

DROP TABLE IF EXISTS `apply_movie_type_middle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply_movie_type_middle` (
  `apply_id` char(32) DEFAULT NULL COMMENT '申请id',
  `movie_type_id` char(32) DEFAULT NULL COMMENT '类型id',
  KEY `FK_Reference_20` (`apply_id`),
  KEY `FK_Reference_21` (`movie_type_id`),
  CONSTRAINT `FK_Reference_20` FOREIGN KEY (`apply_id`) REFERENCES `apply_for_new_movie` (`apply_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_21` FOREIGN KEY (`movie_type_id`) REFERENCES `movie_type` (`movie_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='申请类型中间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cinema`
--

DROP TABLE IF EXISTS `cinema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinema` (
  `cinema_id` char(32) NOT NULL COMMENT '影院id',
  `cinema_brand_id` char(32) DEFAULT NULL COMMENT '品牌id',
  `cinema_management_id` char(32) DEFAULT NULL COMMENT '影院管理id',
  `county_id` char(32) DEFAULT NULL COMMENT '区id',
  `cinema_name` varchar(30) DEFAULT NULL COMMENT '影院名称',
  `cinema_address` text COMMENT '地址',
  `cinema_phone` varchar(30) DEFAULT NULL COMMENT '联系电话',
  `cinema_email` text COMMENT '邮箱 ',
  `cinema_url` text COMMENT '网站连接',
  `cinema_points` double(10,2) DEFAULT '10.00' COMMENT '评分，初始创建的都是10分',
  `cinema_start_time` time DEFAULT NULL COMMENT '营业开始时间',
  `cinema_end_time` time DEFAULT NULL COMMENT '营业结束时间',
  PRIMARY KEY (`cinema_id`),
  KEY `FK_Reference_10` (`cinema_management_id`),
  KEY `FK_Reference_11` (`county_id`),
  KEY `FK_Reference_9` (`cinema_brand_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`cinema_management_id`) REFERENCES `cinema_management` (`cinema_management_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`county_id`) REFERENCES `county` (`county_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`cinema_brand_id`) REFERENCES `cinema_brand` (`cinema_brand_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='影院表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cinema_brand`
--

DROP TABLE IF EXISTS `cinema_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinema_brand` (
  `cinema_brand_id` char(32) NOT NULL COMMENT '品牌id',
  `cinema_brand_name` varchar(30) DEFAULT NULL COMMENT '品牌名称',
  `cinema_brand_logo` text COMMENT '品牌logo',
  PRIMARY KEY (`cinema_brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='影院品牌';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cinema_management`
--

DROP TABLE IF EXISTS `cinema_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinema_management` (
  `cinema_management_id` char(32) NOT NULL COMMENT '影院管理id',
  `cinema_management_email` text COMMENT '影院管理登录的邮箱',
  `cinema_management_password` varchar(32) DEFAULT NULL COMMENT '登录的密码',
  `cinema_management_profile_picture` text COMMENT '头像',
  PRIMARY KEY (`cinema_management_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='影院管理';
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='区县表';
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='省表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_verify`
--

DROP TABLE IF EXISTS `email_verify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_verify` (
  `email` text COMMENT '属于的邮箱',
  `email_verify_code` char(4) DEFAULT NULL COMMENT '验证码',
  `end_time` datetime DEFAULT NULL COMMENT '验证码到期时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='邮箱验证表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `freeze`
--

DROP TABLE IF EXISTS `freeze`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freeze` (
  `platform_management_id` char(32) DEFAULT NULL COMMENT '处理人',
  `user_id` char(32) DEFAULT NULL COMMENT '被冻结的用户',
  `freeze_reson` text COMMENT '冻结的理由',
  KEY `FK_Reference_7` (`platform_management_id`),
  KEY `FK_Reference_8` (`user_id`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`platform_management_id`) REFERENCES `platform_management` (`platform_management_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='账号冻结表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `global_profile_picture`
--

DROP TABLE IF EXISTS `global_profile_picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_profile_picture` (
  `global_profile_picture` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='头像表，初始头像会随机从其中选取';
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='市县表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mobile_display`
--

DROP TABLE IF EXISTS `mobile_display`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mobile_display` (
  `movie_id` char(32) DEFAULT NULL COMMENT '关联的电影',
  `mobile_display_image` text COMMENT '轮动的图片',
  `mobile_display_end_time` datetime DEFAULT ((now() + interval 43200 minute)) COMMENT '轮动到期的时间，默认为一个月',
  KEY `FK_Reference_6` (`movie_id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='轮动图表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `movie_id` char(32) NOT NULL COMMENT '电影id',
  `movie_name` varchar(30) DEFAULT NULL COMMENT '电影名称',
  `movie_start_time` datetime DEFAULT NULL COMMENT '电影上映的时间',
  `movie_duration` varchar(20) DEFAULT NULL COMMENT '电影时长',
  `director` varchar(20) DEFAULT NULL COMMENT '电影导演',
  `star` text COMMENT '电影主演',
  `introduction` text COMMENT '电影介绍',
  `score` double(10,2) DEFAULT '10.00' COMMENT '电影评分，默认满分',
  `poster_image` text COMMENT '列表展示的图片',
  `issuing_region` varchar(20) DEFAULT NULL COMMENT '电影发行的地区',
  PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='电影表';
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='电影类型表';
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='电影类型中间表';
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
  `slice_arrangement_id` char(32) DEFAULT NULL COMMENT '影院排片表id',
  `vote_all_price` double(10,2) DEFAULT NULL COMMENT '购票价格 ',
  `seats` json DEFAULT NULL COMMENT '座位（数组中存储坐标对象） ',
  `vote_pay_state` int DEFAULT '0' COMMENT '购票状态（0：未支付，1：已支付未使用，2：已支付已使用，3：支付超时，4，已取消支付）',
  `pay_timeout` datetime DEFAULT ((now() + interval 15 minute)) COMMENT '支付超时时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间，默认当前时间',
  `code_image` text COMMENT '验票二维码',
  PRIMARY KEY (`order_id`),
  KEY `FK_Reference_16` (`user_id`),
  KEY `FK_Reference_17` (`slice_arrangement_id`),
  CONSTRAINT `FK_Reference_16` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_17` FOREIGN KEY (`slice_arrangement_id`) REFERENCES `slice_arrangement` (`slice_arrangement_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `platform_management`
--

DROP TABLE IF EXISTS `platform_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `platform_management` (
  `platform_management_id` char(32) NOT NULL,
  `platform_management_account` varchar(20) DEFAULT NULL COMMENT '管理账号',
  `platform_management_password` varchar(20) DEFAULT NULL,
  `platform_management_name` varchar(20) DEFAULT NULL,
  `platform_management_profile_picture` text,
  PRIMARY KEY (`platform_management_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='平台管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reject`
--

DROP TABLE IF EXISTS `reject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reject` (
  `reject_id` char(32) NOT NULL,
  `apply_id` char(32) DEFAULT NULL COMMENT '对应的申请',
  `cinema_management_id` char(32) DEFAULT NULL COMMENT '处理人',
  `reject_context` text COMMENT '驳回的理由',
  PRIMARY KEY (`reject_id`),
  KEY `FK_Reference_22` (`apply_id`),
  KEY `FK_Reference_23` (`cinema_management_id`),
  CONSTRAINT `FK_Reference_22` FOREIGN KEY (`apply_id`) REFERENCES `apply_for_new_movie` (`apply_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_23` FOREIGN KEY (`cinema_management_id`) REFERENCES `cinema_management` (`cinema_management_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='驳回表';
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
  `screening_room_name` varchar(30) DEFAULT NULL COMMENT '放映厅名称',
  `row_all` int DEFAULT NULL COMMENT '总行数',
  `col_all` int DEFAULT NULL COMMENT '总列数',
  `layout` json DEFAULT NULL COMMENT '座位布局使用json格式数据的数组',
  PRIMARY KEY (`screening_room_id`),
  KEY `FK_Reference_12` (`cinema_id`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`cinema_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='放映厅';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `slice_arrangement`
--

DROP TABLE IF EXISTS `slice_arrangement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slice_arrangement` (
  `slice_arrangement_id` char(32) NOT NULL COMMENT '影院排片表id',
  `cinema_id` char(32) DEFAULT NULL,
  `movie_id` char(32) DEFAULT NULL,
  `screening_room_id` char(32) DEFAULT NULL,
  `slice_arrangement_start_time` datetime DEFAULT NULL COMMENT '放映时间',
  `vote_price` double(10,2) DEFAULT NULL COMMENT '票价',
  `layout` json DEFAULT NULL COMMENT '已购座位布局',
  `slice_arrangement_end_time` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`slice_arrangement_id`),
  KEY `FK_Reference_13` (`cinema_id`),
  KEY `FK_Reference_14` (`movie_id`),
  KEY `FK_Reference_15` (`screening_room_id`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`cinema_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`screening_room_id`) REFERENCES `screening_room` (`screening_room_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='影院排片表(放映时间在当前时间之前的都可以出售，已购座位布局复制于放映厅的座位布局，然后修改其中的座位的状态就行)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` char(32) NOT NULL,
  `user_email` text COMMENT '用户登录的邮箱',
  `user_password` char(32) DEFAULT NULL COMMENT '用户登录的密码',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `user_profile_picture` text COMMENT '用户头像',
  `user_points` int DEFAULT NULL COMMENT '用户积分',
  `user_level` int DEFAULT NULL COMMENT '用户等级',
  `user_state` int DEFAULT NULL COMMENT '用户账户状态 0：正常，1：冻结',
  `market_id` char(32) DEFAULT NULL COMMENT '用户所在地区',
  PRIMARY KEY (`user_id`),
  KEY `user_market_market_id_fk` (`market_id`),
  CONSTRAINT `user_market_market_id_fk` FOREIGN KEY (`market_id`) REFERENCES `market` (`market_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-09  8:42:54
