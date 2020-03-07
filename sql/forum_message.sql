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

 Date: 07/03/2020 18:23:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for forum_message
-- ----------------------------
DROP TABLE IF EXISTS `forum_message`;
CREATE TABLE `forum_message`  (
  `id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `user_id` varchar(38) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联创建用户ID',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `praise_count` int(5) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `discuss_count` int(5) NOT NULL COMMENT '评论数',
  `collect_count` int(5) NOT NULL DEFAULT 0 COMMENT '收藏数',
  `forward_count` int(5) NOT NULL DEFAULT 0 COMMENT '转发数',
  `create_date_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '论坛-消息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
