/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : fenbushi

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-03-17 23:43:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_log`
-- ----------------------------
DROP TABLE IF EXISTS `tb_log`;
CREATE TABLE `tb_log` (
  `id` int(11) NOT NULL,
  `ip` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_log
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_status`
-- ----------------------------
DROP TABLE IF EXISTS `tb_status`;
CREATE TABLE `tb_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` bigint(15) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_status
-- ----------------------------
INSERT INTO `tb_status` VALUES ('1', '2130706433', '1');
INSERT INTO `tb_status` VALUES ('2', '3232235521', '2');
INSERT INTO `tb_status` VALUES ('3', null, null);

-- ----------------------------
-- Table structure for `tb_test`
-- ----------------------------
DROP TABLE IF EXISTS `tb_test`;
CREATE TABLE `tb_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `port` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tb_test
-- ----------------------------
INSERT INTO `tb_test` VALUES ('1', '111', '2');
INSERT INTO `tb_test` VALUES ('2', '52273', '0');
INSERT INTO `tb_test` VALUES ('3', '57016', '0');
INSERT INTO `tb_test` VALUES ('4', '57715', '0');
INSERT INTO `tb_test` VALUES ('5', '49183', '0');
INSERT INTO `tb_test` VALUES ('6', '49252', '1');
INSERT INTO `tb_test` VALUES ('7', '49259', '0');
INSERT INTO `tb_test` VALUES ('8', '49359', '0');
INSERT INTO `tb_test` VALUES ('9', '3787', '1');
INSERT INTO `tb_test` VALUES ('10', '3799', '2');
INSERT INTO `tb_test` VALUES ('11', '6461', '0');
