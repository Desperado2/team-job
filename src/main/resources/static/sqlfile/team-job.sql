/*
Navicat MySQL Data Transfer

Source Server         : Tencent
Source Server Version : 80012
Source Host           : 118.24.115.208:3306
Source Database       : team-job

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-03-18 11:20:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for git_commit_logs
-- ----------------------------
DROP TABLE IF EXISTS `git_commit_logs`;
CREATE TABLE `git_commit_logs` (
  `id` varchar(36) NOT NULL,
  `project` varchar(255) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `yearweek` varchar(255) DEFAULT NULL,
  `total_add_lines` int(11) DEFAULT NULL,
  `total_del_lines` int(11) DEFAULT NULL,
  `commit_id` varchar(255) DEFAULT NULL,
  `date_commit` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `commit_comment` varchar(255) DEFAULT NULL,
  `commit_type` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for login_record
-- ----------------------------
DROP TABLE IF EXISTS `login_record`;
CREATE TABLE `login_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `browser_type` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `logout_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=293 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` varchar(255) NOT NULL,
  `project_real_name` varchar(255) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `repository_url` varchar(255) DEFAULT NULL,
  `repository_type` int(1) DEFAULT NULL,
  `document_url` varchar(255) DEFAULT NULL,
  `database_url` varchar(255) DEFAULT NULL,
  `coders` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `optioner` varchar(255) DEFAULT NULL,
  `project_date_create` datetime DEFAULT NULL,
  `date_create` datetime DEFAULT NULL,
  `date_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_template
-- ----------------------------
DROP TABLE IF EXISTS `project_template`;
CREATE TABLE `project_template` (
  `id` varchar(10) NOT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `project_manger` varchar(255) DEFAULT NULL,
  `project_level` varchar(10) DEFAULT NULL,
  `project_property` varchar(255) DEFAULT NULL,
  `project_prd` varchar(255) DEFAULT NULL,
  `group_members` varchar(255) DEFAULT NULL,
  `project_server` varchar(255) DEFAULT NULL,
  `project_front` varchar(255) DEFAULT NULL,
  `project_tester` varchar(255) DEFAULT NULL,
  `interface_review` date DEFAULT NULL,
  `case_review` date DEFAULT NULL,
  `interface_test` date DEFAULT NULL,
  `all_test` date DEFAULT NULL,
  `pre_date` date DEFAULT NULL,
  `product_date` date DEFAULT NULL,
  `content` text,
  `remark` text,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `group` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project_user
-- ----------------------------
DROP TABLE IF EXISTS `project_user`;
CREATE TABLE `project_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(10) DEFAULT NULL,
  `project_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

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
  `repository_username` varchar(255) DEFAULT NULL,
  `head_url` varchar(255) DEFAULT NULL,
  `date_create` datetime DEFAULT NULL,
  `date_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `user` VALUES ('dsds', '李四', '$2a$04$FHET/o/I4wSd.A2DqRjZa.2bd/EYocjw830EZ5vMnd8ydwVKzft4u', '111@qq.com', '131132345', '农历', '2019-02-06', 'A部门', 'AAAA工程师', 'mujj', 'image/66fe721f-65a2-4cba-bfa9-70e5fdda409f.jpg', '2019-02-22 15:26:10', '2019-02-22 15:26:10');

-- ----------------------------
-- Table structure for weekly
-- ----------------------------
DROP TABLE IF EXISTS `weekly`;
CREATE TABLE `weekly` (
  `id` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `week` varchar(11) DEFAULT NULL,
  `this_week_report` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `next_week_report` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `feeling` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `date_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `date_update` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
