-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: localhost    Database: onterview
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `article_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `comment_count` int NOT NULL DEFAULT '0',
  `content` varchar(255) DEFAULT NULL,
  `like_count` int NOT NULL DEFAULT '0',
  `title` varchar(255) DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `video_id` bigint DEFAULT NULL,
  PRIMARY KEY (`article_id`),
  KEY `FK6l9vkfd5ixw8o8kph5rj1k7gu` (`member_id`),
  KEY `FKeu6l7mmodbixa3lhl6kmtqk2j` (`video_id`),
  CONSTRAINT `FK6l9vkfd5ixw8o8kph5rj1k7gu` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKeu6l7mmodbixa3lhl6kmtqk2j` FOREIGN KEY (`video_id`) REFERENCES `video` (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `article_id` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `parent_comment_id` bigint DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK5yx0uphgjc6ik6hb82kkw501y` (`article_id`),
  KEY `FKmrrrpi513ssu63i2783jyiv9m` (`member_id`),
  KEY `FKhvh0e2ybgg16bpu229a5teje7` (`parent_comment_id`),
  CONSTRAINT `FK5yx0uphgjc6ik6hb82kkw501y` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE,
  CONSTRAINT `FKhvh0e2ybgg16bpu229a5teje7` FOREIGN KEY (`parent_comment_id`) REFERENCES `comment` (`comment_id`) ON DELETE CASCADE,
  CONSTRAINT `FKmrrrpi513ssu63i2783jyiv9m` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `common_question`
--

DROP TABLE IF EXISTS `common_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `common_question` (
  `common_question_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `common_question` varchar(255) DEFAULT NULL,
  `common_question_folder_id` bigint DEFAULT NULL,
  PRIMARY KEY (`common_question_id`),
  KEY `FK5hy8aophjwj2x98ggrkf6mmrs` (`common_question_folder_id`),
  CONSTRAINT `FK5hy8aophjwj2x98ggrkf6mmrs` FOREIGN KEY (`common_question_folder_id`) REFERENCES `common_question_folder` (`common_question_folder_id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common_question`
--

LOCK TABLES `common_question` WRITE;
/*!40000 ALTER TABLE `common_question` DISABLE KEYS */;
INSERT INTO `common_question` VALUES (1,'2024-02-13 17:13:02.835165','2024-02-13 17:13:02.835165','자기소개를 해주세요.',1),(2,'2024-02-13 17:13:02.891179','2024-02-13 17:13:02.891179','마지막 하고 싶은 말 해주세요.',1),(3,'2024-02-13 17:13:02.933027','2024-02-13 17:13:02.933027','타인과의 갈등을 해결한 경험이 있나요? 어떻게 해결했나요?',1),(4,'2024-02-13 17:13:02.979564','2024-02-13 17:13:02.979564','어려운 상황에서도 평정심을 유지할 수 있는 방법이 있나요?',1),(5,'2024-02-13 17:13:03.018245','2024-02-13 17:13:03.018245','팀에서 리더로서의 역할을 맡은 적이 있나요? 그 경험에 대해 이야기해주세요.',1),(6,'2024-02-13 17:13:03.054018','2024-02-13 17:13:03.054018','실패를 겪었을 때 어떻게 대처하시나요?',1),(7,'2024-02-13 17:13:03.086281','2024-02-13 17:13:03.086281','과거에 잘못을 인정하고 사과한 경험이 있나요?',1),(8,'2024-02-13 17:13:03.121111','2024-02-13 17:13:03.121111','다른 사람의 의견에 대해 열린 마음을 가지고 있나요? 그렇다면 어떻게 표현하시나요?',1),(9,'2024-02-13 17:13:03.157721','2024-02-13 17:13:03.157721','어려운 결정을 내려야 할 때, 어떤 과정을 거치시나요?',1),(10,'2024-02-13 17:13:03.194335','2024-02-13 17:13:03.194335','타인의 다양성을 존중하고 협력하는 데 어떤 노력을 기울이고 있나요?',1),(11,'2024-02-13 17:13:03.229114','2024-02-13 17:13:03.229114','스트레스가 쌓였을 때 자신을 잘 관리할 수 있는 방법은 무엇인가요?',1),(12,'2024-02-13 17:13:03.264799','2024-02-13 17:13:03.264799','동료나 팀원에게 조언을 구할 때 어떻게 접근하시나요?',1),(13,'2024-02-13 17:13:03.302824','2024-02-13 17:13:03.302824','공정한 대우를 받지 못한 상황에서 어떻게 대응하시나요?',1),(14,'2024-02-13 17:13:03.338741','2024-02-13 17:13:03.338741','동료나 팀원과의 원만한 관계를 유지하기 위해 노력한 적이 있나요? 어떤 방식으로요?',1),(15,'2024-02-13 17:13:03.373717','2024-02-13 17:13:03.373717','타인에게 도움을 주거나 협력하는 것에 대해 어떻게 생각하시나요?',1),(16,'2024-02-13 17:13:03.407762','2024-02-13 17:13:03.407762','어려운 고객과 대면했을 때, 어떻게 대응하시나요?',1),(17,'2024-02-13 17:13:03.443616','2024-02-13 17:13:03.443616','새로운 아이디어를 받아들이고 적극적으로 구현하는 방법이 있나요?',1),(18,'2024-02-13 17:13:03.477755','2024-02-13 17:13:03.477755','실패로 인한 실망이나 좌절감을 어떻게 극복하시나요?',1),(19,'2024-02-13 17:13:03.513430','2024-02-13 17:13:03.513430','타인의 의견을 수용하고 이해하는 방법에 대해 이야기해주세요.',1),(20,'2024-02-13 17:13:03.545852','2024-02-13 17:13:03.545852','동료나 팀원이 문제를 겪을 때 어떻게 지원하시나요?',1),(21,'2024-02-13 17:13:03.580374','2024-02-13 17:13:03.580374','자기개발을 위해 노력하는 방법이 있나요? 어떤 자기개발 활동을 하고 있나요?',1),(22,'2024-02-13 17:13:03.617916','2024-02-13 17:13:03.617916','업무에서 발생하는 어려움에 대해 어떤 자체적인 해결책을 찾아낸 적이 있나요?',1),(23,'2024-02-13 17:13:31.724703','2024-02-13 17:13:31.724703','자기소개를 해주세요.',2),(24,'2024-02-13 17:13:31.758734','2024-02-13 17:13:31.758734','마지막 하고 싶은 말 해주세요.',2),(25,'2024-02-13 17:13:31.791113','2024-02-13 17:13:31.791113','백엔드 개발을 선택한 이유는 무엇인가요?',2),(26,'2024-02-13 17:13:31.826053','2024-02-13 17:13:31.826053','가장 자신 있는 프로그래밍 언어는 무엇이며, 그 이유는 무엇인가요?',2),(27,'2024-02-13 17:13:31.866649','2024-02-13 17:13:31.866649','RESTful API와 GraphQL의 차이점에 대해 설명해주세요.',2),(28,'2024-02-13 17:13:31.899572','2024-02-13 17:13:31.899572','데이터베이스를 선택할 때 고려하는 요소는 무엇인가요?',2),(29,'2024-02-13 17:13:31.931051','2024-02-13 17:13:31.931051','인증과 권한 관리를 위해 어떤 도구나 기술을 사용해 본 경험이 있나요?',2),(30,'2024-02-13 17:13:31.962475','2024-02-13 17:13:31.962475','SQL과 NoSQL 데이터베이스의 차이점은 무엇인가요?',2),(31,'2024-02-13 17:13:31.995648','2024-02-13 17:13:31.995648','데이터베이스 인덱스에 대해 설명해주세요.',2),(32,'2024-02-13 17:13:32.028614','2024-02-13 17:13:32.028614','캐시를 사용하는 이유와 캐시 동작 방식에 대해 설명해주세요.',2),(33,'2024-02-13 17:13:32.059077','2024-02-13 17:13:32.059077','동시성과 병렬성의 차이점은 무엇인가요? 백엔드 시스템에서 이를 어떻게 다루나요?',2),(34,'2024-02-13 17:13:32.091530','2024-02-13 17:13:32.091530','서버리스 아키텍처에 대해 설명해주세요.',2),(35,'2024-02-13 17:13:32.122681','2024-02-13 17:13:32.122681','웹 서버와 애플리케이션 서버의 차이점은 무엇인가요?',2),(36,'2024-02-13 17:13:32.154712','2024-02-13 17:13:32.154712','마이크로서비스 아키텍처의 장단점에 대해 설명해주세요.',2),(37,'2024-02-13 17:13:32.186387','2024-02-13 17:13:32.186387','도커와 가상 머신의 차이점은 무엇인가요?',2),(38,'2024-02-13 17:13:32.219542','2024-02-13 17:13:32.219542','백엔드 시스템의 보안에 대해 어떻게 생각하나요? 주요 보안 문제는 무엇인가요?',2),(39,'2024-02-13 17:13:32.249945','2024-02-13 17:13:32.249945','REST API 설계 원칙에 대해 설명해주세요.',2),(40,'2024-02-13 17:13:32.281354','2024-02-13 17:13:32.281354','백엔드 시스템의 성능을 최적화하기 위해 어떤 방법을 사용할 수 있나요?',2),(41,'2024-02-13 17:13:32.311693','2024-02-13 17:13:32.311693','테스트 주도 개발(TDD)과 통합 테스트의 차이점은 무엇인가요?',2),(42,'2024-02-13 17:13:32.340437','2024-02-13 17:13:32.340437','서비스 장애 대응을 위한 전략은 무엇인가요? 백엔드 시스템의 복원력을 높이기 위한 방법은 무엇인가요?',2),(43,'2024-02-13 17:13:32.369517','2024-02-13 17:13:32.369517','CI/CD 파이프라인을 구축해 본 경험이 있나요? 사용한 도구와 경험을 설명해주세요.',2),(44,'2024-02-13 17:13:32.399455','2024-02-13 17:13:32.399455','최근에 공부한 기술이나 프레임워크가 있나요? 관련 경험에 대해 이야기해주세요.',2),(45,'2024-02-13 17:15:16.500350','2024-02-13 17:15:16.500350','자기소개를 해주세요.',3),(46,'2024-02-13 17:15:16.532840','2024-02-13 17:15:16.532840','마지막 하고 싶은 말 해주세요.',3),(47,'2024-02-13 17:15:16.565784','2024-02-13 17:15:16.565784','프론트엔드 개발을 선택한 이유는 무엇인가요?',3),(48,'2024-02-13 17:15:16.594340','2024-02-13 17:15:16.594340','HTML, CSS, JavaScript 각각에 대해 설명해주세요.',3),(49,'2024-02-13 17:15:16.625339','2024-02-13 17:15:16.625339','웹 표준(Standards)에 대해 어떤 것을 알고 있나요?',3),(50,'2024-02-13 17:15:16.659042','2024-02-13 17:15:16.659042','CSS의 박스 모델(Box Model)에 대해 설명해주세요.',3),(51,'2024-02-13 17:15:16.693216','2024-02-13 17:15:16.693216','반응형 웹 디자인과 적응형 웹 디자인의 차이점은 무엇인가요?',3),(52,'2024-02-13 17:15:16.723531','2024-02-13 17:15:16.723531','CSS 전처리기(Preprocessor)를 사용한 경험이 있나요? 그렇다면 어떤 것을 사용해보았나요?',3),(53,'2024-02-13 17:15:16.754785','2024-02-13 17:15:16.754785','자바스크립트의 클로저(Closures)에 대해 설명해주세요.',3),(54,'2024-02-13 17:15:16.784732','2024-02-13 17:15:16.784732','DOM이란 무엇이며, 어떻게 작동하는지 설명해주세요.',3),(55,'2024-02-13 17:15:16.818641','2024-02-13 17:15:16.818641','AJAX의 동작 방식과 사용 이유는 무엇인가요?',3),(56,'2024-02-13 17:15:16.853001','2024-02-13 17:15:16.853001','React나 Angular와 같은 프론트엔드 프레임워크를 사용한 경험이 있나요? 사용한 프레임워크에 대해 설명해주세요.',3),(57,'2024-02-13 17:15:16.885309','2024-02-13 17:15:16.885309','브라우저 저장소(Browser Storage)에 대해 설명해주세요. 쿠키와의 차이점은 무엇인가요?',3),(58,'2024-02-13 17:15:16.915830','2024-02-13 17:15:16.915830','웹 성능 최적화를 위해 어떤 기술이나 방법을 사용할 수 있나요?',3),(59,'2024-02-13 17:15:16.950551','2024-02-13 17:15:16.950551','SEO에 대해 알고 있나요? 프론트엔드 개발자가 SEO를 고려해야 하는 이유는 무엇인가요?',3),(60,'2024-02-13 17:15:16.980397','2024-02-13 17:15:16.980397','웹 접근성(Accessibility)을 고려한 프론트엔드 개발 경험이 있나요? 그렇다면 어떤 기술이나 방법을 사용했나요?',3),(61,'2024-02-13 17:15:17.011638','2024-02-13 17:15:17.011638','웹 애니메이션을 만드는 방법에 대해 설명해주세요.',3),(62,'2024-02-13 17:15:17.042898','2024-02-13 17:15:17.042898','컴포넌트 기반 프론트엔드 프레임워크의 장점은 무엇인가요?',3),(63,'2024-02-13 17:15:17.073910','2024-02-13 17:15:17.073910','크로스 브라우징(Cross-Browser)에 대한 이해도가 어떻게 되나요? 어떻게 크로스 브라우징을 보장하나요?',3),(64,'2024-02-13 17:15:17.107521','2024-02-13 17:15:17.107521','퍼포먼스 최적화를 위해 주로 어떤 도구나 기술을 사용하시나요?',3),(65,'2024-02-13 17:15:17.133964','2024-02-13 17:15:17.133964','웹 보안에 대한 이해도가 어떻게 되나요? 주요 보안 문제는 무엇이며, 그에 대한 대응책은 무엇인가요?',3),(66,'2024-02-13 17:15:17.162051','2024-02-13 17:15:17.162051','최근에 학습한 프론트엔드 기술이나 도구가 있나요? 그것에 대해 이야기해주세요.',3),(67,'2024-02-14 10:46:56.555143','2024-02-14 10:46:56.555143','자기소개를 해주세요.',3),(68,'2024-02-14 10:46:56.590952','2024-02-14 10:46:56.590952','마지막 하고 싶은 말 해주세요.',3),(69,'2024-02-14 10:46:56.623556','2024-02-14 10:46:56.623556','프론트엔드 개발을 선택한 이유는 무엇인가요?',3),(70,'2024-02-14 10:46:56.650784','2024-02-14 10:46:56.650784','HTML, CSS, JavaScript 각각에 대해 설명해주세요.',3),(71,'2024-02-14 10:46:56.684063','2024-02-14 10:46:56.684063','웹 표준(Standards)에 대해 어떤 것을 알고 있나요?',3),(72,'2024-02-14 10:46:56.714313','2024-02-14 10:46:56.714313','CSS의 박스 모델(Box Model)에 대해 설명해주세요.',3),(73,'2024-02-14 10:46:56.782154','2024-02-14 10:46:56.782154','반응형 웹 디자인과 적응형 웹 디자인의 차이점은 무엇인가요?',3),(74,'2024-02-14 10:46:56.811545','2024-02-14 10:46:56.811545','CSS 전처리기(Preprocessor)를 사용한 경험이 있나요? 그렇다면 어떤 것을 사용해보았나요?',3),(75,'2024-02-14 10:46:56.840600','2024-02-14 10:46:56.840600','자바스크립트의 클로저(Closures)에 대해 설명해주세요.',3),(76,'2024-02-14 10:46:56.873410','2024-02-14 10:46:56.873410','DOM이란 무엇이며, 어떻게 작동하는지 설명해주세요.',3),(77,'2024-02-14 10:46:56.910022','2024-02-14 10:46:56.910022','AJAX의 동작 방식과 사용 이유는 무엇인가요?',3),(78,'2024-02-14 10:46:56.936755','2024-02-14 10:46:56.936755','React나 Angular와 같은 프론트엔드 프레임워크를 사용한 경험이 있나요? 사용한 프레임워크에 대해 설명해주세요.',3),(79,'2024-02-14 10:46:56.966925','2024-02-14 10:46:56.966925','브라우저 저장소(Browser Storage)에 대해 설명해주세요. 쿠키와의 차이점은 무엇인가요?',3),(80,'2024-02-14 10:46:56.994851','2024-02-14 10:46:56.994851','웹 성능 최적화를 위해 어떤 기술이나 방법을 사용할 수 있나요?',3),(81,'2024-02-14 10:46:57.025534','2024-02-14 10:46:57.025534','SEO에 대해 알고 있나요? 프론트엔드 개발자가 SEO를 고려해야 하는 이유는 무엇인가요?',3),(82,'2024-02-14 10:46:57.055948','2024-02-14 10:46:57.055948','웹 접근성(Accessibility)을 고려한 프론트엔드 개발 경험이 있나요? 그렇다면 어떤 기술이나 방법을 사용했나요?',3),(83,'2024-02-14 10:46:57.087921','2024-02-14 10:46:57.087921','웹 애니메이션을 만드는 방법에 대해 설명해주세요.',3),(84,'2024-02-14 10:46:57.134132','2024-02-14 10:46:57.134132','컴포넌트 기반 프론트엔드 프레임워크의 장점은 무엇인가요?',3),(85,'2024-02-14 10:46:57.174577','2024-02-14 10:46:57.174577','크로스 브라우징(Cross-Browser)에 대한 이해도가 어떻게 되나요? 어떻게 크로스 브라우징을 보장하나요?',3),(86,'2024-02-14 10:46:57.215250','2024-02-14 10:46:57.215250','퍼포먼스 최적화를 위해 주로 어떤 도구나 기술을 사용하시나요?',3),(87,'2024-02-14 10:46:57.244501','2024-02-14 10:46:57.244501','웹 보안에 대한 이해도가 어떻게 되나요? 주요 보안 문제는 무엇이며, 그에 대한 대응책은 무엇인가요?',3),(88,'2024-02-14 10:46:57.275792','2024-02-14 10:46:57.275792','최근에 학습한 프론트엔드 기술이나 도구가 있나요? 그것에 대해 이야기해주세요.',3);
/*!40000 ALTER TABLE `common_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `common_question_folder`
--

DROP TABLE IF EXISTS `common_question_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `common_question_folder` (
  `common_question_folder_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `common_question_folder` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`common_question_folder_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common_question_folder`
--

LOCK TABLES `common_question_folder` WRITE;
/*!40000 ALTER TABLE `common_question_folder` DISABLE KEYS */;
INSERT INTO `common_question_folder` VALUES (1,'2024-02-13 17:12:22.672545','2024-02-13 17:12:22.672545','인성 면접'),(2,'2024-02-13 17:12:22.770743','2024-02-13 17:12:22.770743','백엔드 직무면접'),(3,'2024-02-13 17:12:22.805251','2024-02-13 17:12:22.805251','프론트엔드 직무면접');
/*!40000 ALTER TABLE `common_question_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_information`
--

DROP TABLE IF EXISTS `file_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file_information` (
  `file_id` bigint NOT NULL AUTO_INCREMENT,
  `origin_filename` varchar(255) DEFAULT NULL,
  `save_filename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  UNIQUE KEY `UK_niivdfryo0374urgacjbs0s2q` (`save_filename`)
) ENGINE=InnoDB AUTO_INCREMENT=935 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_information`
--

LOCK TABLES `file_information` WRITE;
/*!40000 ALTER TABLE `file_information` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interview_question`
--

DROP TABLE IF EXISTS `interview_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interview_question` (
  `interview_question_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `common_question_id` bigint DEFAULT NULL,
  `interviewee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`interview_question_id`),
  KEY `FKgp23m4d0e1gs5qdfqbnxj3irl` (`common_question_id`),
  KEY `FK95r2r22fmdvpf1hqjkk6a0gfq` (`interviewee_id`),
  CONSTRAINT `FK95r2r22fmdvpf1hqjkk6a0gfq` FOREIGN KEY (`interviewee_id`) REFERENCES `interviewee` (`interviewee_id`),
  CONSTRAINT `FKgp23m4d0e1gs5qdfqbnxj3irl` FOREIGN KEY (`common_question_id`) REFERENCES `common_question` (`common_question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2076 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interview_question`
--

LOCK TABLES `interview_question` WRITE;
/*!40000 ALTER TABLE `interview_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `interview_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interview_room`
--

DROP TABLE IF EXISTS `interview_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interview_room` (
  `interview_room_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `question_type` enum('FIT','BACKEND','FRONTEND') NOT NULL,
  `room_type` enum('SINGLE','MULTI','SELF') NOT NULL,
  `runtime` int DEFAULT NULL,
  PRIMARY KEY (`interview_room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interview_room`
--

LOCK TABLES `interview_room` WRITE;
/*!40000 ALTER TABLE `interview_room` DISABLE KEYS */;
/*!40000 ALTER TABLE `interview_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interviewee`
--

DROP TABLE IF EXISTS `interviewee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interviewee` (
  `interviewee_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `interview_room_id` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`interviewee_id`),
  KEY `FKonh03xi9p24mypvrr0u05t5dw` (`interview_room_id`),
  KEY `FK45uo1nxp622k4vygvppcwfaa6` (`member_id`),
  CONSTRAINT `FK45uo1nxp622k4vygvppcwfaa6` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKonh03xi9p24mypvrr0u05t5dw` FOREIGN KEY (`interview_room_id`) REFERENCES `interview_room` (`interview_room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=442 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interviewee`
--

LOCK TABLES `interviewee` WRITE;
/*!40000 ALTER TABLE `interviewee` DISABLE KEYS */;
/*!40000 ALTER TABLE `interviewee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `article_id` bigint NOT NULL,
  `member_id` bigint NOT NULL,
  PRIMARY KEY (`article_id`,`member_id`),
  KEY `FKa4vkf1skcfu5r6o5gfb5jf295` (`member_id`),
  CONSTRAINT `FK1hlv6urq91y6fqfg6bds5gvp9` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE,
  CONSTRAINT `FKa4vkf1skcfu5r6o5gfb5jf295` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ROLE_USER','ROLE_ADMIN') NOT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `UK_mbmcqelty0fbrvxp1q58dn57t` (`email`),
  UNIQUE KEY `UK_hh9kg6jti4n1eoiertn2k6qsc` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (2,'730f54@ssafy.com',NULL,'서싸피','$2a$10$KH5XmaDfVI7wMCD7Kq6CSOVfE8qDDpwgvkton5m5qrG6VuFc3NJGa','ROLE_USER'),(3,'d8f6a7@ssafy.com',NULL,'제갈싸피','$2a$10$kKDSUQ0Hhj4y0hRUYeUg6OBdQFgkmF.XsQP18kHiEOAIVZUW8HK2G','ROLE_USER'),(4,'a7b151@ssafy.com',NULL,'윤싸피','$2a$10$HO8mK522753Plr7BsxuqleIGLrSJi93M6462UpZDFMNUrmuJBT2JS','ROLE_USER'),(5,'28f8ac@ssafy.com',NULL,'임싸피','$2a$10$i8o1GZvxjqcJjxN.YS0ZaOE0LAfLZ163Y.3./65X2cOSoMqRbbOj.','ROLE_USER'),(6,'b98e94@ssafy.com',NULL,'쁇큝햷뚎','$2a$10$SEzt/kwM9a8JXzk7OveGX.2IBSQt6d1sHcx1AmtrOSsGJYXwycBnG','ROLE_USER'),(7,'492e41@ssafy.com',NULL,'조싸피','$2a$10$fENyJ9j1b8AUZ0BVn8/4B.i1kopl9g47iK5T9A.Y6rxQ70NJiUXFa','ROLE_USER'),(8,'e59190@ssafy.com',NULL,'염싸피','$2a$10$nSfQKTkCM/Wa2cMyLlUQe.u3/Mj.ZBFDl6wmiujKnhvMVQGnASyzi','ROLE_USER'),(9,'ca1aee@ssafy.com',NULL,'최싸피','$2a$10$V8v8Mgg/KNoFF1m/XZeyNex91WlFiyssthOzbubqrfUs4e.IYPIgG','ROLE_USER'),(10,'user@ssafy.com',NULL,'갑분싸피','$2a$10$7xa15nLJQnL7ZZkL8rePReGmqiHymSHb2lgFVIhlWcjkSEaH5YIRq','ROLE_USER'),(11,'admin@admin.com',NULL,'관리자','$2a$10$.3x3hZiHgOLl01GEmI1QP.wWolwrZqN0S24xgyLaL6L2iZndCGODq','ROLE_ADMIN'),(12,'test@test.com',NULL,'한글킥','$2a$10$Vd/G.AHfLsTpPHCRWoscU.2Z1hO68hIXEzgRC.0Bl71bm4t3zUDPi','ROLE_USER'),(13,'ssafy@ssafy.com',NULL,'김싸피','$2a$10$aIxSFnn5Ugdgtyd58jxICuRTOKqc29CA3HEG3bFfmrP46llxPBoSW','ROLE_USER'),(16,'ssafy2@ssafy.com',NULL,'이싸피','$2a$10$imI89HU4NZ5FOCtlzgvNqOzfsa0u9qIwtfGfarNOzZP0G2wkHPeJy','ROLE_USER'),(18,'woochul102@gmail.com',NULL,'나멍','$2a$10$iHR8p3GDqGitSszuCdt9/OtlT/5G8nYLIMaYMeVgI.WPKmozPbSpS','ROLE_USER'),(19,'cdex6531@gmail.com',NULL,'박박박','$2a$10$i20p5/fq9BI.4qqIpap80Oj63gXabhn.8hm8UCbs4XVla2ZUJLo9i','ROLE_USER'),(20,'jyeonuy@gmail.com',NULL,'연유','$2a$10$fWm5h1EEfO/n8HI0Xr7zGOjsozqIIyHl9WudqjoFTV2MCoynY/9ku','ROLE_USER'),(21,'duck@naver.com',NULL,'싸피','$2a$10$l8ON3OvyghmdxVLzQqx1Neyb8uP1b1MGaJWX5aiI0rWs7IVNx/Ce2','ROLE_USER'),(22,'goguma@gmail.com',NULL,'고구맛탕','$2a$10$FQF4BX36J8RWhxFmrDee/Ok1b2gF...lJ/1ftw0KgZA0kvrC7gofi','ROLE_USER'),(23,'qkrdustj45@naver.com',NULL,'이호선광인','$2a$10$EPnAi8A/zFPAftAuGHNfXu3nG83vs5yu9ASCHoOL55wDl6BGDioe2','ROLE_USER'),(25,'wkd940709@naver.com',NULL,'장장','$2a$10$cAVUn4axIFApW27nfErk2OHYrSJe0xWqyXrZbQYo71bzUbD6vCoUe','ROLE_USER'),(26,'consultant@test.account',NULL,'컨설턴트','$2a$10$Oqus4ntk4.LPQon1G53a1eeXghjBfJOXe/N4xWYglrA8Hek0/ojp2','ROLE_USER'),(27,'coach@test.account',NULL,'실습코치','$2a$10$dUh.q2dY4hcVWSUR1ic5iuDMPi/aDufyoaiaAJjRgSdp3SzLXSdu2','ROLE_USER'),(30,'interview@test.com',NULL,'조팀장','$2a$10$gngFwreRgAESpL7FpMQcVeoUWAhan0TEKeJkXIDzDsDmnjOdzC3Oi','ROLE_USER'),(31,'jin@ssafy.com',NULL,'진싸피','$2a$10$U0DLPMkdegana2Vu./cxh.buROEHWst3IuveU9yhy2WsoOOv4qk7m','ROLE_USER'),(33,'gusrl123456@gmail.com',NULL,'초이싸피','$2a$10$FlphdSOdTULhfp7Ia7a.wub9fOLVSR9Li.FFSqZOajubPOjOkKSgG','ROLE_USER');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `my_question`
--

DROP TABLE IF EXISTS `my_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_question` (
  `my_question_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `common_question_id` bigint DEFAULT NULL,
  `my_question_folder_id` bigint NOT NULL,
  PRIMARY KEY (`my_question_id`),
  KEY `FKkgnqas7d2tmdjr46p4wk7yw8o` (`common_question_id`),
  KEY `FK1tissjlfx3y5k8ohu1592sw84` (`my_question_folder_id`),
  CONSTRAINT `FK1tissjlfx3y5k8ohu1592sw84` FOREIGN KEY (`my_question_folder_id`) REFERENCES `my_question_folder` (`my_question_folder_id`),
  CONSTRAINT `FKkgnqas7d2tmdjr46p4wk7yw8o` FOREIGN KEY (`common_question_id`) REFERENCES `common_question` (`common_question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_question`
--

LOCK TABLES `my_question` WRITE;
/*!40000 ALTER TABLE `my_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `my_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `my_question_folder`
--

DROP TABLE IF EXISTS `my_question_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_question_folder` (
  `my_question_folder_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `my_question_folder` varchar(255) NOT NULL,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`my_question_folder_id`),
  KEY `FKo9pbs69tstna77y80rcek3k18` (`member_id`),
  CONSTRAINT `FKo9pbs69tstna77y80rcek3k18` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_question_folder`
--

LOCK TABLES `my_question_folder` WRITE;
/*!40000 ALTER TABLE `my_question_folder` DISABLE KEYS */;
/*!40000 ALTER TABLE `my_question_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video` (
  `video_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `bookmark` bit(1) DEFAULT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `video_length` bigint DEFAULT NULL,
  `interview_question_id` bigint DEFAULT NULL,
  `my_question_id` bigint DEFAULT NULL,
  `thumbnail_url` bigint DEFAULT NULL,
  `video_url` bigint DEFAULT NULL,
  PRIMARY KEY (`video_id`),
  UNIQUE KEY `UK_m60ro6g4hwnnpg4tucbyq06g4` (`interview_question_id`),
  UNIQUE KEY `UK_bxfp41pdu37gsvsjb531dggm3` (`thumbnail_url`),
  UNIQUE KEY `UK_c5vfd37nie32dbqtl95kkcwys` (`video_url`),
  KEY `FKa5qm065urfxtrv8y2e88g7jw9` (`my_question_id`),
  CONSTRAINT `FK297jxhft3albuy3fe5k1h5cb0` FOREIGN KEY (`video_url`) REFERENCES `file_information` (`file_id`),
  CONSTRAINT `FK2bkt7r29sndclcyspg67wjrmf` FOREIGN KEY (`thumbnail_url`) REFERENCES `file_information` (`file_id`),
  CONSTRAINT `FK61jpdqa1sy3jvdupdovns6ny4` FOREIGN KEY (`interview_question_id`) REFERENCES `interview_question` (`interview_question_id`),
  CONSTRAINT `FKa5qm065urfxtrv8y2e88g7jw9` FOREIGN KEY (`my_question_id`) REFERENCES `my_question` (`my_question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=468 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
/*!40000 ALTER TABLE `video` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-15 13:36:16
