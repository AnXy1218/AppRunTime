/*
Navicat MySQL Data Transfer

Source Server         : host
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : school

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-04-03 18:20:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `Cno` char(4) NOT NULL,
  `Cname` varchar(20) NOT NULL,
  `Cpno` char(4) DEFAULT NULL,
  `Ccredit` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`Cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('001', '语文', null, '3');
INSERT INTO `course` VALUES ('002', '数学', '001', '3');

-- ----------------------------
-- Table structure for sc
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc` (
  `Sno` char(6) NOT NULL,
  `Cno` char(4) NOT NULL,
  `Grade` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`Sno`,`Cno`),
  KEY `Cno_fk` (`Cno`),
  CONSTRAINT `Cno_fk` FOREIGN KEY (`Cno`) REFERENCES `course` (`Cno`),
  CONSTRAINT `Sno_fk` FOREIGN KEY (`Sno`) REFERENCES `student` (`Sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sc
-- ----------------------------
INSERT INTO `sc` VALUES ('001', '001', '57.00');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `Sno` char(6) NOT NULL,
  `Sname` varchar(8) NOT NULL,
  `Ssex` enum('男','女') NOT NULL DEFAULT '男',
  `Sage` smallint(6) DEFAULT NULL,
  `Sdept` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('001', '张同学', '男', '18', '土木工程系');
INSERT INTO `student` VALUES ('002', '李同学', '男', '17', '电信系');
INSERT INTO `student` VALUES ('003', '赵同学', '女', '18', '土木工程系');
