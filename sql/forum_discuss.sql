/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : clegstdehalearning

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 07/03/2020 18:23:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for forum_discuss
-- ----------------------------
DROP TABLE IF EXISTS `forum_discuss`;
CREATE TABLE `forum_discuss`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `user_id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联创建用户ID',
  `message_id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联消息ID',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '论坛-评论表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
