/*
 Navicat Premium Data Transfer

 Source Server         : xxx
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : xxx:3306
 Source Schema         : alarm-sc

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 06/03/2021 18:58:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vehicle_alarm_202101
-- ----------------------------
DROP TABLE IF EXISTS `vehicle_alarm_202101`;
CREATE TABLE `vehicle_alarm_202101`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
  `license_plate` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'licensePlate',
  `plate_color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'plateColor',
  `device_time` bigint(15) NULL DEFAULT NULL COMMENT 'deviceTime',
  `zone` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  INDEX `license_plate`(`license_plate`) USING BTREE,
  INDEX `plate_color`(`plate_color`) USING BTREE,
  INDEX `device_time`(`device_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'vehicle_alarm' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vehicle_alarm_202102
-- ----------------------------
DROP TABLE IF EXISTS `vehicle_alarm_202102`;
CREATE TABLE `vehicle_alarm_202102`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `license_plate` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'licensePlate',
  `plate_color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'plateColor',
  `device_time` bigint(15) NULL DEFAULT NULL COMMENT 'deviceTime',
  `zone` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  INDEX `license_plate`(`license_plate`) USING BTREE,
  INDEX `plate_color`(`plate_color`) USING BTREE,
  INDEX `device_time`(`device_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'vehicle_alarm' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vehicle_alarm_202103
-- ----------------------------
DROP TABLE IF EXISTS `vehicle_alarm_202103`;
CREATE TABLE `vehicle_alarm_202103`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `license_plate` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'licensePlate',
  `plate_color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'plateColor',
  `device_time` bigint(15) NULL DEFAULT NULL COMMENT 'deviceTime',
  `zone` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  INDEX `license_plate`(`license_plate`) USING BTREE,
  INDEX `plate_color`(`plate_color`) USING BTREE,
  INDEX `device_time`(`device_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'vehicle_alarm' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
