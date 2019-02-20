/*
Navicat MySQL Data Transfer

Source Server         : Tencent
Source Server Version : 80012
Source Host           : 118.24.115.208:3306
Source Database       : team-job

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-02-20 17:28:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `birth_type` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `head_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('dsds', '李四', '$2a$04$.U9iBCWIeOFIR9sVwuhVa.64gWVyk0krgyVNxici4FzAJaUOI77Mu', '111@qq.com', '13113', null, null, null, null, null);

-- ----------------------------
-- Table structure for weekly
-- ----------------------------
DROP TABLE IF EXISTS `weekly`;
CREATE TABLE `weekly` (
  `id` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `week` int(11) DEFAULT NULL,
  `this_week_report` varchar(255) DEFAULT NULL,
  `next_week_report` varchar(255) DEFAULT NULL,
  `feeling` varchar(255) DEFAULT NULL,
  `date_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `date_update` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weekly
-- ----------------------------
