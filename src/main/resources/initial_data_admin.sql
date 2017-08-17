-- DROP DATABASE mwc01;
-- CREATE DATABASE IF NOT EXISTS `mwc01` /*!40100 DEFAULT CHARACTER SET latin1 */;
-- GRANT ALL PRIVILEGES ON mwc01.* TO 'root'@'localhost';
USE `mwc01`;
-- DROP DATABASE javasdi_mwc01;
-- CREATE DATABASE IF NOT EXISTS `javasdi_mwc01` /*!40100 DEFAULT CHARACTER SET latin1 */;
-- GRANT ALL PRIVILEGES ON javasdi_mwc01.* TO 'javasdi_mwc01'@'localhost';
-- USE `javasdi_mwc01`;
-- MySQL dump 10.13  Distrib 5.5.32, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: mwc01
-- ------------------------------------------------------
-- Server version	5.5.32-0ubuntu0.13.04.1

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
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACRONYM` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `URI` longtext,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'JSD','JavaSD','http://www.javasd.com',0);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `branch` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACRONYM` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT '',
  `ADDRESS1` varchar(255) DEFAULT NULL,
  `ADDRESS2` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `ZIP_CODE` varchar(255) DEFAULT NULL,
  `COMPANY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKADAF25A22194F436` (`COMPANY_ID`),
  CONSTRAINT `FKADAF25A22194F436` FOREIGN KEY (`COMPANY_ID`) REFERENCES `company` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES (1,'JSD','1000 My Street','San Diego, CA',0,'92000',1);
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACCESS_CODE` varchar(255) DEFAULT NULL,
  `ACCESS_CODE_TYPE` varchar(255) DEFAULT NULL,
  `BIRTH_DATE` datetime DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRST_NAME` varchar(255) DEFAULT NULL,
  `LAST_NAME` varchar(255) DEFAULT NULL,
  `LINK_CODE` varchar(255) DEFAULT NULL,
  `PHONE` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `MANAGER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKC4E39B55B555AF26` (`MANAGER_ID`),
  CONSTRAINT `FKC4E39B55B555AF26` FOREIGN KEY (`MANAGER_ID`) REFERENCES `person` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'JSD01','ADMIN','1962-02-02','javasd.info@gmail.com','Almir','Campos',NULL,'555-999-0000','REGISTERED',0,NULL);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_in_branch`
--

DROP TABLE IF EXISTS `person_in_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_in_branch` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `POSITION` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `BRANCH_ID` bigint(20) DEFAULT NULL,
  `PERSON_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK968021925377A27E` (`PERSON_ID`),
  KEY `FK96802192DA76635E` (`BRANCH_ID`),
  CONSTRAINT `FK96802192DA76635E` FOREIGN KEY (`BRANCH_ID`) REFERENCES `branch` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK968021925377A27E` FOREIGN KEY (`PERSON_ID`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_in_branch`
--

LOCK TABLES `person_in_branch` WRITE;
/*!40000 ALTER TABLE `person_in_branch` DISABLE KEYS */;
INSERT INTO `person_in_branch` VALUES (1,'Director',0,1,1);
/*!40000 ALTER TABLE `person_in_branch` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `mwc_user`
--

DROP TABLE IF EXISTS `mwc_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mwc_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AVATAR_IMG` longblob,
  `DISPLAY_NAME` varchar(255) DEFAULT NULL,
  `ENABLED` bit(1) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `PREFERRED_LANGUAGE` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `PERSON_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK804710515377A27E` (`PERSON_ID`),
  CONSTRAINT `FK804710515377A27E` FOREIGN KEY (`PERSON_ID`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mwc_user`
--

LOCK TABLES `mwc_user` WRITE;
/*!40000 ALTER TABLE `mwc_user` DISABLE KEYS */;
INSERT INTO `mwc_user` VALUES (1,NULL,'Almir','','almir','en_US','REGISTERED','almir',0,1);
/*!40000 ALTER TABLE `mwc_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mwc_user_log`
--

DROP TABLE IF EXISTS `mwc_user_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mwc_user_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOG_TEXT` varchar(255) DEFAULT NULL,
  `MWC_USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKEAE152563FECC6C9` (`MWC_USER_ID`),
  CONSTRAINT `FKEAE152563FECC6C9` FOREIGN KEY (`MWC_USER_ID`) REFERENCES `mwc_user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mwc_user_log`
--

LOCK TABLES `mwc_user_log` WRITE;
/*!40000 ALTER TABLE `mwc_user_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `mwc_user_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mwc_user_role`
--

DROP TABLE IF EXISTS `mwc_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mwc_user_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AUTHORITY` varchar(255) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `MWC_USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK714BB3A43FECC6C9` (`MWC_USER_ID`),
  CONSTRAINT `FK714BB3A43FECC6C9` FOREIGN KEY (`MWC_USER_ID`) REFERENCES `mwc_user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mwc_user_role`
--

LOCK TABLES `mwc_user_role` WRITE;
/*!40000 ALTER TABLE `mwc_user_role` DISABLE KEYS */;
INSERT INTO `mwc_user_role` VALUES (1,'ROLE_ADMIN',0,1),(2,'ROLE_MANAGER',0,1),(3,'ROLE_USER',0,1);
/*!40000 ALTER TABLE `mwc_user_role` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `series`
--

DROP TABLE IF EXISTS `series`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `series` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `ESTIMATED_END_DATE` datetime DEFAULT NULL,
  `GOAL_WEIGHT` double DEFAULT NULL,
  `INITIAL_PHOTO` longblob,
  `INITIAL_WEIGHT` double DEFAULT NULL,
  `REAL_END_DATE` datetime DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `MWC_USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKCA01FE773FECC6C9` (`MWC_USER_ID`),
  CONSTRAINT `FKCA01FE773FECC6C9` FOREIGN KEY (`MWC_USER_ID`) REFERENCES `mwc_user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `series`
--

LOCK TABLES `series` WRITE;
/*!40000 ALTER TABLE `series` DISABLE KEYS */;
/*!40000 ALTER TABLE `series` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `series_detail`
--

DROP TABLE IF EXISTS `series_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `series_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMMENT` varchar(255) DEFAULT NULL,
  `PHOTO` longblob,
  `VERSION` int(11) DEFAULT NULL,
  `WEIGHT` double DEFAULT NULL,
  `WEIGHT_DATE` datetime DEFAULT NULL,
  `SERIES_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK28EA33193C8373E` (`SERIES_ID`),
  CONSTRAINT `FK28EA33193C8373E` FOREIGN KEY (`SERIES_ID`) REFERENCES `series` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `series_detail`
--

LOCK TABLES `series_detail` WRITE;
/*!40000 ALTER TABLE `series_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `series_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-09-09 17:54:58


-- INSERT INTO mwc01.company (`ACRONYM`, `NAME`, `VERSION`, `URI`) VALUES ('JSD', 'JavaSD', 0, 'http://www.javasd.com');
-- INSERT INTO mwc01.company (`ACRONYM`, `NAME`, `VERSION`, `URI`) VALUES ('SAIC', 'Science Application International Corporation', 0, 'http://www.siac.com');
-- 
-- INSERT INTO mwc01.branch (`ACRONYM`, `ADDRESS1`, `ADDRESS2`, `VERSION`, `ZIP_CODE`, `COMPANY_ID`) VALUES ('CO', '1000 My Street', 'San Diego, CA', 0, '92000', 1);
-- INSERT INTO mwc01.branch (`ACRONYM`, `ADDRESS1`, `ADDRESS2`, `VERSION`, `ZIP_CODE`, `COMPANY_ID`) VALUES ('HR', '1000 HR Street', 'San Diego, CA', 0, '92000', 2);
-- INSERT INTO mwc01.branch (`ACRONYM`, `ADDRESS1`, `ADDRESS2`, `VERSION`, `ZIP_CODE`, `COMPANY_ID`) VALUES ('IT', '1000 IT Street', 'Escondido, CA', 0, '93000', 2);
-- 
-- INSERT INTO mwc01.person (`EMAIL`, `STATUS`, `ACCESS_CODE_TYPE`, `ACCESS_CODE`, `FIRST_NAME`, `LAST_NAME`, `VERSION`) VALUES ('javasd.info@gmail.com', 'REGISTERED', 'ADMIN', 'JSD01', 'Almir', 'Campos', 0);
-- INSERT INTO mwc01.person (`EMAIL`, `STATUS`, `ACCESS_CODE_TYPE`, `ACCESS_CODE`, `FIRST_NAME`, `LAST_NAME`, `VERSION`) VALUES ('javasd.info@gmail.com', 'INVITED', 'NOT_USER', 'JSD02', 'Migol', 'Chang', 0);
-- INSERT INTO mwc01.person (`EMAIL`, `STATUS`, `ACCESS_CODE_TYPE`, `ACCESS_CODE`, `FIRST_NAME`, `LAST_NAME`, `VERSION`) VALUES ('javasd.info@gmail.com', 'INVITED', 'MANAGER', 'SAIC01', 'John', 'Doe', 0);
-- INSERT INTO mwc01.person (`EMAIL`, `STATUS`, `ACCESS_CODE_TYPE`, `ACCESS_CODE`, `FIRST_NAME`, `LAST_NAME`, `VERSION`) VALUES ('javasd.info@gmail.com', 'NOT_INVITED', 'USER', 'SAIC02', 'Mary', 'Doe', 0);
-- 
-- INSERT INTO mwc01.person_in_branch (`POSITION`, VERSION, BRANCH_ID, PERSON_ID) VALUES ('Director', 0, 1, 1)
-- INSERT INTO mwc01.person_in_branch (`POSITION`, VERSION, BRANCH_ID, PERSON_ID) VALUES ('Manager', 0, 1, 2)
-- INSERT INTO mwc01.person_in_branch (`POSITION`, VERSION, BRANCH_ID, PERSON_ID) VALUES ('HR Manager', 0, 2, 3)
-- INSERT INTO mwc01.person_in_branch (`POSITION`, VERSION, BRANCH_ID, PERSON_ID) VALUES ('IT Manager', 0, 3, 4)
-- 
-- INSERT INTO mwc01.mwc_user (AVATAR_IMG, DISPLAY_NAME, ENABLED, PASSWORD, PREFERRED_LANGUAGE, STATUS, USERNAME, VERSION, PERSON_ID) VALUES (NULL, 'Almir', true, 'almir', 'en_US', 'REGISTERED', 'almir', 0, 1);
-- 
-- INSERT INTO mwc01.mwc_user_role (`AUTHORITY`, `VERSION`, `MWC_USER_ID`) VALUES ('ROLE_USER', '0', '1');
-- INSERT INTO mwc01.mwc_user_role (`AUTHORITY`, `VERSION`, `MWC_USER_ID`) VALUES ('ROLE_ADMIN', '0', '1');