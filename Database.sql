-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: progra3
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.04.1

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
-- Table structure for table `boat_placements`
--

DROP TABLE IF EXISTS `boat_placements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boat_placements` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player1_placements` varchar(110) NOT NULL,
  `player2_placements` varchar(110) NOT NULL,
  `game_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `game_placements_fk` (`game_id`),
  CONSTRAINT `game_placements_fk` FOREIGN KEY (`game_id`) REFERENCES `games` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boat_placements`
--

LOCK TABLES `boat_placements` WRITE;
/*!40000 ALTER TABLE `boat_placements` DISABLE KEYS */;
INSERT INTO `boat_placements` VALUES (1,'1:-99:1,0.1:-99:1,1.1:-99:7,0.1:-99:5,3.1:-99:7,1.','2:-99:0,9.2:-99:4,9.2:-99:0,8.2:-99:3,8.2:-99:6,8.',3),(2,'1:5:0,0.1:4:0,1.1:3:5,0.1:2:8,0.1:3:4,1.','2:4:6,5.2:3:0,3.2:3:5,0.2:2:2,5.2:5:5,9.',4);
/*!40000 ALTER TABLE `boat_placements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `games` (
  `id` int NOT NULL AUTO_INCREMENT,
  `moves` varchar(6000) NOT NULL,
  `player1_id` int NOT NULL,
  `player1_hits` int NOT NULL,
  `player1_blanks` int NOT NULL,
  `player1_move_count` int NOT NULL,
  `player2_id` int NOT NULL,
  `player2_hits` int NOT NULL,
  `player2_blanks` int NOT NULL,
  `player2_move_count` int NOT NULL,
  `player1_score` int NOT NULL,
  `player2_score` int NOT NULL,
  `winner_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `player1_id` (`player1_id`),
  KEY `player2_id` (`player2_id`),
  KEY `fk_games_users` (`winner_id`),
  CONSTRAINT `fk_games_users` FOREIGN KEY (`winner_id`) REFERENCES `users` (`id`),
  CONSTRAINT `games_ibfk_1` FOREIGN KEY (`player1_id`) REFERENCES `users` (`id`),
  CONSTRAINT `games_ibfk_2` FOREIGN KEY (`player2_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
INSERT INTO `games` VALUES (1,'nullmanu:A,0:blank.dino:E,3:blank.manu:D,6:blank.dino:A,0:hit.manu:G,3:blank.dino:B,0:hit.manu:F,4:blank.dino:C,0:hit.manu:E,7:hit.dino:D,0:hit.manu:J,8:blank.dino:E,0:hit.manu:C,3:blank.dino:F,0:hit.manu:F,2:hit.dino:G,0:hit.manu:F,0:blank.dino:H,0:hit.manu:J,7:blank.dino:I,0:hit.manu:A,7:blank.dino:J,0:hit.manu:C,7:hit.dino:B,1:blank.manu:D,9:blank.dino:C,1:hit.manu:E,9:blank.dino:D,1:hit.manu:E,8:blank.dino:E,1:hit.manu:G,8:blank.dino:F,1:hit.manu:G,7:blank.dino:G,1:hit.manu:G,5:blank.dino:H,1:hit.manu:H,4:blank.dino:I,1:hit.',10,3,16,19,15,17,2,19,893,893,15),(2,'manu:B,2:blank.dino:A,0:hit.manu:E,6:blank.dino:B,0:hit.manu:D,3:blank.dino:D,0:hit.manu:G,3:blank.dino:F,0:hit.manu:J,3:blank.dino:C,0:hit.manu:E,7:hit.dino:E,0:hit.manu:E,1:blank.dino:H,0:blank.manu:G,2:blank.dino:G,0:hit.manu:J,2:blank.dino:C,2:blank.manu:D,5:blank.dino:D,1:blank.manu:A,6:blank.dino:F,1:blank.manu:C,5:blank.dino:G,1:blank.manu:G,6:blank.dino:E,1:blank.manu:D,7:hit.dino:C,1:blank.manu:B,7:hit.dino:B,1:blank.manu:C,7:hit.dino:A,2:blank.manu:F,7:hit.dino:H,1:blank.manu:I,9:blank.dino:G,2:blank.manu:G,7:blank.dino:F,5:blank.manu:A,7:blank.dino:G,6:blank.manu:C,8:blank.dino:B,7:blank.manu:C,6:blank.dino:C,3:hit.manu:B,4:blank.dino:D,4:blank.manu:E,5:blank.dino:D,3:hit.manu:B,5:blank.dino:E,3:hit.manu:E,4:hit.dino:F,3:hit.manu:B,3:blank.dino:H,3:hit.manu:C,4:blank.dino:G,3:hit.manu:E,3:blank.dino:B,3:hit.manu:H,4:blank.dino:I,3:hit.manu:D,6:blank.dino:J,3:hit.manu:G,5:hit.dino:A,3:hit.',10,7,25,32,15,17,15,32,530,530,15),(3,'user:A,9:hit.Guess:C,6:blank.user:A,8:hit.Guess:F,8:blank.user:B,8:hit.Guess:E,8:blank.user:B,9:hit.Guess:F,7:blank.user:C,9:hit.Guess:D,8:blank.user:C,8:hit.Guess:C,9:blank.user:E,9:hit.Guess:H,8:blank.user:D,9:hit.Guess:I,9:blank.user:F,9:hit.Guess:F,9:blank.user:G,9:hit.Guess:H,7:blank.user:D,8:hit.Guess:J,7:blank.user:F,8:hit.Guess:I,5:blank.user:E,8:hit.Guess:G,6:blank.user:H,9:hit.Guess:H,9:blank.user:G,8:hit.Guess:G,9:blank.user:I,9:hit.Guess:I,8:blank.user:J,9:blank.Guess:J,8:blank.user:H,8:hit.',13,17,1,18,11,0,17,17,943,999,13),(4,'Guess:A,0:blank.dino:A,0:hit.Guess:B,0:blank.dino:B,0:hit.Guess:C,0:blank.dino:C,0:hit.Guess:D,0:blank.dino:D,0:hit.Guess:E,0:blank.dino:E,0:hit.Guess:F,0:hit.dino:F,0:hit.Guess:H,0:hit.dino:H,0:hit.Guess:G,0:hit.dino:G,0:hit.Guess:I,0:blank.dino:I,0:hit.Guess:J,0:blank.dino:J,0:hit.Guess:F,2:blank.dino:A,1:hit.Guess:B,2:blank.dino:B,1:hit.Guess:A,1:blank.dino:C,1:hit.Guess:B,4:blank.dino:D,1:hit.Guess:B,7:blank.dino:E,1:hit.Guess:C,5:hit.dino:F,1:hit.Guess:E,5:blank.dino:G,1:hit.',11,4,13,17,15,17,0,17,999,999,15);
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (10,'manu','$2a$10$aMmddm7DcqLnwzHNeDcX8OSRawvwM5yzkH0hYXtzPi4QOaDX8yNUG'),(11,'Guess','$2a$10$t1J2CvoaobgHyIeHmvQk0.O3cEYGPD4NMhr1lH2Ap5yeOWydyEmQW'),(12,'test','$2a$10$isK4Uf8tjqZpex2jUqdbzOrmKHDy6I8DUq7xTRl7C7yKlrJgyxtJy'),(13,'user','$2a$10$8cnYa418DgNhgVt1GKbiFeycjKEpSzjwd.DkmHUCX2xf1p980.BVK'),(14,'a','$2a$10$HufThNtOSVE6WWa6xRA7zewIRR.yVMrGk.oP5aiWTF.cpQBdeBraq'),(15,'dino','$2a$10$RMxrRpoTunMu7rRiqQ/s4.coAWZYfJQix5nLAAT24CdqqGFThIBmu');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-06  8:04:21
