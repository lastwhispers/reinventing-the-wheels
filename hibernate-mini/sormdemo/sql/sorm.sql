/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.56 : Database - sorm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sorm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `sorm`;

/*Table structure for table `dept` */

DROP TABLE IF EXISTS `dept`;

CREATE TABLE `dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `dept` */

insert  into `dept`(`id`,`dname`,`address`) values (1,'财务部','金融街'),(2,'技术部','四三旗');

/*Table structure for table `emp` */

DROP TABLE IF EXISTS `emp`;

CREATE TABLE `emp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empname` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `deptId` int(11) DEFAULT NULL,
  `bonus` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `emp` */

insert  into `emp`(`id`,`empname`,`salary`,`birthday`,`age`,`deptId`,`bonus`) values (2,'tom',2500.4,'2019-04-01',25,1,200),(3,'kangkang',3000.8,'2019-04-01',30,2,100),(4,'mali',3000.8,'2019-04-01',30,1,300),(5,'lily',3000.8,'2019-04-01',30,1,121),(6,'lily',3000.8,'2019-04-01',30,1,123),(7,'lily',3000.8,'2019-04-01',30,2,123),(8,'lily',3000.8,'2019-04-01',30,2,123);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
