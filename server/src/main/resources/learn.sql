/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80034 (8.0.34)
 Source Host           : localhost:3306
 Source Schema         : learn

 Target Server Type    : MySQL
 Target Server Version : 80034 (8.0.34)
 File Encoding         : 65001

 Date: 22/10/2023 17:30:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `content_type` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文件类型',
  `file_size` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '文件大小',
  `old_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '原文件名',
  `upload_path` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文件真实保存访问路径',
  `virtual_path` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文件虚拟访问路径',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of file_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标记',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编码',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `sort` int DEFAULT '-1' COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `code`, `name`, `sort`) VALUES ('36de972c62a611b58879de66e67dd1ce', NULL, NULL, b'1', NULL, '11', '测试', 2222);
INSERT INTO `sys_dict` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `code`, `name`, `sort`) VALUES ('e16ca09bdc992072f6af1403000d3036', NULL, NULL, b'1', NULL, 'ee', '33', -1);
INSERT INTO `sys_dict` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `code`, `name`, `sort`) VALUES ('fb2b5a9aff5b6fe5ceb1887882b4eaf7', NULL, NULL, b'0', NULL, 'gender', '性别', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `dict_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典编码',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `value` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '值',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_dict_code` (`dict_code`) USING BTREE,
  CONSTRAINT `fk_dict_code` FOREIGN KEY (`dict_code`) REFERENCES `sys_dict` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_item` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `dict_code`, `code`, `value`) VALUES ('07464485b8b91e16b9831250cbef781d', NULL, NULL, 1, '2023-10-22 17:18:45', 'gender', '3', '测试');
INSERT INTO `sys_dict_item` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `dict_code`, `code`, `value`) VALUES ('439dfcb59e8dc2b1d30b0c5e4b6f1655', NULL, NULL, 0, NULL, 'gender', '2', '保密');
INSERT INTO `sys_dict_item` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `dict_code`, `code`, `value`) VALUES ('6f0a1feff80fcaa3d3c232d3295df70b', NULL, NULL, 0, NULL, 'gender', '0', '男');
INSERT INTO `sys_dict_item` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `dict_code`, `code`, `value`) VALUES ('74ebd427f74d4e637ca48f443e0b338f', NULL, NULL, 0, NULL, 'gender', '1', '女');
INSERT INTO `sys_dict_item` (`id`, `create_by`, `create_time`, `del_flag`, `update_time`, `dict_code`, `code`, `value`) VALUES ('8e0efb4f57a4299b1120508d93ef1e6e', NULL, NULL, 1, '2023-10-22 17:21:11', 'gender', '44', '44');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单图标',
  `menus_type` int DEFAULT '0' COMMENT '菜单类型 1:菜单 2:按钮 3:超链接',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单名',
  `permission` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '授权标识',
  `sort` int DEFAULT '-1' COMMENT '菜单排序号',
  `file_path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文件路径',
  `path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '浏览器路径',
  `link` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '超链接地址',
  `show_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否隐藏 0:否 1:是',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK7n06vwmm7nis42afede48dgyv` (`pid`) USING BTREE,
  CONSTRAINT `FK7n06vwmm7nis42afede48dgyv` FOREIGN KEY (`pid`) REFERENCES `sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('00eed5e8-a8df-4f6d-b758-c6bf8af47a37', NULL, '<a-icon type=\"fullscreen-exit\" />', 3, '超链接', '', 5, NULL, NULL, 'http://www.baidu.com', 0, NULL, '2023-09-01 01:39:22', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('28da4001-5f34-4174-a29d-151b5567ff3d', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8', '', 2, '删除菜单', 'sys:menus:del', 5, NULL, NULL, NULL, 0, NULL, '2022-06-10 15:26:28', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('3aaf0b5c-5d08-4e72-8ab3-869388525b6d', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8', '', 2, '添加子菜单', 'sys:menus:add-children', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:09:59', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('47ef4177-39ee-40ff-b587-d108ca4db764', 'f2e2258b-0ef5-4175-948e-dfdec4185785', '', 2, '删除角色', 'sys:roles:del', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:11:52', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('4b35f66f0eb0523915899ed9a8f3dc7a', '828e0ec3-69c1-4eac-8d19-339f7b3fb917', '', 1, '测试1', '', -1, '', '', NULL, 0, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('4bfb47b0-1235-40da-ab56-8fb5863957fa', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '', 2, '锁定用户', 'sys:user:lock', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:08:11', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '828e0ec3-69c1-4eac-8d19-339f7b3fb917', '<a-icon type=\"user\" />', 1, '用户管理', '', 5, 'manage/sys-user/list', '/manage/users-list', NULL, 0, NULL, '2023-09-01 01:39:17', NULL, 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('54bc1606-8c0e-4824-9e37-d25744d10550', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '', 2, '编辑用户', 'sys:user:edit', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:10:56', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('73e3102d-5319-45c6-a3d2-156524aed6f1', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8', '', 2, '编辑菜单', 'sys:menus:edit', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:10:22', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('828e0ec3-69c1-4eac-8d19-339f7b3fb917', NULL, '<a-icon type=\"setting\" />', 1, '系统管理', '', 9, NULL, NULL, NULL, 0, NULL, '2021-06-02 21:05:24', '2023-09-10 02:40:36', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('8a475740-d314-4885-aebd-100b22ddf625', 'f2e2258b-0ef5-4175-948e-dfdec4185785', '', 2, '添加角色', 'sys:roles:add', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:10:39', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('950cc9fc-08e0-434e-ac57-4f3d66680ef0', NULL, '<a-icon type=\"fullscreen-exit\" />', 1, '欢迎页面', '', 1, 'manage/Welcome', '/manage/welcome', NULL, 0, NULL, '2023-09-01 01:39:20', '2023-09-10 02:40:35', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('b5ae23e0-c026-443f-b316-8f67eafa8fd8', '828e0ec3-69c1-4eac-8d19-339f7b3fb917', '<a-icon type=\"menu-unfold\" />', 1, '菜单管理', '', 5, 'manage/sys-menu/list', '/manage/menus-list', NULL, 0, NULL, '2021-06-02 22:59:17', NULL, 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('ba3afebe16a31e7874409629a70663c7', '950cc9fc-08e0-434e-ac57-4f3d66680ef0', '', 1, '测试1', '', -1, '', '', NULL, 0, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('c717739f-cef5-4b02-bbd5-f865fc1c2061', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '', 2, '添加用户', 'sys:user:add', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:07:37', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('cb983e73-af3a-4369-8c47-f83984642df3', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8', '', 2, '添加菜单', 'sys:menus:add', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:11:32', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('dc555c47937d5fad9eabd6f52e97445a', '828e0ec3-69c1-4eac-8d19-339f7b3fb917', '<a-icon type=\"snippets\" />', 1, '字典管理', '', 1, 'manage/sys-dict/list', '/manage/dict-list', NULL, 0, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('dee9ad90-08e2-4792-8c09-fe6d8b5d04cb', 'f2e2258b-0ef5-4175-948e-dfdec4185785', '', 2, '编辑角色', 'sys:roles:edit', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:11:16', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('df83b0c9-7d96-448a-a337-8e85664dce0e', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '', 2, '删除用户', 'sys:user:del', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:07:53', '2023-09-02 01:25:53', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('eefb0cb6-a1d5-40e9-922c-e5ada9ac510e', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5', '', 2, '解锁用户', 'sys:user:unlock', 5, NULL, NULL, NULL, 0, NULL, '2021-06-02 23:08:22', '2023-09-10 02:36:57', 0);
INSERT INTO `sys_menu` (`id`, `pid`, `icon`, `menus_type`, `name`, `permission`, `sort`, `file_path`, `path`, `link`, `show_flag`, `create_by`, `create_time`, `update_time`, `del_flag`) VALUES ('f2e2258b-0ef5-4175-948e-dfdec4185785', '828e0ec3-69c1-4eac-8d19-339f7b3fb917', '<a-icon type=\"team\" />', 1, '角色管理', '', 5, 'manage/sys-role/list', '/manage/roles-list', NULL, 0, NULL, '2021-06-02 23:00:22', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `permission_list` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '权限列表',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `create_time`, `del_flag`, `update_time`, `name`, `permission_list`, `create_by`) VALUES ('01841a2e-309f-4e3a-9609-e2574695dbe3', '2023-09-09 21:30:32', 0, '2023-09-09 21:30:39', '超链接', '[\"sys:menus:del\", \"sys:menus:add-children\", \"sys:roles:del\", \"sys:user:lock\", \"sys:user:edit\", \"sys:menus:edit\", \"sys:roles:add\", \"sys:user:add\", \"sys:menus:add\", \"sys:roles:edit\", \"sys:user:del\", \"sys:user:unlock\"]', 'admin');
INSERT INTO `sys_role` (`id`, `create_time`, `del_flag`, `update_time`, `name`, `permission_list`, `create_by`) VALUES ('12578678-e523-4448-9ccb-c4ba6ef01f96', '2023-08-30 23:16:41', 0, '2023-09-09 21:30:48', '欢迎页面', '[\"sys:menus:del\", \"sys:menus:add-children\", \"sys:roles:del\", \"sys:user:lock\", \"sys:user:edit\", \"sys:menus:edit\", \"sys:roles:add\", \"sys:user:add\", \"sys:menus:add\", \"sys:roles:edit\", \"sys:user:del\", \"sys:user:unlock\"]', 'admin');
INSERT INTO `sys_role` (`id`, `create_time`, `del_flag`, `update_time`, `name`, `permission_list`, `create_by`) VALUES ('1d528a1b-15c9-4929-b998-b70ce1333d9c', '2023-08-30 23:30:10', 0, '2023-09-09 21:30:51', '用户管理', '[\"sys:menus:del\", \"sys:menus:add-children\", \"sys:roles:del\", \"sys:user:lock\", \"sys:user:edit\", \"sys:menus:edit\", \"sys:roles:add\", \"sys:user:add\", \"sys:menus:add\", \"sys:roles:edit\", \"sys:user:del\", \"sys:user:unlock\"]', 'admin');
INSERT INTO `sys_role` (`id`, `create_time`, `del_flag`, `update_time`, `name`, `permission_list`, `create_by`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '2023-09-09 21:30:30', 0, NULL, '系统管理员', '[\"sys:menus:del\", \"sys:menus:add-children\", \"sys:roles:del\", \"sys:user:lock\", \"sys:user:edit\", \"sys:menus:edit\", \"sys:roles:add\", \"sys:user:add\", \"sys:menus:add\", \"sys:roles:edit\", \"sys:user:del\", \"sys:user:unlock\"]', 'admin');
INSERT INTO `sys_role` (`id`, `create_time`, `del_flag`, `update_time`, `name`, `permission_list`, `create_by`) VALUES ('a15001d9-db9e-44bb-9427-0b3836f2267c', '2023-08-30 23:15:36', 0, '2023-09-09 21:30:53', '角色1', '[\"sys:menus:del\", \"sys:menus:add-children\", \"sys:roles:del\", \"sys:user:lock\", \"sys:user:edit\", \"sys:menus:edit\", \"sys:roles:add\", \"sys:user:add\", \"sys:menus:add\", \"sys:roles:edit\", \"sys:user:del\", \"sys:user:unlock\"]', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menus`;
CREATE TABLE `sys_role_menus` (
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色id',
  `menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单id',
  KEY `fk_r_m_menu_id` (`menu_id`) USING BTREE,
  KEY `fk_r_m_role_id` (`role_id`) USING BTREE,
  CONSTRAINT `fk_r_m_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_r_m_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_menus
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'dee9ad90-08e2-4792-8c09-fe6d8b5d04cb');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'f2e2258b-0ef5-4175-948e-dfdec4185785');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '47ef4177-39ee-40ff-b587-d108ca4db764');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '8a475740-d314-4885-aebd-100b22ddf625');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '4e5aae5a-1a1e-4b64-89a8-3dae010ec2e5');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '4bfb47b0-1235-40da-ab56-8fb5863957fa');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '54bc1606-8c0e-4824-9e37-d25744d10550');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'c717739f-cef5-4b02-bbd5-f865fc1c2061');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'df83b0c9-7d96-448a-a337-8e85664dce0e');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'eefb0cb6-a1d5-40e9-922c-e5ada9ac510e');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'b5ae23e0-c026-443f-b316-8f67eafa8fd8');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '28da4001-5f34-4174-a29d-151b5567ff3d');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '3aaf0b5c-5d08-4e72-8ab3-869388525b6d');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '73e3102d-5319-45c6-a3d2-156524aed6f1');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'cb983e73-af3a-4369-8c47-f83984642df3');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '00eed5e8-a8df-4f6d-b758-c6bf8af47a37');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', 'dc555c47937d5fad9eabd6f52e97445a');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '950cc9fc-08e0-434e-ac57-4f3d66680ef0');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '4b35f66f0eb0523915899ed9a8f3dc7a');
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`) VALUES ('37276b55-dc6a-49d5-9ae3-271e1783d442', '828e0ec3-69c1-4eac-8d19-339f7b3fb917');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `age` int NOT NULL DEFAULT '0' COMMENT '年龄',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '用户状态标记 0:正常 1:禁用',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `mail` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `last_modified_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '最后操作用户',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电话',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `gender` tinyint DEFAULT NULL COMMENT '性别',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_51bvuyvihefoh4kp5syh2jpi4` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `create_time`, `del_flag`, `update_time`, `age`, `name`, `password`, `status`, `username`, `mail`, `last_modified_by`, `phone`, `create_by`, `gender`) VALUES ('001', NULL, 0, '2023-09-13 00:57:05', 20, 'he quan', '21232f297a57a5a743894a0e4a801fc3', b'0', 'admin', '162165436@qq.com', NULL, '11111111', NULL, 1);
INSERT INTO `sys_user` (`id`, `create_time`, `del_flag`, `update_time`, `age`, `name`, `password`, `status`, `username`, `mail`, `last_modified_by`, `phone`, `create_by`, `gender`) VALUES ('d69f043524094cf90f60a6c55e8c3cfa', NULL, 0, '2023-09-13 00:57:06', 18, '222', '81dc9bdb52d04dc20036dbd8313ed055', b'0', '222', '222', NULL, '11111111', NULL, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles` (
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  KEY `fk_u_r_user_id` (`user_id`) USING BTREE,
  KEY `fk_u_r_role_id` (`role_id`) USING BTREE,
  CONSTRAINT `fk_u_r_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_u_r_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user_roles
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('001', '37276b55-dc6a-49d5-9ae3-271e1783d442');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('001', '01841a2e-309f-4e3a-9609-e2574695dbe3');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('001', '12578678-e523-4448-9ccb-c4ba6ef01f96');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('d69f043524094cf90f60a6c55e8c3cfa', '01841a2e-309f-4e3a-9609-e2574695dbe3');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('d69f043524094cf90f60a6c55e8c3cfa', '12578678-e523-4448-9ccb-c4ba6ef01f96');
INSERT INTO `sys_user_roles` (`user_id`, `role_id`) VALUES ('d69f043524094cf90f60a6c55e8c3cfa', 'a15001d9-db9e-44bb-9427-0b3836f2267c');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
