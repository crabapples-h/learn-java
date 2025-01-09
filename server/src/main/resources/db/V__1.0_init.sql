/*
 Navicat Premium Dump SQL

 Source Server         : crabapples-pc
 Source Server Type    : MySQL
 Source Server Version : 80402 (8.4.2)
 Source Host           : nps.crabapples.cn:30001
 Source Schema         : learn

 Target Server Type    : MySQL
 Target Server Version : 80402 (8.4.2)
 File Encoding         : 65001

 Date: 09/01/2025 17:00:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` varchar(64) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `content_type` varchar(256) DEFAULT NULL COMMENT '文件类型',
  `file_size` mediumtext COMMENT '文件大小',
  `old_name` varchar(256) DEFAULT NULL COMMENT '原文件名',
  `upload_path` varchar(256) DEFAULT NULL COMMENT '文件真实保存访问路径',
  `virtual_path` varchar(256) DEFAULT NULL COMMENT '文件虚拟访问路径',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of file_info
-- ----------------------------
BEGIN;
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('0002d5abea1b07d0561102d663368425', NULL, b'0', NULL, 'image/jpeg', '2223362', '6lT9QNO3ZQKK4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\ffbfb32386c74b39a0fa4f8a36ab9770.jpg', '/file/ffbfb32386c74b39a0fa4f8a36ab9770.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('16dcc16a160acf788e9bf19edecc888a', NULL, b'0', NULL, 'image/jpeg', '2223362', 'Z9bY9wibllxS4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\498176723cc24b71b8d8206c0f25d616.jpg', '/file/498176723cc24b71b8d8206c0f25d616.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('1856503789444427778', '2024-11-13 09:06:10', b'0', '2024-11-13 09:06:10', 'image/png', '6106908', '截屏2024-10-15 16.51.45.png', '/Users/mshe/developer/uploadPath/8617528f3c1a4425a7f827bbd6a90880.png', '/file/8617528f3c1a4425a7f827bbd6a90880.png', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('1856515442038829058', '2024-11-13 09:52:28', b'0', '2024-11-13 09:52:28', 'image/png', '4339861', '截屏2024-10-15 16.52.07.png', '/Users/mshe/developer/uploadPath/4a5ae35706114cf2896833a97a9d742b.png', '/file/4a5ae35706114cf2896833a97a9d742b.png', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('1856515637841522690', '2024-11-13 09:53:15', b'0', '2024-11-13 09:53:15', 'image/png', '6106908', '截屏2024-10-15 16.51.45.png', '/Users/mshe/developer/uploadPath/5106980077784fe88d1babe3ecf18f68.png', '/file/5106980077784fe88d1babe3ecf18f68.png', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('1856522208109686785', '2024-11-13 10:19:21', b'0', '2024-11-13 10:19:21', 'image/png', '6106908', '截屏2024-10-15 16.51.45.png', '/Users/mshe/developer/uploadPath/4f3f8f9f199d492c9cdf8861a2efecc1.png', '/file/4f3f8f9f199d492c9cdf8861a2efecc1.png', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('18b99da8ffb9087562e7aebbf0ac2df5', NULL, b'0', NULL, 'image/jpeg', '2223362', '81vNblS2xZOz4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\adcd5d335acd47629bc397cdbd912ee1.jpg', '/file/adcd5d335acd47629bc397cdbd912ee1.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('194ec94409b0f1c12ae2da16ffb14845', NULL, b'0', NULL, 'image/jpeg', '2223362', '1t7LFqIx3MFg4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\ff9f43eb1f2a4f788985d2ec169b672b.jpg', '/file/ff9f43eb1f2a4f788985d2ec169b672b.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('35acf5a3ed5ed8c1aa1625630ab25cee', NULL, b'0', NULL, 'image/jpeg', '2435559', 'nuWJboFboF9Yb13a268fe38824d5ed9a87f2251fc69a.jpg', 'd:\\uploadPath\\bdf20b08e3a64cc9ba268b2c035e382a.jpg', '/file/bdf20b08e3a64cc9ba268b2c035e382a.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('3925835b81ae7c95b830793c6f0f1abe', NULL, b'0', NULL, 'image/jpeg', '2223362', 'RZ7YyKb3uZqu4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\26598642a54a421ebc94a823578c9992.jpg', '/file/26598642a54a421ebc94a823578c9992.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('43fa402ba2f49a56204c4f21cf2d53d5', NULL, b'0', NULL, 'image/jpeg', '2223362', '9tFFwomZR03y4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\0c92ca61a2e3454e820425d9ad97bbe9.jpg', '/file/0c92ca61a2e3454e820425d9ad97bbe9.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('477a16d73acca5450f6e3c1840dd1695', NULL, b'0', NULL, 'image/jpeg', '2223362', 'hDPZdjfJ1YBQ4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\b5dc0d62573c444bb11311afd15ee998.jpg', '/file/b5dc0d62573c444bb11311afd15ee998.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('47f8932cfdad8970f0ad9962201622be', NULL, b'0', NULL, 'image/jpeg', '2435559', 'tltFHo81cvwPb13a268fe38824d5ed9a87f2251fc69a.jpg', 'd:\\uploadPath\\1d47a297b7f4496abccef38a4e0e0e40.jpg', '/file/1d47a297b7f4496abccef38a4e0e0e40.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('4bf0865183be6a0f3133bca58fa3c453', NULL, b'0', NULL, 'image/jpeg', '2223362', '5YBCcclbFgOo4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\cdd01e6c1a2f47a9941eb71599ce34ff.jpg', '/file/cdd01e6c1a2f47a9941eb71599ce34ff.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('52e3ea30aa4b68ea67046e034066aaa7', NULL, b'0', NULL, 'image/jpeg', '2223362', 'FXq8oGus5vv14611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\8e555618abee467ba1a4f4afcd1b16be.jpg', '/file/8e555618abee467ba1a4f4afcd1b16be.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('7223ab05522910f4fdf539bf1eb1d744', NULL, b'0', NULL, 'image/jpeg', '2223362', 'RPxmk5o2roAK4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\17aafd6b8d95433c813126c38eb1ea63.jpg', '/file/17aafd6b8d95433c813126c38eb1ea63.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('7ad84151f97f30532d1d87f3e615420e', NULL, b'0', NULL, 'image/jpeg', '2223362', 'GeKJiQErQvEY4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\57984f2e3ade474cad18566a87edfc61.jpg', '/file/57984f2e3ade474cad18566a87edfc61.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('90f65675811e364558284ebdd5f05288', NULL, b'0', NULL, 'image/jpeg', '6891278', 'Ec5LeBnxMPf299c0609129a09c8d6caf051ddcc1daa2.jpg', 'd:\\uploadPath\\d7e7fd895ee94740aa2656763563f74a.jpg', '/file/d7e7fd895ee94740aa2656763563f74a.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('9278ca91d8a3e2558eed8f9b81d55e07', NULL, b'0', NULL, 'image/jpeg', '6891278', 'GCOT3a2TubAh99c0609129a09c8d6caf051ddcc1daa2.jpg', 'd:\\uploadPath\\c695c93ef9d842afa32ef550f01ed182.jpg', '/file/c695c93ef9d842afa32ef550f01ed182.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('9513d6cc0af8d99d0fbd956b033f20ea', NULL, b'0', NULL, 'image/jpeg', '2223362', 'sOG0TTlvqcpK4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\6ad15adc638d484781e9b273fc841ec7.jpg', '/file/6ad15adc638d484781e9b273fc841ec7.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('989de2f28e8977698e896bf6fc94f333', NULL, b'0', NULL, 'image/jpeg', '6891278', 'N0L9xEnC66FC99c0609129a09c8d6caf051ddcc1daa2.jpg', 'd:\\uploadPath\\703f21ed7c60414996d1b3dcd4dc8bb5.jpg', '/file/703f21ed7c60414996d1b3dcd4dc8bb5.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('a2766c3a02c6e5697a1e259863e5b55c', NULL, b'0', NULL, 'image/jpeg', '2223362', 'gnjqOzz5uGMY4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\15a4868f8bd6436b9c27bcea8cfab117.jpg', '/file/15a4868f8bd6436b9c27bcea8cfab117.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('b5b712aa880f47de7c5a9c8f684f6997', NULL, b'0', NULL, 'image/jpeg', '2223362', 'G4BLBZErT1lg4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\59409f6d862a4e88bc4d8def9d7cc920.jpg', '/file/59409f6d862a4e88bc4d8def9d7cc920.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('bc050b7b1e8604680b2283c9e2e7ab20', NULL, b'0', NULL, 'image/jpeg', '2223362', 'dxUVl01AtuEu4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\0852ea091e364bbbbbfada5217d68869.jpg', '/file/0852ea091e364bbbbbfada5217d68869.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('bd89f92f45a678b303fda895d519dd7c', NULL, b'0', NULL, 'image/jpeg', '2223362', '5CPr58WOsEUe4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\c5415f95c79d4d03ae7985951a1b61fb.jpg', '/file/c5415f95c79d4d03ae7985951a1b61fb.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('bf4a93b1ff7ffcbe48c4569ab9c59fde', NULL, b'0', NULL, 'image/jpeg', '2223362', 'bCifNfncEK344611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\7013a7a28cfd4b96a5ffb76c0949dce6.jpg', '/file/7013a7a28cfd4b96a5ffb76c0949dce6.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('c2b94c5978830f72effc3f7bc5c8d210', NULL, b'0', NULL, 'image/jpeg', '2223362', 'PQKo9VuyoZNd4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\40654dbf49d449d3bae4663e6ea57743.jpg', '/file/40654dbf49d449d3bae4663e6ea57743.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('c71bd74e8ffeab6f1a69c1d251bf5f43', NULL, b'0', NULL, 'image/jpeg', '2223362', 'hnHW8GszdDTm4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\a802c130d73347c5b5e3bc1e6842d974.jpg', '/file/a802c130d73347c5b5e3bc1e6842d974.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('cba58c426d2ece5c6a76db5b6cf352bf', NULL, b'0', NULL, 'image/jpeg', '2223362', 'DFQK15IAmsIc4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\a83fbd6577f944d6b5f0bb22794990db.jpg', '/file/a83fbd6577f944d6b5f0bb22794990db.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('d0b3803f0d8565bc3b82e1ee10f940ac', NULL, b'0', NULL, 'image/jpeg', '2223362', '3JYpKIWpjfQJ4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\d044cebece04452cb1d7e8d4b123202a.jpg', '/file/d044cebece04452cb1d7e8d4b123202a.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('d37e20d8751d53855d6883e11cbd9851', NULL, b'0', NULL, 'image/jpeg', '2223362', 'hoKUGeBEB5J34611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\3709bdd5758f4ff9acf99f264b73b44e.jpg', '/file/3709bdd5758f4ff9acf99f264b73b44e.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('ee2ea82612de439ee09ecb7abad93eb4', NULL, b'0', NULL, 'image/jpeg', '2223362', 'r0xBEKXRrPjV4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\a556719fb22b44d394bede6470c0e828.jpg', '/file/a556719fb22b44d394bede6470c0e828.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('eef183eafa1a322b6a76b46853d6fcd9', NULL, b'0', NULL, 'image/jpeg', '2223362', 'MXuXsgrktJGb4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\f6edfc6eb08b4080b9e32934efc291d8.jpg', '/file/f6edfc6eb08b4080b9e32934efc291d8.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('f3f47209ab3935e299e87619874002eb', NULL, b'0', NULL, 'image/jpeg', '6891278', 'oKuu5iq2xRzU99c0609129a09c8d6caf051ddcc1daa2.jpg', 'd:\\uploadPath\\31fd0921145b41d4a077822c3069c949.jpg', '/file/31fd0921145b41d4a077822c3069c949.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('fbd21b9e0bac296b9fa9643e73cb5c78', NULL, b'0', NULL, 'image/jpeg', '2223362', 'OeBclCkR0OJJ4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\64f8f55e2b04417880a0ba5b717eb285.jpg', '/file/64f8f55e2b04417880a0ba5b717eb285.jpg', NULL);
INSERT INTO `file_info` (`id`, `create_time`, `del_flag`, `update_time`, `content_type`, `file_size`, `old_name`, `upload_path`, `virtual_path`, `create_by`) VALUES ('fe6017af428935b9e547d36eab9ba780', NULL, b'0', NULL, 'image/jpeg', '2223362', '051c40mHclEU4611082a5b5b14a232a20d04f7b0f788.jpg', 'd:\\uploadPath\\f0c971e6a28a4d86bd693fcb88851cec.jpg', '/file/f0c971e6a28a4d86bd693fcb88851cec.jpg', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(64) NOT NULL,
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `code` varchar(64) NOT NULL COMMENT '编码',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `sort` int DEFAULT '-1' COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_index` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `code`, `name`, `sort`) VALUES ('38f8d2f8bcd22ec5737b1b5e6921bcc5', NULL, NULL, b'0', NULL, 'gender', '性别', -1);
INSERT INTO `sys_dict` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `code`, `name`, `sort`) VALUES ('7524050066eff80bbee02a36327f4380', NULL, NULL, b'1', NULL, '21', '11', -1);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `id` varchar(64) NOT NULL,
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `dict_code` varchar(64) NOT NULL COMMENT '字典编码',
  `text` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `value` varchar(64) NOT NULL COMMENT '值',
  PRIMARY KEY (`id`),
  KEY `fk_dict_code` (`dict_code`),
  CONSTRAINT `fk_dict_code` FOREIGN KEY (`dict_code`) REFERENCES `sys_dict` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_item` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `dict_code`, `text`, `value`) VALUES ('67216ac030248acd91affddcf0f0001c', NULL, NULL, 1, '2024-11-12 17:54:41', 'gender', '测试', '4');
INSERT INTO `sys_dict_item` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `dict_code`, `text`, `value`) VALUES ('812d2bfc1950da07d0680562b3016d3e', NULL, NULL, 0, NULL, 'gender', '男', '0');
INSERT INTO `sys_dict_item` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `dict_code`, `text`, `value`) VALUES ('83a5d952e6bbad3bf41106c52949333c', NULL, NULL, 0, NULL, 'gender', '女', '1');
INSERT INTO `sys_dict_item` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `dict_code`, `text`, `value`) VALUES ('d97d32b988be22c772e4e5b6ab9b8be8', NULL, NULL, 0, NULL, 'gender', '未知', '3');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(64) NOT NULL,
  `pid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `icon` varchar(64) DEFAULT NULL COMMENT '菜单图标',
  `menus_type` int DEFAULT '0' COMMENT '菜单类型 1:菜单 2:按钮 3:超链接',
  `name` varchar(64) DEFAULT NULL COMMENT '菜单名',
  `permission` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '授权标识',
  `sort` int DEFAULT '-1' COMMENT '菜单排序号',
  `file_path` varchar(64) DEFAULT NULL COMMENT '文件路径',
  `path` varchar(64) DEFAULT NULL COMMENT '浏览器路径',
  `link` varchar(64) DEFAULT NULL COMMENT '超链接地址',
  `show_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否隐藏 0:否 1:是',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `FK7n06vwmm7nis42afede48dgyv` (`pid`),
  CONSTRAINT `FK7n06vwmm7nis42afede48dgyv` FOREIGN KEY (`pid`) REFERENCES `sys_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('00eed5e8-a8df-4f6d-b758-c6bf8af47a37', NULL, 'shuishoufu', 3, '超链接', '', 5, NULL, NULL, 'http://www.baidu.com', 0, NULL, '2023-09-01 01:39:22', '2024-11-13 20:09:50', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('2629775283efd8d02faae13430e5f77c', NULL, NULL, 1, 'SSE测试', NULL, 2, '2', '1', NULL, 0, NULL, NULL, '2024-11-13 22:04:15', 1);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('28da4001-5f34-4174-a29d-151b5567ff3d', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8', '', 2, '删除菜单', 'sys:menus:del', 5, NULL, NULL, NULL, 0, NULL, '2022-06-10 15:26:28', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('3aaf0b5c-5d08-4e72-8ab3-869388525b6d', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8', '', 2, '添加子菜单', 'sys:menus:add-children', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:09:59', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('41a43e3792368d3daa3f822c5def5d02', NULL, 'bold', 1, '蓝牙测试', NULL, 4, 'example/BluetoothExample', '/example/BluetoothExample', NULL, 0, NULL, '2024-11-15 23:46:25', '2024-11-15 23:46:25', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('47ef4177-39ee-40ff-b587-d108ca4db764', 'f2e2258b-0ef5-4175-948e-dfdec4185785', '', 2, '删除角色', 'sys:roles:del', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:11:52', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('4b4b42ec61847ba3feacab546cd1f7c9', NULL, 'twitter', 1, '蓝牙传输测试', NULL, 5, 'example/DGLabExample', '/example/DGLabExample', NULL, 0, NULL, '2024-11-15 23:47:17', '2024-12-24 22:51:23', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('4bfb47b0-1235-40da-ab56-8fb5863957fa', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '', 2, '锁定用户', 'sys:user:lock', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:08:11', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '828e0ec3-69c1-4eac-8d19-339f7b3fb917', 'shuishoufu', 1, '用户管理', '', 5, 'manage/sys-user/list', '/manage/user-list', NULL, 0, NULL, '2023-09-01 01:39:17', '2024-11-13 20:09:52', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('54bc1606-8c0e-4824-9e37-d25744d10550', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '', 2, '编辑用户', 'sys:user:edit', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:10:56', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('5777a2e5314ac1c3fd95a681550e317d', NULL, 'diaodai', 1, 'SSE测试', NULL, 2, 'example/SseExample', '/sse/example', NULL, 0, NULL, '2024-11-13 23:16:11', '2024-11-13 23:27:09', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('73e3102d-5319-45c6-a3d2-156524aed6f1', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8', '', 2, '编辑菜单', 'sys:menus:edit', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:10:22', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('8192966aa5b50cd0e353142bfc36e69d', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', NULL, 2, '修改密码', 'sys:user:resetpwd', -1, NULL, NULL, NULL, 0, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('828e0ec3-69c1-4eac-8d19-339f7b3fb917', NULL, '<a-icon type=\"setting\" />', 1, '系统管理', '', 9, NULL, NULL, NULL, 0, NULL, '2021-06-02 21:05:24', '2023-09-10 02:40:36', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('8a475740-d314-4885-aebd-100b22ddf625', 'f2e2258b-0ef5-4175-948e-dfdec4185785', '', 2, '添加角色', 'sys:roles:add', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:10:39', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('950cc9fc-08e0-434e-ac57-4f3d66680ef0', NULL, 'HTML-fill', 1, '欢迎页面', '', 1, 'manage/Welcome', '/manage/welcome', NULL, 0, NULL, '2023-09-01 01:39:20', NULL, 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('b131f56c456839af83b88aa177b8c016', NULL, 'xiangji', 1, 'websocket测试', NULL, 3, 'example/WebSocketExample', '/example/websocket', NULL, 0, NULL, '2024-11-14 21:27:38', '2024-11-15 23:26:49', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('b5ae23e0-c026-443f-b316-8f67eafa8fd8', '828e0ec3-69c1-4eac-8d19-339f7b3fb917', '', 1, '菜单管理', '', 5, 'manage/sys-menu/list', '/manage/menu-list', NULL, 0, NULL, '2021-06-02 22:59:17', NULL, 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('c717739f-cef5-4b02-bbd5-f865fc1c2061', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '', 2, '添加用户', 'sys:user:add', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:07:37', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('cb983e73-af3a-4369-8c47-f83984642df3', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8', '', 2, '添加菜单', 'sys:menus:add', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:11:32', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('dc555c47937d5fad9eabd6f52e97445a', '828e0ec3-69c1-4eac-8d19-339f7b3fb917', '', 1, '字典管理', '', 1, 'manage/sys-dict/list', '/manage/dict-list', NULL, 0, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('dee9ad90-08e2-4792-8c09-fe6d8b5d04cb', 'f2e2258b-0ef5-4175-948e-dfdec4185785', '', 2, '编辑角色', 'sys:roles:edit', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:11:16', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('df83b0c9-7d96-448a-a337-8e85664dce0e', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '', 2, '删除用户', 'sys:user:del', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:07:53', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('eefb0cb6-a1d5-40e9-922c-e5ada9ac510e', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '', 2, '解锁用户', 'sys:user:unlock', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:08:22', '2023-09-10 02:36:57', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('f2e2258b-0ef5-4175-948e-dfdec4185785', '828e0ec3-69c1-4eac-8d19-339f7b3fb917', '', 1, '角色管理', '', 5, 'manage/sys-role/list', '/manage/role-list', NULL, 0, NULL, '2021-06-02 23:00:22', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色名称',
  `permission_list` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '权限列表',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `name`, `permission_list`, `create_time`, `del_flag`, `update_time`, `create_by`) VALUES ('01841a2e-309f-4e3a-9609-e2574695dbe3', '超链接', '[\"sys:menus:del\", \"sys:menus:add-children\", \"sys:roles:del\", \"sys:user:lock\", \"sys:user:edit\", \"sys:menus:edit\", \"sys:roles:add\", \"sys:user:add\", \"sys:menus:add\", \"sys:roles:edit\", \"sys:user:del\", \"sys:user:unlock\"]', '2023-09-09 21:30:32', 0, NULL, 'admin');
INSERT INTO `sys_role` (`id`, `name`, `permission_list`, `create_time`, `del_flag`, `update_time`, `create_by`) VALUES ('12578678-e523-4448-9ccb-c4ba6ef01f96', '欢迎页面', '[\"sys:menus:del\", \"sys:menus:add-children\", \"sys:roles:del\", \"sys:user:lock\", \"sys:user:edit\", \"sys:menus:edit\", \"sys:roles:add\", \"sys:user:add\", \"sys:menus:add\", \"sys:roles:edit\", \"sys:user:del\", \"sys:user:unlock\"]', '2023-08-30 23:16:41', 0, NULL, 'admin');
INSERT INTO `sys_role` (`id`, `name`, `permission_list`, `create_time`, `del_flag`, `update_time`, `create_by`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', '用户管理', '[\"sys:menus:del\", \"sys:menus:add-children\", \"sys:roles:del\", \"sys:user:lock\", \"sys:user:edit\", \"sys:menus:edit\", \"sys:roles:add\", \"sys:user:add\", \"sys:menus:add\", \"sys:roles:edit\", \"sys:user:del\", \"sys:user:unlock\"]', '2023-08-30 23:30:10', 0, NULL, 'admin');
INSERT INTO `sys_role` (`id`, `name`, `permission_list`, `create_time`, `del_flag`, `update_time`, `create_by`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '系统管理员', '[\"sys:menus:del\", \"sys:menus:add-children\", \"sys:roles:del\", \"sys:user:lock\", \"sys:user:edit\", \"sys:menus:edit\", \"sys:roles:add\", \"sys:user:add\", \"sys:menus:add\", \"sys:roles:edit\", \"sys:user:del\", \"sys:user:unlock\"]', '2023-09-09 21:30:30', 0, '2024-12-24 23:22:49', 'admin');
INSERT INTO `sys_role` (`id`, `name`, `permission_list`, `create_time`, `del_flag`, `update_time`, `create_by`) VALUES ('a15001d9-db9e-44bb-9427-0b3836f2267c', '角色1', '[\"sys:menus:del\", \"sys:menus:add-children\", \"sys:roles:del\", \"sys:user:lock\", \"sys:user:edit\", \"sys:menus:edit\", \"sys:roles:add\", \"sys:user:add\", \"sys:menus:add\", \"sys:roles:edit\", \"sys:user:del\", \"sys:user:unlock\"]', '2023-08-30 23:15:36', 0, NULL, 'admin');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menus`;
CREATE TABLE `sys_role_menus` (
  `role_id` varchar(64) NOT NULL COMMENT '角色id',
  `menu_id` varchar(64) NOT NULL COMMENT '菜单id',
  KEY `fk_r_m_menu_id` (`menu_id`),
  KEY `fk_r_m_role_id` (`role_id`),
  CONSTRAINT `fk_r_m_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_r_m_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_role_menus
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('01841a2e-309f-4e3a-9609-e2574695dbe3', '00eed5e8-a8df-4f6d-b758-c6bf8af47a37');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('12578678-e523-4448-9ccb-c4ba6ef01f96', '950cc9fc-08e0-434e-ac57-4f3d66680ef0');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('a15001d9-db9e-44bb-9427-0b3836f2267c', '00eed5e8-a8df-4f6d-b758-c6bf8af47a37');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', '4bfb47b0-1235-40da-ab56-8fb5863957fa');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', '54bc1606-8c0e-4824-9e37-d25744d10550');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', '8192966aa5b50cd0e353142bfc36e69d');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', 'c717739f-cef5-4b02-bbd5-f865fc1c2061');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', 'df83b0c9-7d96-448a-a337-8e85664dce0e');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', 'eefb0cb6-a1d5-40e9-922c-e5ada9ac510e');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', '28da4001-5f34-4174-a29d-151b5567ff3d');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', '3aaf0b5c-5d08-4e72-8ab3-869388525b6d');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', '73e3102d-5319-45c6-a3d2-156524aed6f1');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', 'cb983e73-af3a-4369-8c47-f83984642df3');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'dee9ad90-08e2-4792-8c09-fe6d8b5d04cb');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'f2e2258b-0ef5-4175-948e-dfdec4185785');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '47ef4177-39ee-40ff-b587-d108ca4db764');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '8a475740-d314-4885-aebd-100b22ddf625');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '4bfb47b0-1235-40da-ab56-8fb5863957fa');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '8192966aa5b50cd0e353142bfc36e69d');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'c717739f-cef5-4b02-bbd5-f865fc1c2061');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'df83b0c9-7d96-448a-a337-8e85664dce0e');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'eefb0cb6-a1d5-40e9-922c-e5ada9ac510e');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '28da4001-5f34-4174-a29d-151b5567ff3d');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '3aaf0b5c-5d08-4e72-8ab3-869388525b6d');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '73e3102d-5319-45c6-a3d2-156524aed6f1');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'cb983e73-af3a-4369-8c47-f83984642df3');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'dc555c47937d5fad9eabd6f52e97445a');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '00eed5e8-a8df-4f6d-b758-c6bf8af47a37');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '950cc9fc-08e0-434e-ac57-4f3d66680ef0');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '5777a2e5314ac1c3fd95a681550e317d');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'b131f56c456839af83b88aa177b8c016');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '41a43e3792368d3daa3f822c5def5d02');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '4b4b42ec61847ba3feacab546cd1f7c9');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '54bc1606-8c0e-4824-9e37-d25744d10550');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '828e0ec3-69c1-4eac-8d19-339f7b3fb917');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL,
  `age` int NOT NULL DEFAULT '0' COMMENT '年龄',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '用户状态标记 0:正常 1:禁用',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `mail` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(15) DEFAULT NULL COMMENT '电话',
  `gender` tinyint DEFAULT NULL COMMENT '性别',
  `avatar` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '最后操作用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_51bvuyvihefoh4kp5syh2jpi4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `age`, `name`, `password`, `status`, `username`, `mail`, `phone`, `gender`, `avatar`, `tenant_id`, `del_flag`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('001', 20, 'admin', '21232f297a57a5a743894a0e4a801fc3', b'0', 'admin', '12345@qq.com', '13111111111', 1, '/file/4f3f8f9f199d492c9cdf8861a2efecc1.png', '0', 0, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` (`id`, `age`, `name`, `password`, `status`, `username`, `mail`, `phone`, `gender`, `avatar`, `tenant_id`, `del_flag`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('002', 0, 'test', '{bcrypt}$2a$10$bR7K0c0.cZ8tyvcpTl5dU.nmjr.tWGfWmNo1DDHezlDGQE.ujBu1S', b'0', 'test', NULL, NULL, NULL, NULL, '0', 0, '2024-12-17 00:49:48', NULL, '2024-12-17 01:10:49', NULL);
INSERT INTO `sys_user` (`id`, `age`, `name`, `password`, `status`, `username`, `mail`, `phone`, `gender`, `avatar`, `tenant_id`, `del_flag`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('6e4d94b34fe44d7cd79fd199a8a62b3c', 11, '111', NULL, b'0', '111', '111', '111', NULL, NULL, '0', 0, NULL, NULL, '2024-11-11 22:21:59', NULL);
INSERT INTO `sys_user` (`id`, `age`, `name`, `password`, `status`, `username`, `mail`, `phone`, `gender`, `avatar`, `tenant_id`, `del_flag`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('d69f043524094cf90f60a6c55e8c3cfa', 18, '222', '698d51a19d8a121ce581499d7b701668', b'0', '222', '222', '11111111', 2, NULL, '0', 0, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles` (
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  KEY `fk_u_r_user_id` (`user_id`),
  KEY `fk_u_r_role_id` (`role_id`),
  CONSTRAINT `fk_u_r_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_u_r_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_user_roles
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('6e4d94b34fe44d7cd79fd199a8a62b3c', '01841a2e-309f-4e3a-9609-e2574695dbe3');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('d69f043524094cf90f60a6c55e8c3cfa', '01841a2e-309f-4e3a-9609-e2574695dbe3');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('d69f043524094cf90f60a6c55e8c3cfa', '12578678-e523-4448-9ccb-c4ba6ef01f96');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('d69f043524094cf90f60a6c55e8c3cfa', '1d528a1b-15c9-4929-b998-b70ce1333d9c');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('001', '37276b55-dc6a-49d5-9ae3-271e1783d442');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('001', '01841a2e-309f-4e3a-9609-e2574695dbe3');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('001', '12578678-e523-4448-9ccb-c4ba6ef01f96');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
