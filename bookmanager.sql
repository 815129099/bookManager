/*
Navicat MySQL Data Transfer

Source Server         : mysql80
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : bookmanager

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-12-13 10:46:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `bookId` varchar(100) NOT NULL,
  `bookName` varchar(100) NOT NULL,
  `bookNumber` int(11) NOT NULL,
  `lendNumber` int(40) NOT NULL,
  `bookLocation` varchar(40) NOT NULL,
  `bookState` int(11) NOT NULL DEFAULT 1 COMMENT '1在库；0出库',
  PRIMARY KEY (`id`),
  KEY `bookId` (`bookId`),
  KEY `bookName` (`bookName`)
) ENGINE=InnoDB AUTO_INCREMENT=1951 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for inform
-- ----------------------------
DROP TABLE IF EXISTS `inform`;
CREATE TABLE `inform` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `geNumber` varchar(20) NOT NULL,
  `title` varchar(40) NOT NULL,
  `detail` varchar(200) NOT NULL,
  `createTime` date DEFAULT NULL,
  `updateTime` date DEFAULT NULL,
  `informState` int(11) DEFAULT NULL COMMENT '0正常 1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `bookId` varchar(40) NOT NULL,
  `geName` varchar(40) NOT NULL,
  `geNumber` varchar(40) NOT NULL,
  `bookName` varchar(100) NOT NULL,
  `state` varchar(40) NOT NULL COMMENT '归还，未还',
  `lendTime` date DEFAULT NULL,
  `backTime` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `applyTime` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `geNumber` varchar(100) NOT NULL,
  `geName` varchar(100) NOT NULL,
  `password` varchar(40) NOT NULL,
  `role` varchar(40) NOT NULL COMMENT '角色：admin，user，visitor',
  `authority` varchar(40) NOT NULL COMMENT '角色：admin，user，visitor',
  `userState` varchar(100) NOT NULL COMMENT '用户状态：0有效，1无效',
  `createTime` date DEFAULT NULL,
  `updateTime` date DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
