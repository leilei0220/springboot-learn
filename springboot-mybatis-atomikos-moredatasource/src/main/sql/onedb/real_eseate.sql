/*
 Navicat Premium Data Transfer

 Source Server         : leilei
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : one

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 02/03/2020 11:29:07
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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of real_eseate
-- ----------------------------
INSERT INTO `real_eseate` VALUES (1, '龙湖1', '成都', '三室', 127, '2019-08-10 00:00:00', 1);
INSERT INTO `real_eseate` VALUES (2, '龙湖2', '成都', '四室一厅', 555, '2019-08-25 00:00:00', 1);
INSERT INTO `real_eseate` VALUES (3, '龙湖3', '成都', '五室一厅', 126, '2019-08-31 13:03:20', 1);
INSERT INTO `real_eseate` VALUES (4, '樊城1', '德阳', '三室', 127, '2019-08-30 13:03:51', 2);
INSERT INTO `real_eseate` VALUES (5, '樊城2', '德阳', '四室一厅', 126, '2019-08-27 13:04:17', 2);
INSERT INTO `real_eseate` VALUES (6, '樊城3', '德阳', '五室一厅', 555, '2019-08-31 13:05:16', 2);
INSERT INTO `real_eseate` VALUES (7, '南城都汇', '成都武侯', '四室两厅', 156, '2020-03-02 10:37:10', 16);
INSERT INTO `real_eseate` VALUES (8, '英汇三期', '群音荟萃', '青年公寓', 120, '2020-03-02 10:44:14', 1);

SET FOREIGN_KEY_CHECKS = 1;
