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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `email` varchar(45) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `homeAddress` varchar(60) NOT NULL,
  `homeLatitude` double NOT NULL,
  `homeLongitude` double NOT NULL,
  `currEvent` int DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `idUser_UNIQUE` (`idUser`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `FK_idEvent_User_idx` (`currEvent`),
  CONSTRAINT `FK_idEvent_User` FOREIGN KEY (`currEvent`) REFERENCES `event` (`idEvent`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'petar','aefefeaf','sdjicsidojcsiodvijsdi','aaaaaaaaaaaaaaaaaa','jciajciosdnvksdvi\n','sdjcsidvjojvkdjviofd',78,123.2,NULL),(3,'petar','aefefeaf','asdasdasdasdasda','sdffsd','jciajciosdnvksdvi\n','fsdfsdd',78,123.2,NULL),(7,'petar','aefefeaf','asdasdasdsasdas','sdffsdasdassd','jciajciosdnvksdvi\n','fsdfsdd',78,123.2,NULL),(10,'petar','aefefeaf','asdasdasdsasdadsadaas','sdffsdasdassdssss','jciajciosdnvksdvi\n','fsdfsdd',78,123.2,NULL),(21,'asdas','asvcasd','df@vsvd.com','fccsdv459','sdvsdvsd8v4','undefined',0,0,NULL),(22,'a','b','c@vad.df','abscadasdassss','dasdasdsadsss','Bulevar kralja Aleksandra 73, 11120 Belgrade, Serbia',44.80551,20.47611,NULL),(23,'petar','aefefeaf','asdasdasdsasdadaaasadaas','sdffsdasdassdsssssasa','jciajciosdnvksdvi\n','fsdfsdd',78,123.2,NULL),(24,'Vasilije','Stambolic','vasilije.stambolic@gmail.com','vstambolic','123456789','Ulica Jedrenska 1, 11120 Belgrade, Serbia',44.80063,20.48777,1),(25,'petar','aefefeaf','aaaaaaaaaaaaaa','sdffsdasdassdsssssasacas','aadasdasdasda\n','fsdfsdd',78,123.2,NULL),(26,'petar','aefefeaf','ssssvvv','sssssssssssfff','aadasdasdas','fsdfsdd',78,123.2,NULL),(27,'pe','csdcs','qq@aaac.com','vakimadafaki','qweqweqwe','Miokovci, Serbia',43.9435,20.2674,NULL),(28,'mile','šdžšć','vas@v.com','789789789','987987987','Rožanj, Bosnia and Herzegovina',44.5317,18.9327,NULL),(29,'aaaaaaaaaaaaaa','bbbbbbbbbbb','ccccccccc@ccccc.cc','USERNAME','PASSWORD','Bulevar kralja Aleksandra 73, 11120 Belgrade, Serbia',44.80551,20.47611,NULL),(30,'petar','aefefeaf','ssssvvvsas','sssssssssssfffsss','aadasdasdas','fsdfsdd',78,123.2,NULL),(31,'FIRSTNAME','LASTNAME','email@em.com','123456789','123456789','Bulevar kralja Aleksandra 73, 11120 Belgrade, Serbia',44.80551,20.47611,NULL),(33,'Vasilije','Stambolic','vstambolic@gmail.com','v_stambolic','123456789','Ulica Svetozara Markovića, 11000 Belgrade, Serbia',44.80259,20.46262,NULL),(34,'Milos','Bugaric','shomy99@gmail.com','shomy999','shomy999','Čačak, Serbia',43.8936,20.3494,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
