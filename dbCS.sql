CREATE DATABASE  IF NOT EXISTS `checkskills` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `checkskills`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: checkskills
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `correct` tinyint DEFAULT NULL,
  `question_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_question_id_idx` (`question_id`),
  CONSTRAINT `fk_answer_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=436 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (401,'Mamie Nova',0,353),(402,'Apple',0,353),(403,'Facebook',0,353),(404,'Amazon',0,353),(405,'Google',1,353),(406,'Un language de programmation',0,354),(407,'Une librairie',0,354),(408,'Un framework',1,354),(409,'Il désigne l’expédition en masse d’informations publicitaires par voie électronique. Réservé à l’origine aux courriers promotionnels physiques, le terme s’applique aujourd’hui indifféremment aux publipostages physiques et électroniques (e-mailing).',0,355),(410,'Il désigne l\'utilisation de liens commerciaux ou publicitaires sur les moteurs de recherche. Dans le cadre du SEO, l\'espace publicitaire est principalement acheté aux enchères et à la performance au clic en ciblant des requêtes précises grâce à des mots clés.',0,355),(411,'Mettre en place une stratégie de visibilité web pour faire en sorte que votre site soit le meilleur pour répondre à l\'intention de recherche de vos visiteurs.',1,355),(412,'Service Establishment Opérator',0,356),(413,'Social Engineering Organisation ',0,356),(414,'Search Engine Optimization',1,356),(415,'gigaoctet (Go)\n ',1,357),(416,'octets',0,357),(417,'kilo-octet (Ko)',0,357),(418,'de sortie',1,358),(419,'d\'entrée',0,358),(420,'d\'entrée/sortie',0,358),(421,'un disque dur',1,359),(422,'la carte mère',0,359),(423,'la souris',0,359),(424,'le microprocesseur\n',1,360),(425,'la mémoire ROM.',0,360),(426,'la mémoire RAM',0,360),(427,' un ordinateur',0,361),(428,' la science du traitement des connaissances',0,361),(429,' la science du traitement rationnel de l\'information',1,361),(430,'a1',0,362),(431,'a2',0,362),(432,'a3',0,362),(433,'b3',0,363),(434,'b2',1,363),(435,'b1',0,363);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidate` (
  `id` int NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate`
--

LOCK TABLES `candidate` WRITE;
/*!40000 ALTER TABLE `candidate` DISABLE KEYS */;
/*!40000 ALTER TABLE `candidate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan` (
  `id` int NOT NULL,
  `credits` int DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES (1,5,'oneshot','Economique',5),(2,30,'oneshot','Standard',20),(3,200,'monthly','Entreprise',100);
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qcm`
--

DROP TABLE IF EXISTS `qcm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qcm` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `note` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `credits` int DEFAULT NULL,
  `difficulty` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `visible` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_users_id_idx` (`user_id`),
  CONSTRAINT `fk_qcm_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qcm`
--

LOCK TABLES `qcm` WRITE;
/*!40000 ALTER TABLE `qcm` DISABLE KEYS */;
INSERT INTO `qcm` VALUES (95,'Angular',0,20,8,'EASY','Maecenas hendrerit in est vitae suscipit. Cras fringilla, quam quis tincidunt lobortis, lacus turpis faucibus erat, id iaculis sapien odio a magna. Aenean at euismod mi. Donec vel dolor pellentesque augue vulputate lacinia. Praesent congue dolor tellus, euismod ullamcorper metus luctus eu. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin dictum interdum ante, vel interdum lacus cursus vel.',1),(96,'Référencement SEO',0,20,5,'EASY','Afringilla, quam quis tincidunt lobortis, lacus turpis faucibus erat, id iaculis sapien odio a magna. Aenean at euismod mi. Donec vel dolor pellentesque augue vulputate lacinia. Praesent congue dolor tellus, euismod ullamcorper metus luctus eu. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin dictum interdum ante, vel interdum lacus cursus vel.',1),(97,'Culture générale : l\'informatique',0,20,5,'NORMAL','Rest vitae suscipit. Cras fringilla, quam quis tincidunt lobortis, lacus turpis faucibus erat, id iaculis sapien odio a magna. Aenean at euismod mi. Donec vel dolor pellentesque augue Proin dictum interdum ante, vel interdum lacus cursus vel.',1),(98,'Test - aaa',0,20,NULL,'HARD',NULL,1),(99,'Test - bbb',0,20,NULL,'NORMAL',NULL,1);
/*!40000 ALTER TABLE `qcm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qcm_history`
--

DROP TABLE IF EXISTS `qcm_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qcm_history` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `qcm_id` int unsigned DEFAULT NULL,
  `success` int DEFAULT NULL,
  `date_used` datetime DEFAULT NULL,
  `date_bought` datetime DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `employer_id` int DEFAULT NULL,
  `candidate_id` int DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_qcm_id_idx` (`qcm_id`),
  KEY `fk_qcm_history_users_employer_idx` (`employer_id`),
  KEY `fk_qcm_history_candidate_idx` (`candidate_id`),
  CONSTRAINT `fk_qcm_history_candidate` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`),
  CONSTRAINT `fk_qcm_history_qcm` FOREIGN KEY (`qcm_id`) REFERENCES `qcm` (`id`),
  CONSTRAINT `fk_qcm_history_users_employer` FOREIGN KEY (`employer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qcm_history`
--

LOCK TABLES `qcm_history` WRITE;
/*!40000 ALTER TABLE `qcm_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `qcm_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qcm_sector`
--

DROP TABLE IF EXISTS `qcm_sector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qcm_sector` (
  `qcm_id` int unsigned DEFAULT NULL,
  `sector_id` int unsigned DEFAULT NULL,
  KEY `fk_qcm_sector_qcm_idx` (`qcm_id`),
  KEY `fk_qcm_sector_sector_idx` (`sector_id`),
  CONSTRAINT `fk_qcm_job_job` FOREIGN KEY (`sector_id`) REFERENCES `sector` (`id`),
  CONSTRAINT `fk_qcm_job_qcm` FOREIGN KEY (`qcm_id`) REFERENCES `qcm` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qcm_sector`
--

LOCK TABLES `qcm_sector` WRITE;
/*!40000 ALTER TABLE `qcm_sector` DISABLE KEYS */;
INSERT INTO `qcm_sector` VALUES (95,1),(95,2),(95,5),(95,8),(95,10),(96,1),(96,3),(96,4),(96,10),(97,1),(97,10),(98,1),(98,2),(98,5),(98,6),(98,8),(98,10),(98,11),(98,12),(99,1),(99,2),(99,4),(99,5),(99,9),(99,10);
/*!40000 ALTER TABLE `qcm_sector` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `note` int DEFAULT NULL,
  `total_true` int DEFAULT NULL,
  `total_false` int DEFAULT NULL,
  `timeout` int DEFAULT NULL,
  `visible` tinyint DEFAULT NULL,
  `qcm_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `qcm_id` (`qcm_id`),
  CONSTRAINT `fk_question_qcm` FOREIGN KEY (`qcm_id`) REFERENCES `qcm` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=364 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (353,'Qui maintient Angular ?',0,0,0,30,0,95),(354,'Angular, c\'est :',0,0,0,20,0,95),(355,'Quelle est la définition du SEO ?',0,0,0,30,0,96),(356,'Que veut dire SEO ?',0,0,0,30,0,96),(357,'Actuellement, la capacité d\'un disque dur s\'exprime généralement en : ',0,0,0,30,0,97),(358,'L\'écran cathodique classique est un organe :',0,0,0,30,0,97),(359,'Pour sauver les informations de manière permanente, j\'utilise :',0,0,0,30,0,97),(360,'Dans le micro-ordinateur le traitement de l\'information est réalisé par :',0,0,0,30,0,97),(361,'L\'informatique est',0,0,0,30,0,97),(362,'Q-aaa',0,0,0,30,0,98),(363,'Q - bbb',0,0,0,30,0,99);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_USER'),(2,'ROLE_EMPLOYER'),(3,'ROLE_ADMIN'),(4,'ROLE_AUTHOR');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sector`
--

DROP TABLE IF EXISTS `sector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sector` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sector`
--

LOCK TABLES `sector` WRITE;
/*!40000 ALTER TABLE `sector` DISABLE KEYS */;
INSERT INTO `sector` VALUES (1,10,'Informatique'),(2,1,'Conception et développement'),(3,1,'Conseil et expertise'),(4,1,'Formation et enseignement'),(5,2,'Développement web'),(6,2,'Développement logiciel'),(7,5,'Développement backend'),(8,5,'Développement frontend'),(9,5,'Développement fullstack'),(10,NULL,'Numérique'),(11,10,'Multimedia'),(12,11,'Infographiste'),(13,11,'Montage vidéo');
/*!40000 ALTER TABLE `sector` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription` (
  `id` int NOT NULL AUTO_INCREMENT,
  `plan_id` int DEFAULT NULL,
  `employer_id` int DEFAULT NULL,
  `credits_used` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_subscription_plan_idx` (`plan_id`),
  KEY `fk_subscription_employer_idx` (`employer_id`),
  CONSTRAINT `fk_subscription_employer` FOREIGN KEY (`employer_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_subscription_plan` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (1,2,16,30),(2,1,1,20),(3,3,16,200),(4,2,16,30),(5,1,16,5),(6,3,16,200),(7,1,21,5),(8,1,21,5),(9,1,21,5),(10,1,21,5),(11,1,21,5),(12,1,21,5),(13,1,21,5),(14,1,21,5),(15,1,21,5),(16,1,21,5),(17,1,21,5),(18,1,21,5),(19,1,21,5),(20,1,21,5),(21,1,21,5),(22,1,21,5),(23,1,21,5),(24,1,21,5),(25,1,21,5),(26,1,21,5),(27,1,16,5),(28,3,16,30),(29,1,21,NULL),(30,2,21,NULL),(31,1,21,NULL),(32,2,21,NULL),(33,2,21,NULL),(34,1,21,NULL),(35,1,21,NULL),(36,1,21,NULL),(37,2,21,NULL);
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  KEY `fk_role_id_idx` (`role_id`),
  KEY `fk_user_id_idx` (`user_id`),
  CONSTRAINT `fk_user_roles_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_user_roles_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,2),(1,1),(2,3),(2,1),(3,1),(4,4),(4,1),(5,4),(5,1),(6,1),(6,4),(7,4),(7,1),(9,3),(10,1),(10,4),(11,4),(11,1),(12,1),(12,4),(13,3),(13,4),(14,3),(14,4),(15,4),(15,3),(16,2),(17,1),(18,4),(19,3),(20,4),(21,2),(22,2),(23,3),(24,3);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'fabiche@gmail.com','Fabrice','$2a$10$iA81cfZ20xlrTRf29XaXju2FSiKAx9CwFNA/9n5MBcMAwJtNORDWy','fabiche'),(2,'roger@gmail.com','Roger','$2a$10$MVg6RZmIGg5.VeGbOby0AOqFEalXFAUFGOtUei29cWJ1sKhc27rxq','roger'),(3,'max@gmail.com','Maxime','$2a$10$7pfvmHrgHSjWM9JYzB.NYe2iTH6TgcWzRUg2/XPA3vDZjWa6O2GjO','Max!'),(4,'soso@gmail.com','Sophie','$2a$10$haITlKB8QDtBbWY7Ib1L/esjY6BAVwz7vY7AdePbQQ7YyatOE1D1e','Sophie'),(5,'juju@gmail.com','Julien','$2a$10$z3TN3zLWanIdX/FoVD8k0OLYDH4EAGIJbONBya977sUr4qC2Sz/Ru','Julien'),(6,'gege@gmail.com','Georges','$2a$10$5cWtehLCJgCmu//AzcGiCubMtIMWHE1tTMZmZjJ1XOLK9re/KdPu6','Georges'),(7,'dada@gmail.com','dam','$2a$10$lCLFDO31ooROy7H0SH6MUu2NvIS6aKsQcgwrFB3O8TR4PLNYavaKq','Damien'),(8,'tintin@gmail.com','Tintin','$2a$10$csOd7yJYdBBJfWgAdyBr..LyoT3LNU2qsauY/Gp8uBKUD2ZvqiLgG','tintin'),(9,'dalton@hotmail.fr','Joe Dalton','$2a$10$h7sbQVOOs1paKYq76SS1iO.Pn1Ke9gcou6ypcYOBLyq6k9.2zKbxC','dalton'),(10,'titi@gmail.com','Titi','$2a$10$g4TjZmXtxEnxodCIDeVApesJsr/.tEns9xWVoa1wUX77BmonodKZW','Titi'),(11,'qsdqsd@gmail.com','qsdqsd','$2a$10$eFZRB8DAk7SP8MBGPiCGZ.xU2R2pO.2ZwROqoAFE1uT/68k615nKO','qsdqsd'),(12,'marion@gmail.com','Marion','$2a$10$uyiRS3gYC6VdFnLyAsUchO57o496RDv8iN70MSqTsNcPvLRwpJaQy','Marion'),(13,'kev@gmail.com','Kevin','$2a$10$x/7XIAgJlnjActk/fhQPFeHOTr2Tuy.9fsY8xQMuR9qdM.HCjSAma','Kevin'),(14,'pat@gmail.com','Patrick','$2a$10$oHRRYoHHAyMtoL7wOYUMVeMFx3EecVwI7qVeZZ241G/eXIR4En/OO','Patou'),(15,'pat2@gmail.com','Patrick','$2a$10$jSOO855QqaT6qqRfyUyqIOrMCWPzk4AokUl1g2BB2F2QZtCH343UW','Patou2'),(16,'clement@gmail.com','Clément','$2a$10$05OJMCkSNnAFFpzUab0oy.mBY5nCbShRgd4o1DA9ce9NCjsJ8KRaS','Clem'),(17,'ursula@gmail.com','Ursula','$2a$10$bUq8qJFJnpehMi.8dxMGle5zCaq3UuNLc6VVLgIb9skHMQRvS8Ziu','Ursula'),(18,'doob@gmail.com','Bastien','$2a$10$9jMsyueMH0K1wXVLca0uLOzliMk0PEJ/Qzf/yK/pU983ePTvK1vzW','Doob'),(19,'nico@gg.fr','Nicolas','$2a$10$h3Sm2pxKFCBh0BKwaK2MyeeDOj3QSjH4G1o53JtnUUzBMH31t2ENa','Nico'),(20,'jj@caramail.com','Jean de la Fontaine','$2a$10$Wm0ik.B7T/9vjT0cidYVMOENwTwJSpAxn4sZeTKn88eLYclJ.A/Ue','Jean'),(21,'dora@gmail.com','Dora','$2a$10$FLd9vzY3KKgHlg/MTmlGJ.x3mOkqXi6bAySu/xTDkfOXi5G/cAkwu','Dora'),(22,'f.e@zaeazgmail.com','azeaeza','$2a$10$P2pXYxs1bLcUNKSexagmeO4RyZznr3Cw3EAdW/PNy7qfwXdGCgN0y','eazazeza'),(23,'franceoise@gmail.com','Francoise','$2a$10$JCnnwFvrsQIzADy7Hx/Hl.WGsvvEr.jZ0pCrUzF.UKiXRjp4RA9hm','francoise'),(24,'tatay@gmail.com','TataYoyo','$2a$10$QGBozfCcIlLYilP5AE/k7.PPimflMCuwYO6.M/DmZDC.ymVV/4dE2','TataYoyo');
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

-- Dump completed on 2020-12-01 21:02:35
