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
-- Table structure for table `alarm`
--

DROP TABLE IF EXISTS `alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alarm` (
  `idAlarm` int NOT NULL,
  `label` varchar(45) NOT NULL,
  `time` time NOT NULL,
  `repetitive` tinyint NOT NULL,
  `active` tinyint NOT NULL,
  `idTrack` varchar(45) NOT NULL,
  `idUser` int NOT NULL,
  `event` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`idAlarm`),
  UNIQUE KEY `idAlarm_UNIQUE` (`idAlarm`),
  KEY `FK_idUser_Alarm_idx` (`idUser`),
  KEY `FK_idTrack_Alarm_idx` (`idTrack`),
  KEY `FK_idEvent_Alarm_idx` (`event`),
  CONSTRAINT `FK_idEvent_Alarm` FOREIGN KEY (`event`) REFERENCES `event` (`idEvent`),
  CONSTRAINT `FK_idTrack_Alarm` FOREIGN KEY (`idTrack`) REFERENCES `track` (`spotifyURI`) ON UPDATE CASCADE,
  CONSTRAINT `FK_idUser_Alarm` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarm`
--

LOCK TABLES `alarm` WRITE;
/*!40000 ALTER TABLE `alarm` DISABLE KEYS */;
INSERT INTO `alarm` VALUES (1,'OdbranaProjekta','09:30:00',0,1,'0q1exbzIDRxqJ6VUKH3seg',33,20,'2021-02-27'),(2,'OdbranaProjekta - Reminder','09:24:00',0,1,'0q1exbzIDRxqJ6VUKH3seg',33,NULL,'2021-02-27'),(3,'Alarm','08:00:00',1,0,'5sEcwMeC3QDnSOWlyQyQ3E',33,NULL,NULL),(4,'EventLepaBrena','13:13:00',0,1,'6Srsvsx2nt780lgGKdcoCi',34,21,'2021-02-28'),(5,'EventLepaBrena - Reminder','13:01:00',0,1,'6Srsvsx2nt780lgGKdcoCi',34,NULL,'2021-02-28');
/*!40000 ALTER TABLE `alarm` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-26  4:33:23
