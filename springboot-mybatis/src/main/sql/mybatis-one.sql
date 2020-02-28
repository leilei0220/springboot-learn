/*
 Navicat Premium Data Transfer

 Source Server         : leilei
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : mybatis-one

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 24/02/2020 17:52:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for real_eseate
-- ----------------------------
DROP TABLE IF EXISTS `real_eseate`;
CREATE TABLE `real_eseate`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `house_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `area` int(10) NULL DEFAULT NULL,
  `build_time` datetime(0) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of real_eseate
-- ----------------------------
INSERT INTO `real_eseate` VALUES (1, '龙湖1', '成都', '三室', 127, '2019-08-10 00:00:00', 1);
INSERT INTO `real_eseate` VALUES (2, '龙湖2', '成都', '四室一厅', 555, '2019-08-25 00:00:00', 1);
INSERT INTO `real_eseate` VALUES (3, '龙湖3', '成都', '五室一厅', 126, '2019-08-31 13:03:20', 1);
INSERT INTO `real_eseate` VALUES (4, '樊城1', '德阳', '三室', 127, '2019-08-30 13:03:51', 2);
INSERT INTO `real_eseate` VALUES (5, '樊城2', '德阳', '四室一厅', 126, '2019-08-27 13:04:17', 2);
INSERT INTO `real_eseate` VALUES (6, '樊城3', '德阳', '五室一厅', 555, '2019-08-31 13:05:16', 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `card_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '雷', '123456789123456789');
INSERT INTO `user` VALUES (2, '胡', '987654321987654321');

SET FOREIGN_KEY_CHECKS = 1;
