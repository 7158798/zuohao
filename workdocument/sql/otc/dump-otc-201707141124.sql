-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: 130.252.100.119    Database: otc
-- ------------------------------------------------------
-- Server version	5.6.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_system_args`
--

DROP TABLE IF EXISTS `t_system_args`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_system_args` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `key_code` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '参数key',
  `value` varchar(2048) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '参数值',
  `description` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  `type` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '参数类型  预留',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='配置参数表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_args`
--

LOCK TABLES `t_system_args` WRITE;
/*!40000 ALTER TABLE `t_system_args` DISABLE KEYS */;
INSERT INTO `t_system_args` VALUES (1,'TRADE_TIME_LIMIT','90','交易限时',NULL),(2,'TRADE_ALL_LIMIT_TIMES','5','交易限时',NULL),(3,'OPERN_REALNAME','0','实名认证开关0关1开',NULL);
/*!40000 ALTER TABLE `t_system_args` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-14 11:24:59
