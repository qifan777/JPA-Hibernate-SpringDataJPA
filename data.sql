-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: jpa_hibernate_spring
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` int DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `house_number` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKda8tuywtf0gb6sedwk7la1pgi` (`user_id`),
  CONSTRAINT `FKda8tuywtf0gb6sedwk7la1pgi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

INSERT INTO `address` VALUES ('4626bc5c-9acf-4b53-856b-7bdb987795ed','2023-09-30 15:33:42.752402','2023-09-30 15:33:42.752402',0,'汉中市','丹景山路','城固县','29',NULL,NULL,NULL,'+86 13686863075','罗富财','1','陕西省'),('f0655fef-0f03-470a-91e5-a30a8293b7de','2023-09-30 15:33:42.734338','2023-09-30 15:33:42.734338',0,'南阳市','友谊路','方城县','976',NULL,NULL,NULL,'+86 13686863075','罗富财','1','河南省');

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` int DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `menu_type` int NOT NULL,
  `name` varchar(20) NOT NULL,
  `order_num` int NOT NULL DEFAULT '0',
  `parent_id` varchar(255) DEFAULT '0',
  `path` varchar(255) DEFAULT NULL,
  `perms` varchar(255) NOT NULL,
  `valid_status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_m05sb1hgsv38qjb4ksyh5eat2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` VALUES ('01cc59f3-68f4-4c40-b210-0e2613515df2','2023-07-19 20:54:16.000000','2023-07-19 20:54:16.000000',NULL,'permission','Lock',1,'权限管理',0,'0','permission','permission',1),('1b9525c8-8575-493c-8ece-d7ab33abee5f','2023-08-27 13:42:11.000000','2023-08-27 13:42:11.000000',NULL,'/product/product',NULL,2,'商品管理',0,'762d9ff3-56af-4f16-ae7b-69f6672a7139','/product/product','/product/product',1),('1e814cb8-9bd1-4b70-b6d1-20bac7f639f7','2023-07-30 11:45:46.000000','2023-07-30 11:45:46.000000',NULL,'coupon','Discount',2,'优惠券管理',0,'c25e2a70-0b73-4bb9-a8f4-126987009cea','/coupon','coupon.manage',1),('57f1b117-a63f-45c6-b879-a8af28a3a177','2023-08-26 19:24:22.000000','2023-08-26 19:28:45.000000',NULL,'/product/attribute-category',NULL,2,'属性类别',3,'762d9ff3-56af-4f16-ae7b-69f6672a7139','/product/attribute-category','/product/attribute-category',1),('5da4e269-d900-433b-8ae4-1822872afd41','2023-07-19 20:55:04.000000','2023-07-23 13:02:15.000000',NULL,'user','UserFilled',2,'用户管理',0,'01cc59f3-68f4-4c40-b210-0e2613515df2','/user','user',1),('719c61c2-b39f-49eb-8775-3912881ca223','2023-07-20 20:57:35.000000','2023-07-23 12:56:43.000000',NULL,'role','Avatar',2,'角色管理',2,'01cc59f3-68f4-4c40-b210-0e2613515df2','/role','role',1),('762d9ff3-56af-4f16-ae7b-69f6672a7139','2023-08-26 19:21:32.000000','2023-08-27 12:04:34.000000',NULL,'product','Goods',1,'商品中心',2,'0','product','product',1),('85e64e06-faff-41af-a91b-3ed498b838d8','2023-07-30 11:47:24.000000','2023-07-30 15:22:02.000000',NULL,'/coupon/activity','Tickets',2,'领券中心',1,'c25e2a70-0b73-4bb9-a8f4-126987009cea','/coupon/activity','coupon.activity',1),('c25e2a70-0b73-4bb9-a8f4-126987009cea','2023-07-29 19:43:40.000000','2023-07-30 17:25:33.000000',NULL,'/coupon/center','Ticket',1,'优惠券',1,'0','/coupon/center','coupon',1),('d34d1968-81a8-4bff-a38d-9b72da279d1a','2023-07-20 20:54:35.000000','2023-07-23 12:56:34.000000',NULL,'menu','Menu',2,'菜单管理',1,'01cc59f3-68f4-4c40-b210-0e2613515df2','/menu','menu',1),('e462492c-817d-4f41-8ede-f97a3a48233b','2023-08-26 19:28:07.000000','2023-08-26 19:28:07.000000',NULL,'/product/attribute',NULL,2,'属性',2,'762d9ff3-56af-4f16-ae7b-69f6672a7139','/product/attribute','/product/attribute',1),('e9dc4417-a08c-4d20-8fa0-13bcbe5042fa','2023-08-27 14:02:34.000000','2023-08-27 14:02:34.000000',NULL,'/product/product-category',NULL,2,'商品类别',2,'762d9ff3-56af-4f16-ae7b-69f6672a7139','/product/product-category','/product/product-category',1);

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` int DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  `valid_status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

INSERT INTO `role` VALUES ('1','2023-07-15 21:46:59.000000','2023-07-15 21:47:01.000000',NULL,'管理员',1),('b439c6fb-6fa5-4c2b-977f-33b0418b5461','2023-07-18 21:23:05.000000','2023-07-18 21:23:05.000000',NULL,'测试2',1);

--
-- Table structure for table `role_menu`
--

DROP TABLE IF EXISTS `role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menu` (
  `role_id` varchar(255) NOT NULL,
  `menu_id` varchar(255) NOT NULL,
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` int DEFAULT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `FKfg4e2mb2318tph615gh44ll3` (`menu_id`),
  CONSTRAINT `FKfg4e2mb2318tph615gh44ll3` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `FKqyvxw2gg2qk0wld3bqfcb58vq` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu`
--

INSERT INTO `role_menu` VALUES ('1','01cc59f3-68f4-4c40-b210-0e2613515df2','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','1b9525c8-8575-493c-8ece-d7ab33abee5f','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','1e814cb8-9bd1-4b70-b6d1-20bac7f639f7','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','57f1b117-a63f-45c6-b879-a8af28a3a177','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','5da4e269-d900-433b-8ae4-1822872afd41','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','719c61c2-b39f-49eb-8775-3912881ca223','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','762d9ff3-56af-4f16-ae7b-69f6672a7139','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','85e64e06-faff-41af-a91b-3ed498b838d8','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','c25e2a70-0b73-4bb9-a8f4-126987009cea','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','d34d1968-81a8-4bff-a38d-9b72da279d1a','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','e462492c-817d-4f41-8ede-f97a3a48233b','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL),('1','e9dc4417-a08c-4d20-8fa0-13bcbe5042fa','','0000-00-00 00:00:00.000000','0000-00-00 00:00:00.000000',NULL);

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` int DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `valid_status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

INSERT INTO `user` VALUES ('1','2023-07-12 15:08:02.000000','2023-09-21 08:44:50.000000',NULL,'https://my-community.oss-cn-qingdao.aliyuncs.com/20230921084447起凡.jpg',0,'起凡',1),('a2349c03-fa0a-49d9-9cb9-8cfb71a73b94','2023-08-30 21:58:25.000000','2023-09-22 21:59:47.000000',NULL,NULL,2,'默认用户',1);

--
-- Table structure for table `user_phone_password`
--

DROP TABLE IF EXISTS `user_phone_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_phone_password` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` int DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9yy68yo4aiwce6q8s4un602gm` (`phone_number`),
  CONSTRAINT `FKd6w8y4r41l2pvgj1grnrilj5f` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_phone_password`
--

INSERT INTO `user_phone_password` VALUES ('1','2023-07-15 21:48:24.000000','2023-07-15 21:48:26.000000',NULL,'$2a$10$nFVy5FGDp07PsLFWlcGq5O2tRuRN2SuvqwIOutmH7.zGRr7KFtuxa','13656987994'),('a2349c03-fa0a-49d9-9cb9-8cfb71a73b94','2023-09-22 21:59:47.000000','2023-09-22 21:59:47.000000',NULL,NULL,'13656987996');

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` VALUES ('1','1'),('1','b439c6fb-6fa5-4c2b-977f-33b0418b5461'),('a2349c03-fa0a-49d9-9cb9-8cfb71a73b94','b439c6fb-6fa5-4c2b-977f-33b0418b5461');

--
-- Table structure for table `user_wechat`
--

DROP TABLE IF EXISTS `user_wechat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_wechat` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `version` int DEFAULT NULL,
  `open_id` varchar(30) NOT NULL,
  `qr_code` varchar(255) DEFAULT NULL,
  `valid_status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t3dix7ds5j8iokj13p74kxhdt` (`open_id`),
  CONSTRAINT `FKnlyjocnjr3qy7bxk7fi3k6arc` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_wechat_chk_1` CHECK ((`valid_status` between 0 and 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_wechat`
--

INSERT INTO `user_wechat` VALUES ('a2349c03-fa0a-49d9-9cb9-8cfb71a73b94','2023-08-30 21:58:25.000000','2023-09-22 21:59:47.000000',NULL,'oEheF5USRu6Y3qWjpb3wJPBfuejw',NULL,NULL);
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-30 15:40:03
