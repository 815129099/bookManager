/*
Navicat MySQL Data Transfer

Source Server         : mysql80
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : bookmanager

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-09-07 16:38:03
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '00001', '博览群书 2005 5', '1', '0', 'A-1-1');
INSERT INTO `book` VALUES ('2', '00002', '博览群书 2005 6', '1', '0', 'A-1-1');
INSERT INTO `book` VALUES ('3', '00003', '博览群书 2005 7\r\n博览群书 2005 7', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('4', '00004', '博览群书 2005 8', '1', '0', 'A-1-1');
INSERT INTO `book` VALUES ('5', '00005', '博览群书 2005 9', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('6', '00006', '博览群书 2006 5', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('7', '00007', '博览群书 2006 8', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('8', '00008', '博览群书 2006 10', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('9', '00009', '特战精锐 揭秘俄罗斯特种部队', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('10', '00010', '士兵突击', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('11', '00011', '中国诗学 第七辑', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('12', '00012', '毛泽东 诗词书法赏析', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('13', '00013', '鲁迅散文全集', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('14', '00014', '童年 在人间 我的大学', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('15', '00015', '小妇人', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('16', '00016', '交际花盛衰记', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('17', '00017', '三毛作品集-温柔的夜', '1', '1', 'A-1-1');
INSERT INTO `book` VALUES ('18', '00019', '小妇人0', '1', '1', 'A-2-2');
