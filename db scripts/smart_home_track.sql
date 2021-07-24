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
-- Table structure for table `track`
--

DROP TABLE IF EXISTS `track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `track` (
  `spotifyURI` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `artist` varchar(45) NOT NULL,
  `album` varchar(45) NOT NULL,
  `year` int NOT NULL,
  PRIMARY KEY (`spotifyURI`),
  UNIQUE KEY `spotifyURI_UNIQUE` (`spotifyURI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track`
--

LOCK TABLES `track` WRITE;
/*!40000 ALTER TABLE `track` DISABLE KEYS */;
INSERT INTO `track` VALUES ('0DiWol3AO6WpXZgp0goxAV','One More Time','Daft Punk','Discovery',2001),('0I309LjQtHTHKJOG4GW2NW','Quit Shoving','Svetlana Fujii','Quit Shoving',2020),('0q1exbzIDRxqJ6VUKH3seg','Sara','Fleetwood Mac','Greatest Hits',1988),('0QkWikH5Z3U0f79T9iuF6c','Judas','Lady Gaga','Born This Way',2011),('10Nmj3JCNoMeBQ87uw5j8k','Dani California','Red Hot Chili Peppers','Stadium Arcadium',2006),('18T3FLEYW0qjEyvPRBHZ7r','Luda za tobom','Lepa Brena','Luda za tobom',1996),('1csdjnvw','Revolucija','stoja','vipper',2018),('1e5tmR9hgWAFcEkX7b8npl','Kilo dole kilo gore','Mile Kitic','Madjionicar',2017),('1m3paVx65imhvCjPx505Oy','Micaela','Sonora Carruseles, Luis Florez','The Best',2015),('1Up57MvGxgkLcExqDBbMaM','Stojanka','Stojček','Stojarka',2020),('2AgYoBhg8op8cD1rrJwlEc','Evropa','Stoja','Stoja',2002),('2fwl1M9TpNcA6tr9U3dDn1','Srce je moje violina','Lepa Lukic','Prolece, leto, jesen, zima',2013),('2ptWzGBVvVSDZ9mJavA9hS','boba','eevee','boba',2020),('2SAqBLGA283SUiwJ3xOUVI','Laugh Now Cry Later (feat. Lil Durk)','Drake, Lil Durk','Laugh Now Cry Later (feat. Lil Durk)',2020),('3rchFjeKZ0bJxO7EEdM3S5','Paparazzi','Kim Dracula','Paparazzi',2020),('3tlXDvaNrrOmdvG0XVUOcv','Polly','Nirvana','Nevermind (Remastered)',1991),('3wkDUqmF29t2f7amhqanMV','Kad Hodaš','Riblja Corba','Večeras Vas Zabavljaju Muzičari Koji Piju!',1984),('43QlIT5Rf5otA24tINi2xS','Nema nista majko od tvoga veselja','Šaban Šaulić','Šaban Šaulić',2001),('44ARj4IaCoOevEu1oy0z1v','The Outlaw Torn','Metallica','Load',1996),('4hsQFFzYbi66nfEkCK6889','Trofrtaljka','Mica Trofrtaljka','The Best Of',2018),('4mCf3vQf7z0Yseo0RxAi3V','Desperado','Rihanna','ANTI (Deluxe)',2016),('4NGPwVcbHqiWKWfdwd5W9e','Minut','Olja Karleusa','Olja Karleusa',2010),('4T4Xenxn8abz1BDAHmdDEG','Luzeru','Jadranka Barjaktarovic','Luzeru',2017),('4Telts1bK5FEkHaMGrzCBE','Majko','Darko Lazic','Majko',2019),('4txVNI0bTTOQZLww439cTg','Janko Janosik','Young Star Boy','Mixtape No.1',2020),('5ghIJDpPoe3CfHMGu71E6T','Smells Like Teen Spirit','Nirvana','Nevermind (Remastered)',1991),('5sEcwMeC3QDnSOWlyQyQ3E','Moth Into Flame','Metallica','Hardwired…To Self-Destruct',2016),('5SiKFhhUvZbO5VADIsmgAr','Mesaj mala','Rada Manojlovic, Sasa Matic','Rada Manojlovic',2011),('5uUlOIGQxxj1KS4dWJwzoS','Breña','A Perfect Circle','Mer De Noms',2000),('6HdtoO63B620I9wWSRZWzJ','Sladjana','Senidah','Bez Tebe',2019),('6JyTvSOJS6nPdT7FLfBUdL','Nemoj Mnogo Da Me Volis','Sloba Radanovic, Dara Bubamara','Nemoj Mnogo Da Me Volis',2020),('6QhXQOpyYvbpdbyjgAqKdY','Cecilia','Simon & Garfunkel','Bridge Over Troubled Water',1970),('6Srsvsx2nt780lgGKdcoCi','Čačak','Lepa Brena','Lepa Brena - Hitovi',2017),('6YAgKfxxK7xXHpavOoQsmF','Popovitch','Ace Hood','Mr. Hood',2020),('76iYt7EuOMeaE7oexIviJu','Kojae','Tohi','Kojae',2021),('7aYTycXhbuNixlUXO0oNBB','Kavasaki','Rasta','Kavasaki',2014),('7F0472ZCPeef1yXGv2eXou','Neka Gori Sve','MC Yankoo, Petar Mitić','Neka Gori Sve',2018),('7r4BG3JPscI4ugSk40mgbi','Tisina','Bajaga & Instruktori','Prodavnica tajni',1988),('aaa','Too Late','The weeknd','after hours',2018),('AAAAAAA','Too Late','The weeknd','after hours',2018),('BBBBB','Too Late','The weeknd','after hours',2018),('ddd','Too Late','The weeknd','after hours',2018),('dddddd','Too Late','The weeknd','after hours',2018),('qqq','Too Late','The weeknd','after hours',2018),('qqqrrrrrqrwvfg','Too Late','The weeknd','after hours',2018),('vaaaaa','Too Late','The weeknd','after hours',2018),('vksjnjnkvjnkjn','Too Late','The weeknd','after hours',2018),('vksjnjnkvjnkjns','Too Late','The weeknd','after hours',2018),('vksjnjnkvjnkjnss','Too Late','The weeknd','after hours',2018),('vksjnjnkvjnkjnssgg','Too Late','The weeknd','after hours',2018);
/*!40000 ALTER TABLE `track` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-26  4:33:22
