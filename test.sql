/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-01-29 16:32:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_code` varchar(20) NOT NULL COMMENT '登录名',
  `user_name` varchar(20) NOT NULL COMMENT '姓名',
  `pwd` varchar(64) NOT NULL COMMENT '密码',
  `is_account_not_expired` tinyint(4) NOT NULL DEFAULT '1' COMMENT '账号未过期',
  `is_account_not_locked` tinyint(4) NOT NULL DEFAULT '1' COMMENT '账号未锁定',
  `is_credentials_non_expired` tinyint(4) NOT NULL DEFAULT '1' COMMENT '密码未过期',
  `is_enabled` tinyint(4) NOT NULL DEFAULT '1' COMMENT '账号被启用',
  `age` tinyint(3) unsigned NOT NULL COMMENT '年龄',
  `sex` tinyint(4) NOT NULL COMMENT '性别 0女 1男',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '管理员', '$2a$10$wIUL9uNHNWHJSuxPY.svsuqYV2KUQ/mx5SlKVH/4UxXRzVAihIo7i', '1', '1', '1', '1', '18', '0');
INSERT INTO `t_user` VALUES ('2', 'user', '用户', '$2a$10$wIUL9uNHNWHJSuxPY.svsuqYV2KUQ/mx5SlKVH/4UxXRzVAihIo7i', '1', '1', '1', '1', '18', '0');
