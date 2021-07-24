-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: smart_home
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `idEvent` int NOT NULL AUTO_INCREMENT,
  `label` varchar(45) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `idUser` int NOT NULL,
  `address` varchar(60) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`idEvent`),
  UNIQUE KEY `idEvent_UNIQUE` (`idEvent`),
  KEY `FK_idUser_idx` (`idUser`),
  CONSTRAINT `FK_idUser_Event` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'EVENT_0','2021-02-25','06:15:00',24,'Čačak, Serbia',43.8936,20.3494),(2,'EVENT_0','2021-02-25','06:15:00',24,'Čačak, Serbia',43.8936,20.3494),(3,'EVENT_0','2021-02-25','06:15:00',24,'Čačak, Serbia',43.8936,20.3494),(4,'EVENT_0','2021-02-25','06:15:00',24,'Čačak, Serbia',43.8936,20.3494),(5,'EVENT_0','2021-02-25','06:15:00',24,'Čačak, Serbia',43.8936,20.3494),(6,'Event','2021-02-25','08:00:00',24,'Home',43.7644,20.2473),(7,'Event','2021-02-25','08:00:00',24,'Ulica Jedrenska 1, 11120 Belgrade, Serbia',43.7644,20.2473),(8,'eee','2021-02-25','08:00:00',31,'Bulevar kralja Aleksandra 73, 11120 Belgrade, Serbia',43.7644,20.2473),(9,'Event','2021-02-25','08:00:00',24,'Čačak, Serbia',43.8936,20.3494),(15,'PARMENAC','2021-02-25','22:24:00',24,'Parmenac, Serbia',43.8953,20.2977),(16,'PARMENAC EVENT','2021-02-25','22:26:00',24,'Parmenac, Serbia',43.8953,20.2977),(17,'pm','2021-02-25','22:30:00',24,'Parmenac, Serbia',43.8953,20.2977),(18,'parme','2021-02-25','22:42:00',24,'Parmenac, Serbia',43.8953,20.2977),(19,'svavasv','2021-02-25','22:54:00',24,'Parmenac, Serbia',43.8953,20.2977),(20,'OdbranaProjekta','2021-02-27','09:30:00',33,'Bulevar kralja Aleksandra 73, 11120 Belgrade, Serbia',44.80551,20.47611),(21,'EventLepaBrena','2021-02-28','13:13:00',34,'Preljina, Serbia',43.9171,20.4083);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-26  4:33:24
