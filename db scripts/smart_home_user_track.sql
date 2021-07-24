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
-- Table structure for table `user_track`
--

DROP TABLE IF EXISTS `user_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_track` (
  `idUser` int NOT NULL,
  `idTrack` varchar(45) NOT NULL,
  PRIMARY KEY (`idUser`,`idTrack`),
  KEY `FK_idTrack_idx` (`idTrack`),
  CONSTRAINT `FK_idTrack` FOREIGN KEY (`idTrack`) REFERENCES `track` (`spotifyURI`) ON UPDATE CASCADE,
  CONSTRAINT `FK_idUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_track`
--

LOCK TABLES `user_track` WRITE;
/*!40000 ALTER TABLE `user_track` DISABLE KEYS */;
INSERT INTO `user_track` VALUES (24,'0DiWol3AO6WpXZgp0goxAV'),(24,'0I309LjQtHTHKJOG4GW2NW'),(33,'0q1exbzIDRxqJ6VUKH3seg'),(24,'0QkWikH5Z3U0f79T9iuF6c'),(24,'10Nmj3JCNoMeBQ87uw5j8k'),(24,'18T3FLEYW0qjEyvPRBHZ7r'),(28,'18T3FLEYW0qjEyvPRBHZ7r'),(24,'1e5tmR9hgWAFcEkX7b8npl'),(24,'1m3paVx65imhvCjPx505Oy'),(27,'1Up57MvGxgkLcExqDBbMaM'),(27,'2AgYoBhg8op8cD1rrJwlEc'),(24,'2fwl1M9TpNcA6tr9U3dDn1'),(24,'2ptWzGBVvVSDZ9mJavA9hS'),(24,'2SAqBLGA283SUiwJ3xOUVI'),(24,'3rchFjeKZ0bJxO7EEdM3S5'),(31,'3tlXDvaNrrOmdvG0XVUOcv'),(34,'3wkDUqmF29t2f7amhqanMV'),(27,'43QlIT5Rf5otA24tINi2xS'),(24,'44ARj4IaCoOevEu1oy0z1v'),(33,'44ARj4IaCoOevEu1oy0z1v'),(31,'4hsQFFzYbi66nfEkCK6889'),(24,'4mCf3vQf7z0Yseo0RxAi3V'),(24,'4NGPwVcbHqiWKWfdwd5W9e'),(24,'4T4Xenxn8abz1BDAHmdDEG'),(24,'4Telts1bK5FEkHaMGrzCBE'),(24,'4txVNI0bTTOQZLww439cTg'),(24,'5ghIJDpPoe3CfHMGu71E6T'),(33,'5sEcwMeC3QDnSOWlyQyQ3E'),(24,'5SiKFhhUvZbO5VADIsmgAr'),(24,'5uUlOIGQxxj1KS4dWJwzoS'),(24,'6HdtoO63B620I9wWSRZWzJ'),(24,'6JyTvSOJS6nPdT7FLfBUdL'),(24,'6QhXQOpyYvbpdbyjgAqKdY'),(34,'6Srsvsx2nt780lgGKdcoCi'),(27,'6YAgKfxxK7xXHpavOoQsmF'),(24,'76iYt7EuOMeaE7oexIviJu'),(24,'7aYTycXhbuNixlUXO0oNBB'),(24,'7F0472ZCPeef1yXGv2eXou'),(34,'7r4BG3JPscI4ugSk40mgbi'),(24,'AAAAAAA'),(24,'BBBBB'),(24,'qqq'),(24,'vaaaaa'),(24,'vksjnjnkvjnkjn'),(24,'vksjnjnkvjnkjns');
/*!40000 ALTER TABLE `user_track` ENABLE KEYS */;
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
