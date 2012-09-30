-- MySQL dump 10.13  Distrib 5.5.24, for Win64 (x86)
--
-- Host: localhost    Database: invoicedb
-- ------------------------------------------------------
-- Server version	5.5.24

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
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `InvoiceID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`InvoiceID`),
  UNIQUE KEY `idInvoice_UNIQUE` (`InvoiceID`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (2,'Invoice For Bill'),(3,'Invoice For Bill'),(4,'Invoice For Ben'),(5,'Invoice For Ben'),(6,'Invoice For Ben'),(7,'Invoice For Ben'),(8,'Invoice For Ben'),(9,'Invoice For Ben'),(10,'Invoice For Ben'),(11,'Invoice For Ben'),(12,'Invoice For Ben'),(13,'Invoice For Ben'),(14,'Invoice For Bob'),(15,'Invoice For Bob'),(16,'Invoice For Bob'),(17,'Invoice For Bob'),(18,'Invoice For Bob'),(19,'Invoice For Bob'),(20,'Invoice For Bob'),(21,'Invoice For Bob'),(22,'Invoice For Bob'),(23,'Invoice For Bob'),(24,'Invoice For Bob'),(25,'Invoice For Bob'),(27,'Invoice For Bob'),(28,'Invoice For Bob'),(29,'Invoice For Bob'),(30,'Invoice For Bob'),(31,'Invoice For Bob'),(32,'Invoice For Bob'),(33,'Invoice For Bob'),(34,'Invoice For Bob'),(35,'Invoice For Bob'),(36,'Merlot Invoice'),(37,'Invoice For Bob'),(38,'Merlot Invoice'),(39,'Invoice For Bob'),(40,'Merlot Invoice'),(41,'Merlot Invoice'),(42,'Merlot Invoice'),(43,'Invoice for Merlot'),(44,'Merlot Invoice'),(45,'Invoice for Merlot'),(46,'Invoice For Bob'),(47,'Merlot Invoice'),(48,'Invoice for Merlot'),(49,'Invoice For Bob'),(50,'Merlot Invoice'),(51,'Invoice for Merlot'),(52,'Merlot Invoice'),(53,'Merlot Invoice'),(54,'Merlot Invoice'),(55,'Merlot Invoice'),(56,'Invoice for Merlot'),(57,'Invoice for Merlot'),(58,'Invoice For Bob'),(59,'Invoice for Merlot'),(60,'Invoice For Bob'),(61,'Invoice for Merlot'),(66,'Invoice for Merlot'),(69,'Invoice for Merlot'),(72,'Invoice for Merlot'),(73,'Invoice for Merlot'),(76,'Invoice for Merlot'),(79,'Invoice for Merlot'),(84,'Invoice for Merlot'),(87,'Invoice for Merlot'),(90,'Invoice for Merlot'),(93,'Invoice for Merlot'),(94,'Invoice for Merlot'),(99,'Invoice for Merlot'),(102,'Invoice for Merlot'),(103,'Invoice for Merlot'),(106,'Invoice for Merlot'),(109,'Invoice for Merlot'),(114,'Invoice for Merlot'),(117,'Invoice for Merlot'),(118,'Invoice for Merlot'),(121,'Invoice for Merlot'),(124,'Invoice for Merlot'),(127,'Invoice for Merlot'),(130,'Invoice for Merlot'),(133,'Invoice for Merlot'),(136,'Invoice for Merlot'),(139,'Invoice for Merlot'),(142,'Invoice for Merlot'),(145,'Invoice for Merlot'),(150,'Invoice for Merlot'),(151,'Invoice for Merlot'),(156,'Invoice for Merlot'),(159,'Invoice for Merlot'),(160,'Invoice for Merlot'),(163,'Invoice for Merlot'),(166,'Invoice for Merlot'),(169,'Invoice for Merlot'),(172,'Invoice for Merlot'),(175,'Invoice for Merlot'),(178,'Invoice for Merlot'),(183,'Invoice for Merlot'),(188,'Invoice for Merlot'),(189,'Invoice for Merlot'),(192,'Invoice for Merlot'),(197,'Invoice for Merlot'),(198,'Invoice for Merlot'),(201,'Invoice for Merlot'),(206,'Invoice for Merlot'),(207,'Invoice for Merlot'),(212,'Invoice for Merlot'),(215,'Invoice for Merlot');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoicelineitem`
--

DROP TABLE IF EXISTS `invoicelineitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoicelineitem` (
  `LineItemID` int(11) NOT NULL AUTO_INCREMENT,
  `InvoiceID` int(11) DEFAULT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Balance` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`LineItemID`),
  UNIQUE KEY `LineItemID_UNIQUE` (`LineItemID`),
  KEY `FK_Invoice` (`InvoiceID`),
  CONSTRAINT `FK_Invoice` FOREIGN KEY (`InvoiceID`) REFERENCES `invoice` (`InvoiceID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=267 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoicelineitem`
--

LOCK TABLES `invoicelineitem` WRITE;
/*!40000 ALTER TABLE `invoicelineitem` DISABLE KEYS */;
INSERT INTO `invoicelineitem` VALUES (2,2,'Cheesehead Bolts',-10.50),(3,3,'Cheesehead Bolts',-10.50),(4,4,'Cheesehead Bolts',-10.50),(5,5,'Cheesehead Bolts',-10.50),(6,6,'Cheesehead Bolts',-10.50),(7,7,'Cheesehead Bolts',-10.50),(8,8,'Cheesehead Bolts',-10.50),(9,9,'Cheesehead Bolts',-10.50),(10,10,'Cheesehead Bolts',-10.50),(11,11,'Cheesehead Bolts',-10.50),(12,12,'Cheesehead Bolts',-10.50),(13,13,'Cheesehead Bolts',-10.50),(14,14,'Cheesehead Bolts',-10.50),(15,15,'Cheesehead Bolts',-10.50),(16,16,'Cheesehead Bolts',-10.50),(17,17,'Cheesehead Bolts',-10.50),(18,18,'Cheesehead Bolts',-10.50),(19,19,'Cheesehead Bolts',-10.50),(20,20,'Cheesehead Bolts',-10.50),(21,21,'Cheesehead Bolts',-10.50),(22,22,'Cheesehead Bolts',-10.50),(23,23,'Cheesehead Bolts',-10.50),(24,24,'Cheesehead Bolts',-10.50),(25,25,'Cheesehead Bolts',-10.50),(26,27,'Cheesehead Bolts',-10.50),(27,28,'Cab',-10.50),(28,29,'Cheesehead Bolts',-10.50),(29,30,'Cheesehead Bolts',-10.50),(30,31,'Cheesehead Bolts',-10.50),(31,32,'Cheesehead Bolts',-10.50),(32,33,'Cheesehead Bolts',-10.50),(33,34,'Cheesehead Bolts',-10.50),(34,35,'Cheesehead Bolts',-10.50),(35,36,'Merlot, 750ml',-20.99),(36,37,'Cheesehead Bolts',-10.50),(37,38,'Merlot, 750ml',-20.99),(38,39,'Cheesehead Bolts',-10.50),(39,40,'Merlot, 750ml',-20.99),(40,41,'Merlot, 750ml',-20.99),(41,42,'Merlot, 750ml',-20.99),(42,43,'Merlot, 750ml',-20.99),(43,44,'Merlot, 750ml',-20.99),(44,45,'Merlot, 750ml',-20.99),(45,46,'Cheesehead Bolts',-10.50),(46,47,'Merlot, 750ml',-20.99),(47,48,'Merlot, 750ml',-20.99),(48,49,'Cheesehead Bolts',-10.50),(49,50,'Merlot, 750ml',-20.99),(50,51,'Merlot, 750ml',-20.99),(51,52,'Merlot, 750ml',-20.99),(52,53,'Merlot, 750ml',-20.99),(53,54,'Merlot, 750ml',-20.99),(54,55,'Merlot, 750ml',-20.99),(55,56,'Merlot, 750ml',-20.99),(56,57,'Merlot, 750ml',-20.99),(57,58,'Cheesehead Bolts',-10.50),(58,59,'Merlot, 750ml',-20.99),(59,60,'Cheesehead Bolts',-10.50),(60,61,'Merlot, 750ml',-20.99),(67,66,'Merlot, 750ml',-20.99),(71,69,'Merlot, 750ml',-20.99),(75,72,'Merlot, 750ml',-20.99),(76,73,'Merlot, 750ml',-20.99),(80,76,'Merlot, 750ml',-20.99),(84,79,'Merlot, 750ml',-20.99),(91,84,'Merlot, 750ml',-20.99),(95,87,'Merlot, 750ml',-20.99),(99,90,'Merlot, 750ml',-20.99),(103,93,'Merlot, 750ml',-20.99),(104,94,'Merlot, 750ml',-20.99),(111,99,'Merlot, 750ml',-20.99),(115,102,'Merlot, 750ml',-20.99),(116,103,'Merlot, 750ml',-20.99),(120,106,'Merlot, 750ml',-20.99),(124,109,'Merlot, 750ml',-20.99),(131,114,'Merlot, 750ml',-20.99),(135,117,'Merlot, 750ml',-20.99),(136,118,'Merlot, 750ml',-20.99),(140,121,'Merlot, 750ml',-20.99),(144,124,'Merlot, 750ml',-20.99),(148,127,'Merlot, 750ml',-20.99),(152,130,'Merlot, 750ml',-20.99),(156,133,'Merlot, 750ml',-20.99),(160,136,'Merlot, 750ml',-20.99),(164,139,'Merlot, 750ml',-20.99),(168,142,'Merlot, 750ml',-20.99),(172,145,'Merlot, 750ml',-20.99),(179,150,'Merlot, 750ml',-20.99),(180,151,'Merlot, 750ml',-20.99),(187,156,'Merlot, 750ml',-20.99),(191,159,'Merlot, 750ml',-20.99),(192,160,'Merlot, 750ml',-20.99),(196,163,'Merlot, 750ml',-20.99),(200,166,'Merlot, 750ml',-20.99),(204,169,'Merlot, 750ml',-20.99),(208,172,'Merlot, 750ml',-20.99),(212,175,'Merlot, 750ml',-20.99),(216,178,'Merlot, 750ml',-20.99),(223,183,'Merlot, 750ml',-20.99),(230,188,'Merlot, 750ml',-20.99),(231,189,'Merlot, 750ml',-20.99),(235,192,'Merlot, 750ml',-20.99),(242,197,'Merlot, 750ml',-20.99),(243,198,'Merlot, 750ml',-20.99),(247,201,'Merlot, 750ml',-20.99),(254,206,'Merlot, 750ml',-20.99),(255,207,'Merlot, 750ml',-20.99),(262,212,'Merlot, 750ml',-20.99),(266,215,'Merlot, 750ml',-20.99);
/*!40000 ALTER TABLE `invoicelineitem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-03 12:36:10
