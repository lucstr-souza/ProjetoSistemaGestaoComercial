-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: sgc_lojaartesanato
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `cli_clientes`
--

DROP TABLE IF EXISTS `cli_clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cli_clientes` (
  `cli_id` int NOT NULL,
  `cli_nome` varchar(100) NOT NULL,
  `cli_cpf` varchar(11) NOT NULL,
  `cli_email` varchar(100) NOT NULL,
  `cli_telefone` varchar(20) NOT NULL,
  `cli_endereço` varchar(100) NOT NULL,
  PRIMARY KEY (`cli_id`),
  UNIQUE KEY `cli_cpf_UNIQUE` (`cli_cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cli_clientes`
--

LOCK TABLES `cli_clientes` WRITE;
/*!40000 ALTER TABLE `cli_clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `cli_clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `it_itens_venda`
--

DROP TABLE IF EXISTS `it_itens_venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `it_itens_venda` (
  `it_id` int NOT NULL,
  `vd_id` int NOT NULL,
  `pd_id` int NOT NULL,
  `it_quantidade` int NOT NULL,
  `it_preço` decimal(10,2) NOT NULL,
  PRIMARY KEY (`it_id`),
  KEY `FK_vd_id_idx` (`vd_id`),
  KEY `FK_pd_id_idx` (`pd_id`),
  CONSTRAINT `FK_pd_id` FOREIGN KEY (`pd_id`) REFERENCES `pd_produtos` (`pd_id`),
  CONSTRAINT `FK_vd_id` FOREIGN KEY (`vd_id`) REFERENCES `vd_vendas` (`vd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `it_itens_venda`
--

LOCK TABLES `it_itens_venda` WRITE;
/*!40000 ALTER TABLE `it_itens_venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `it_itens_venda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pd_produtos`
--

DROP TABLE IF EXISTS `pd_produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pd_produtos` (
  `pd_id` int NOT NULL,
  `pd_nome` varchar(100) NOT NULL,
  `pd_descriçao` longtext NOT NULL,
  `pd_preço` decimal(10,2) NOT NULL,
  `pd_estoque` int NOT NULL,
  PRIMARY KEY (`pd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pd_produtos`
--

LOCK TABLES `pd_produtos` WRITE;
/*!40000 ALTER TABLE `pd_produtos` DISABLE KEYS */;
/*!40000 ALTER TABLE `pd_produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `us_usuarios`
--

DROP TABLE IF EXISTS `us_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `us_usuarios` (
  `us_id` int NOT NULL,
  `us_username` varchar(100) NOT NULL,
  `us_senha` varchar(200) NOT NULL,
  `us_perfil` varchar(45) NOT NULL,
  PRIMARY KEY (`us_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `us_usuarios`
--

LOCK TABLES `us_usuarios` WRITE;
/*!40000 ALTER TABLE `us_usuarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `us_usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vd_vendas`
--

DROP TABLE IF EXISTS `vd_vendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vd_vendas` (
  `vd_id` int NOT NULL,
  `vd_data` datetime NOT NULL,
  `cli_id` int NOT NULL,
  `us_id` int NOT NULL,
  `vd_valortotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`vd_id`),
  KEY `FK_us_id_idx` (`us_id`),
  KEY `FK_cli_cliente_idx` (`cli_id`),
  CONSTRAINT `FK_cli_id` FOREIGN KEY (`cli_id`) REFERENCES `cli_clientes` (`cli_id`),
  CONSTRAINT `FK_us_id` FOREIGN KEY (`us_id`) REFERENCES `us_usuarios` (`us_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vd_vendas`
--

LOCK TABLES `vd_vendas` WRITE;
/*!40000 ALTER TABLE `vd_vendas` DISABLE KEYS */;
/*!40000 ALTER TABLE `vd_vendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'sgc_lojaartesanato'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-25 11:21:28
