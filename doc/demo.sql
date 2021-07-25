/*
 Navicat Premium Data Transfer

 Source Server         : 47.102.214.56_3306
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 47.102.214.56:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 15/06/2021 20:17:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hour_data
-- ----------------------------
DROP TABLE IF EXISTS `hour_data`;
CREATE TABLE `hour_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_hour` int(11) NULL DEFAULT NULL COMMENT 'int类型的小时时间，就像2018-09-05 11:00:00对应的int类型的数值',
  `date` int(11) NULL DEFAULT NULL COMMENT 'int类型的日期，就是2018-09-05转换成的int类型',
  `offer_id` int(11) NULL DEFAULT NULL COMMENT 'offerID，来自offer表的主键',
  `aff_id` int(10) NULL DEFAULT NULL COMMENT '下游渠道id',
  `origin_source_id` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '下游上报的原始sourceId',
  `new_source_id` int(11) NULL DEFAULT 0 COMMENT '加密后的新sourceId，默认为0',
  `event_type` tinyint(4) NULL DEFAULT 1 COMMENT 'event_id，来自于config表中的事件定义',
  `clicks` int(11) NULL DEFAULT NULL COMMENT '点击数',
  `revenue_num` int(11) NULL DEFAULT NULL COMMENT '我们接收到的数据，比如转化数、注册数',
  `payout_num` int(11) NULL DEFAULT 0 COMMENT '给下游的数据，比如转化数、注册数',
  `revenue` decimal(10, 2) NULL DEFAULT NULL COMMENT '这个小时我们的收益',
  `payout` decimal(10, 2) NULL DEFAULT NULL COMMENT '这个小时给下游的费用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_offer_date_aff_hour_1`(`offer_id`, `aff_id`, `event_type`, `date_hour`, `origin_source_id`, `new_source_id`) USING BTREE,
  INDEX `date_index`(`date`) USING BTREE,
  INDEX `report_index`(`date_hour`, `date`, `offer_id`, `aff_id`, `event_type`, `clicks`, `revenue_num`, `payout_num`, `revenue`, `payout`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '每日每小时数据表。注意价格是实时变化的' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolecode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色代码',
  `rolename` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `desc` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15314 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_menu_unique`(`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 277 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单表。默认有菜单，就有菜单下的查询权限，否则页面都无法展示' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_menu_opera
-- ----------------------------
DROP TABLE IF EXISTS `role_menu_opera`;
CREATE TABLE `role_menu_opera`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_opera_id` int(11) NOT NULL COMMENT '菜单操作id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_menu_opera_unique`(`role_id`, `menu_opera_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 510 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单操作表，角色和菜单操作的关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名，也就是登录名，默认使用邮箱',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码，md5',
  `salt` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码盐',
  `manager_id` int(11) NULL DEFAULT NULL COMMENT '所属人id，相当于parent_id，这个只针对内部用户有效',
  `is_inneruser` tinyint(1) NULL DEFAULT 1 COMMENT '是否是内部用户。1：是，0：否，指的就是affiliate用户，并且是审核通过的',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态。1：active、2：deleted',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` int(11) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_username1`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 812 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `roleid` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
